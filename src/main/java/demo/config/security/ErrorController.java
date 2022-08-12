package demo.config.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * 2022/7/24 15:40
 **/
@RestController
public class ErrorController {

    @RequestMapping("/err")
    public String error() {
        return "系统异常, 请稍后再试！";
    }
}
