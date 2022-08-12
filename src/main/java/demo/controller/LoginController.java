package demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController extends BaseController {

    /**
     * 增加用户
     *
     * @return
     */
    @RequestMapping("/addMember")
    public Map<String, Object> addMember() {
        return successResult("addMember");
    }

    /**
     * 删除用户
     *
     * @return
     */
    @RequestMapping("/delMember")
    public String delMember() {
        return "delMember";
    }

    /**
     * updateMember
     *
     * @return
     */
    @RequestMapping("/updateMember")
    public String updateMember() {
        return "updateMember";
    }

    /**
     * showMember
     *
     * @return
     */
    @RequestMapping("/showMember")
    public String showMember() {
        return "showMember";
    }

}
