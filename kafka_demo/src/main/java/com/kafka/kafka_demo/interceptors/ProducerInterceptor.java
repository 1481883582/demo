package com.kafka.kafka_demo.interceptors;

import com.kafka.kafka_demo.partitioner.MyPartitioner;
import com.kafka.kafka_demo.serializer.MySerializer;
import com.kafka.kafka_demo.serializer.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;

import java.util.Date;
import java.util.Properties;

/**
 * 简单生产者   subscribe自动管理消息分配给消费组中的消费者
 */
@Slf4j
public class ProducerInterceptor {
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
        //自定义拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, MyProducerInterceptor.class.getName());

        //配置自定义分区策略  使用得轮询
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class.getName());


        KafkaProducer<String, User> producer = new KafkaProducer<String, User>(properties);

        for (int i = 0; i < 10; i++) {

            //推送消息 无key走的时轮询策略
            ProducerRecord<String, User> record = new ProducerRecord<String, User>("bb",  new User(i,"user"+i, new Date()));

            //发送消息  topic key value
            producer.send(record);
            log.info(record.toString());
        }

        //关闭生产者
        producer.close();
    }

}
