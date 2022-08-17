package demo.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * 2022/8/13 15:08
 * 分布式并发锁实现
 **/
@Component
public class RedisLock {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加元素
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        if (null == key || null == value) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存在返回 false, 否则返回 true
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public Boolean setNx(String key, String value, Long expireTime, TimeUnit timeUnit) {
        if (null == key || null == value) {
            return false;
        }
        // Springboot 1.5版本setIfabsent只能设置key-value， 需要单独对key设置过期时间，因为时两部操作，所以不是原子性
        // redisTemplate.opsForValue().setIfAbsent(key, value);
        // redisTemplate.expirt(key, expireTime, timeUnit)

        // 在SpringBoot 2可以直接使用redisTemplate的setIfAbsent设置key-value和过期时间， 是原子性
        return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public Object getLock(String key) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除锁
     * @param key
     */
    public void removeLock(Object key) {
        if (null == key) {
            return;
        }
        redisTemplate.delete(key);
    }

    /**
     * 加锁，
     * @param key
     * @param waitTime 等待时间， 在这个时间内会多次尝试获取锁，超过这个时间还没获得锁，就返回false
     * @param interval 间隔时间， 每隔多长时间重新尝试一次获得锁
     * @param expireTime key的过期时间
     * @return
     */
    public Boolean lock(String key, Long waitTime, Long interval, Long expireTime) {
        String value = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();

        Boolean flag = setNx(key, value, expireTime, TimeUnit.MILLISECONDS);
        logger.info("flag: {}", flag);
        if (flag) {
            return flag;
        } else {
            //获取现在时间
            long newTime = System.currentTimeMillis();
            //获取等待过期时间
            long loseTime = newTime + waitTime;
            // 不断的尝试获取锁成功返回
            while(System.currentTimeMillis() < loseTime) {
                Boolean testFlag = setNx(key, value, expireTime, TimeUnit.MILLISECONDS);
                logger.info("testFlag: {}", testFlag);
                if (testFlag) {
                    return testFlag;
                }
                try {
                    Thread.sleep(interval);
                } catch (Exception e) {
                    logger.error("获取锁异常: ", e);
                }
            }
        }
        return false;
    }

    /**
     * 释放锁
     * @param key
     */
    public void unLock(String key) {
        removeLock(key);
    }

    // SpringBoot 1.5版本setIfabsent
//    public Boolean setIfAbsent(String key, String value) {
//        Boolean tf = redisTemplate.opsForValue().setIfAbsent(key, value);
//        redisTemplate.expire(key, 60, TimeUnit.DAYS);
//        return tf;
//    }


}
