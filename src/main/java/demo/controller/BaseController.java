package demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * controller基类， 所有web端controller都继承此类，包含配置信息、包装返回数据等
 */
public class BaseController{

    /**
     * slf4j
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求返回成功
     * @param value 返回值
     * @return 返回
     */
    protected Map<String, Object> successResult(Object... value) {
        Map<String, Object> result = new HashMap<>();
        result.put("flag", "success");
        if (value.length > 0) {
            result.put("result", value[0]);
        }
        return  result;
    }

    protected Map<String, Object> successResult2(Object... value) {
        Map<String, Object> result = new HashMap<>();
        result.put("flag", "success");
        if (value.length == 1) {
            result.put("result", value[0]);
        } else if (value.length == 2) {
            result.put("result", value[0]);
            result.put("message", value[1]);
        }
        return  result;
    }

    /**
     * 请求失败返回
     * @param errorMessage 提示信息
     * @return 封装两个入参map, 前端获取为json
     */
    public Map<String, Object> failedResult(String... errorMessage) {
        Map<String, Object> result = new HashMap<>();
        result.put("flag", "failed");
        if (errorMessage.length > 0) {
            result.put("message", errorMessage[0]);
        } else {
            result.put("mesasge", "系统异常");
        }
        return result;
    }

}
