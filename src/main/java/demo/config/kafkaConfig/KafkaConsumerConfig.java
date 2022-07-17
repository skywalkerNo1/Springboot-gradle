package demo.config.kafkaConfig;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * 配置Kafka 的消费者
 * @author admin
 * 2022/5/2 16:38
 **/
@Configuration
public class KafkaConsumerConfig implements ApplicationRunner {

    String topic = "kafka-test";
    String groupId = "bigdata111";

    /**
     * 主要参数
     * 1、bootstrap.server， group.id, key.deserializer, value.deserializer
     * 2、session.timeout.ms coordinator 检测失败的时间， 设置为比较小的值
     * 3、max.pool.interval.ms consumer 处理逻辑最大时间
     * 4、auto.offset.reset [earliest, lastest, none]
     * earliest: 当个分区下有已提交的offset时，从提交的offset开始消费，无提交时的offset时，从头开始消费
     * latest: 当各分区下有已提交的offset时，从提交的offset开始消费，无提交的offset时， 消费新产生的该分区下的数据
     * none: topic个分区都存在已提交的offset时， 从offset后开始消费，只要有一个分区不存在已提交的offset，则抛出异常
     * 5、enable.auto.commit 是否自动提交位移，设置为false, 由用户自行提交位移
     * 6、fetch.max.bytes 指定consumer单次获取数据的最大字节数
     * 7、max.poll.records 单次调用poll的最大返回消息数，默认500
     * 8、heartbeat.interval.ms 越小越好
     * 9、connections.max.idle.ms kafka定期关闭空闲Socket的时间
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 必须指定有业务员意义的名字
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        // 从最早的消息开始读取
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singletonList(topic));

//        try {
//            while (true) {
//                // 1000 是超时时间（ms）, 在该时间内 poll 会等待服务器返回数据
//                ConsumerRecords<String, String> records = consumer.poll(1000);
//                // poll返回一个记录里列表，
//                // 每条记录都包含了记录所属组题的信息， 记录所在分区的信息， 记录在分区里的偏移量， 以及记录的键值对
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.println("--------------------------------------------------------");
//                    System.out.printf("offset=%d, key=%s, value=%s%n", record.offset(), record.key(), record.value());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭消费者， 网络连接和socket也会随之关闭， 并立即触发一次在均衡
//            consumer.close();
//        }


    }
}
