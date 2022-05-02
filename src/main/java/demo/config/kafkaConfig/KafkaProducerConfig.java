package demo.config.kafkaConfig;

import com.alibaba.druid.util.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置 Kafka 的生产者的工厂类
 *
 * @author admin
 */
@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.properties.security.protocol}")
    private String protocol;
    @Value("${spring.kafka.producer.properties.sasl.mechanism}")
    private String mechanism;
    @Value("${spring.kafka.producer.properties.sasl.jaas.config}")
    private String jaasConfig;

    @Bean("kafkaProducerTemplate")
    @Primary
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * 配置工厂类
     *
     */
    private ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * kafka配置信息
     *
     */
    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        if (!StringUtils.isEmpty(protocol) && !StringUtils.isEmpty(mechanism) && StringUtils.isEmpty(jaasConfig)) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, protocol);
            props.put(SaslConfigs.SASL_MECHANISM, mechanism);
            props.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);
        }
        return props;
    }
}
