package demo.config.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author admin
 * 2022/8/13 14:58
 * 去除redis序列化时， key-value的乱码问题
 **/
@Configuration
public class RedisConfiguration {

    @Primary
    @Bean
    public RedisTemplate setRedisTemplate(@Qualifier("redisTemplate") RedisTemplate template) {
        RedisSerializer stringSerialize = new StringRedisSerializer();
        template.setKeySerializer(stringSerialize);
        template.setValueSerializer(stringSerialize);
        template.setHashKeySerializer(stringSerialize);
        template.setHashValueSerializer(stringSerialize);
        return template;
    }
}
