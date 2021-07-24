package demo.service;

/**
 * @author admin
 * 2021/7/222:15
 **/
public interface RedisService {

    /**
     * 根据key获取redis中的value值
     * @param key 键名
     * @return 返回信息
     */
    String getRedisValue(String key);

    /**
     * 数据存入redis中
     * @param key 键名
     * @param value 值
     */
    void setRedisValue(String key, String value);

    /**
     *  删除redis缓存
     * @param key 键名
     */
    void deleteRedisValue(String key);
}
