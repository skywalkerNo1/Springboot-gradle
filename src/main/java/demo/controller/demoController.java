package demo.controller;

import demo.config.webSocket.WebSocketServer;
import demo.service.AsyncSerivce;
import demo.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Api(tags = "demo")
@RestController
@RequestMapping("/test")
public class demoController extends BaseController {

    @Autowired
    private RedisService redisService;
//    @Resource(name = "threadPool")
//    private ExecutorService executorService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Resource(name = "kafkaProducerTemplate")
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private AsyncSerivce asyncSerivce;

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public Map<String, Object> test() {
        return successResult("test success!");
    }

    @ApiOperation("测试接口")
    @GetMapping("/demo")
    public Map<String, Object> demo(String message) {
        webSocketServer.sendMessage( "127.0.0.1",message);
        return successResult();
    }

    @ApiOperation("kafka测试接口")
    @GetMapping("/kafkaDemo")
    public Map<String, Object> kafkaDemo(String message) {
        kafkaTemplate.send( "kafka-test",message);
        return successResult();
    }

    @GetMapping("/getTest")
    public void getTest(Integer count) throws Exception {
        for (int i = 1; i <= 15; i++) {
            asyncSerivce.executeAsync();
            Thread.sleep(count);
        }
    }

    @GetMapping("/getShowTest")
    public void getShowTest(Integer count) throws Exception {
        for (int i = 1; i <= 15; i++) {
            asyncSerivce.executeShowAsync();
            Thread.sleep(count);
        }
    }
}
