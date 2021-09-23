package com.kafka.kafka_demo.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 简单消费者  subscribe自动管理消息分配给消费组中的消费者
 */
@Slf4j
public class ConsumerSubscribe {
    public static void main(String[] args) {
        //创建KafkaAdminClient
        Properties properties = new Properties();
        //kafka 服务器可以多个  0.0.0.0:8080,0.0.0.0:8080
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "test:9092");
        //key传递反序列化规则
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //value传递反序列化规则
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //指定消费者分组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "c2");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //订阅相关得Topics
        //一个topic多个分区时 一个消费组多个消费者时  subscribe订阅自动负载均衡
        consumer.subscribe(Pattern.compile("^ac.*"));

        //遍历消息队列
        while (true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            if(!consumerRecords.isEmpty()){//从队列中获取数据
                Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
                while (iterator.hasNext()){
                    //获取一个消费消息
                    ConsumerRecord<String, String> next = iterator.next();

                    //消息的Topic
                    String topic = next.topic();

                    //消息的分区
                    int partition = next.partition();

                    //消息的偏移量
                    long offset = next.offset();

                    //key
                    String key = next.key();
                    //value
                    String value = next.value();
                    //时间戳
                    long timestamp = next.timestamp();

//                    log.info("===========topic:" + topic +"\n分区partition:" + partition + "\n偏移量offset:" + offset + "\nkey:" + key + "\nvalue:" + value +"\ntimestamp" + timestamp);
                    log.info("===========topic:" + topic +"==分区partition:" + partition + "==偏移量offset:" + offset + "==key:" + key + "==value:" + value +"==timestamp" + timestamp);
                }
            }
            //死循环让一下线程  防止机子卡死
            Thread.yield();
        }
    }

}
