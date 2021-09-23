package com.kafka.kafka_demo.acks;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 简单生产者   subscribe自动管理消息分配给消费组中的消费者
 */
@Slf4j
public class ProducerAcksIdempotent {
    public static void main(String[] args) {
        //创建KafkaProducer
        Properties properties = new Properties();
        //kafka 服务器可以多个  0.0.0.0:8080,0.0.0.0:8080
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "test:9092");
        //key传递序列化规则
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //value传递序列化规则
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        //设置Kafka Acks以及retries
        //设置应答模式 需要Leader和至少一个副本确认
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        //重发次数3次 如果超过3次也失败,则系统放弃发送
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        //将检测超时的时间设置为1毫秒
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1);

        //开启Kafka的幂等性
        //开启幂等
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        //限制客户端在单个连接上能够发送的未响应请求的个数。设置此值是1表示kafka broker在响应请求之前client不能再向同一个broker发送请求。注意：设置此参数是为了避免消息乱序
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //推送给分区消息 有key走的时hash
        ProducerRecord<String, String> record = new ProducerRecord<>("ac", "key", "开启幂等了");

        //发送消息  topic key value
        producer.send(record);
        log.info(record.toString());
        //刷新缓存区
        producer.flush();


        //关闭生产者
        producer.close();
    }

}
