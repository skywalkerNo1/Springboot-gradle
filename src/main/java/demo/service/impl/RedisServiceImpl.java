package demo.service.impl;

import demo.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * 2021/7/222:20
 **/
@Service
public class RedisServiceImpl implements RedisService {

    //缓存过期时间
    public static final Integer ONE_HOUR_TIMEOUT = 3600;

    @Resource(name = "template")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getRedisValue(String key) {
        String value = null;
        if (!ObjectUtils.isEmpty(key)) {
            value = (String) redisTemplate.opsForValue().get(key);
        }
        return value;
    }

    @Override
    public void setRedisValue(String key, String value) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            redisTemplate.opsForValue().set(key, value, ONE_HOUR_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    @Override
    public void deleteRedisValue(String key) {
        redisTemplate.delete(key);
    }
}
