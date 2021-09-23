package com.kafka.kafka_demo.serializer;

import com.kafka.kafka_demo.partitioner.MyPartitioner;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

/**
 * 简单生产者   subscribe自动管理消息分配给消费组中的消费者
 */
@Slf4j
public class ProducerSerializer {
    public static void main(String[] args) {
        //创建KafkaProducer
        Properties properties = new Properties();
        //kafka 服务器可以多个  0.0.0.0:8080,0.0.0.0:8080
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "test:9092");
        //key传递序列化规则
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, MySerializer.class.getName());
        //value传递序列化规则
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MySerializer.class.getName());
        //配置自定义分区策略
//        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());

        KafkaProducer<String, User> producer = new KafkaProducer<String, User>(properties);
        for (int i = 0; i < 10; i++) {

            //推送消息 无key走的时轮询策略
            ProducerRecord<String, User> record = new ProducerRecord<>("ac", "key" + 1, new User(i,"user"+i, new Date()));

            //发送消息  topic key value
            producer.send(record);
            log.info(record.toString());
        }

        //关闭生产者
        producer.close();
    }

}
