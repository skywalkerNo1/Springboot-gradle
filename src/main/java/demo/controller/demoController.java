package demo.controller;

import demo.config.webSocket.WebSocketServer;
import demo.dao.UserMapper;
import demo.model.User;
import demo.service.RedisService;
import demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Api(tags = "demo")
@RestController
public class demoController extends BaseController {

    @Autowired
    private RedisService redisService;
    @Resource(name = "threadPool")
    private ExecutorService executorService;
    @Autowired
    private WebSocketServer webSocketServer;

    @ApiOperation("测试接口")
    @GetMapping("/demo")
    public Map<String, Object> demo(String message) {
        webSocketServer.sendMessage( "127.0.0.1",message);
        return successResult();
    }
}
