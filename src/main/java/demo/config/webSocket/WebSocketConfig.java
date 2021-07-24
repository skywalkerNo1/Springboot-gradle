package demo.config.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启websocket支持
 * @author admin
 * 2021/7/2021:52
 **/
@Configuration
public class WebSocketConfig {

    /**
     * 启动websocket
     * @return 返回
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
