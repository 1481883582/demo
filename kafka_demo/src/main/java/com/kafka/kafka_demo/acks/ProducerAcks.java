package com.kafka.kafka_demo.acks;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

/**
 * 简单生产者   subscribe自动管理消息分配给消费组中的消费者
 */
@Slf4j
public class ProducerAcks {
    public static void main(String[] args) throws InterruptedException {
        //创建KafkaProducer
        Properties properties = new Properties();
        //kafka 服务器可以多个  0.0.0.0:8080,0.0.0.0:8080
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "cdh01.example.com:9092,cdh02.example.com:9092,cdh03.example.com:9092");
        //key传递序列化规则
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //value传递序列化规则
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //设置Kafka Acks以及retries
        //设置应答模式 需要Leader和至少一个副本确认
//        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        //重发次数3次 如果超过3次也失败,则系统放弃发送
//        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        //将检测超时的时间设置为1毫秒
//        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1);
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (; ; ) {


            //推送给分区消息 有key走的时hash
            ProducerRecord<String, String> record = new ProducerRecord<>("flume_es_test1", "flume_reids", UUID.randomUUID().toString());

            //发送消息  topic key value
            producer.send(record);
            //刷新缓存区
            producer.flush();
            log.info(record.toString());

            Thread.sleep(1000);
        }


        //关闭生产者
    }

}
