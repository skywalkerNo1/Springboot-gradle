package demo.config.webSocket;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * 2021/7/2021:56
 **/
@ServerEndpoint("/websokcet/{ip}")
@Component
public class WebSocketServer {

    /**
     * 定义一个全局缓存，用来存放每个客户端对应的websocket对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 建立连接成功调用的方法
     * @param session session
     * @param ip 连接唯一key
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("ip") String ip) {
        this.session = session;
        if (!concurrentHashMap.containsKey(ip)) {
            concurrentHashMap.put(ip, this);
            System.out.println("【websocket】已连接，ip：" + ip);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClass(@PathParam("ip") String ip) {
        concurrentHashMap.remove(ip);
        System.out.println("【websocket】连接已关闭！，ip：" + ip);
    }

    /**
     * 接收到客户端消息时触发
     */
    @OnMessage
    public void  onMessage(String msg, @PathParam("ip") String ip) {
        System.out.println("接收到客户端的消息！");
        System.out.println(msg);
    }

    /**
     * 连接发生异常时触发
     * @param errroe 错误信息
     */
    @OnError
    public void onError(Throwable errroe) {
        System.out.println("【websocket】连接发生异常：" + errroe);
    }

    /**
     * 发送消息到指定客户端
     * @param ip 指定客户端
     * @param message 推送给客户端的消息
     */
    public void sendMessage(String ip, String message) {
        WebSocketServer server = concurrentHashMap.get(ip);
        if (!Objects.isNull(server)) {
            //当客户端时open状态时，才能发送消息
            if (server.session.isOpen()) {
                try {
                    server.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.out.println("【websocket】异常！消息发送失败，ip:" + ip);
                }
            } else {
                System.out.println("【websocket】会话已关闭, ip:" + ip);
            }
        } else {
            System.out.println("【websocket】会话不存在，ip:" + ip);
        }
    }

    /**
     * 发送消息到所有客户端
     * @param message 推送给客户端的消息
     */
    public void sendAllMessage(String message) {
        Set<Entry<String, WebSocketServer>> entities = concurrentHashMap.entrySet();
        for (Entry<String, WebSocketServer> entry : entities) {
            String ip = entry.getKey();
            WebSocketServer server = entry.getValue();
            if (server.session.isOpen()) {
                try {
                    Session session = server.session;
                    //同步锁
                    synchronized (session) {
                        server.session.getBasicRemote().sendText(message);
                    }
                } catch (Exception e) {
                    System.out.println("【websocket】异常！消息发送失败，ip:" + ip);
                }
            } else {
                System.out.println("【websocket】会话不存在，ip:" + ip);
            }
        }
    }
}
