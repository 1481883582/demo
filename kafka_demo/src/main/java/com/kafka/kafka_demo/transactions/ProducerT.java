package com.kafka.kafka_demo.transactions;

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
public class ProducerT {
    public static void main(String[] args) {
        KafkaProducer<String, String> producer = buildKafkaProducer();
        producer.initTransactions();//初始化事务

        try{
            producer.beginTransaction();//开启事务

            for (int i = 0; i < 10; i++) {

//                if(i==8){
//                    int n=10/0;
//                }

                //推送给分区消息 有key走的时hash
                ProducerRecord<String, String> record = new ProducerRecord<>("ac", "key" + i, "事务" + i);

                //发送消息  topic key value
                producer.send(record);
                //刷新缓存区
                producer.flush();

                log.info(record.toString());
            }

            producer.commitTransaction();//提交事务

        }catch (Exception e){
            producer.abortTransaction();//关闭事务
        }finally {
            //关闭生产者
            producer.close();
        }
    }

    private static KafkaProducer<String, String> buildKafkaProducer() {

        //创建KafkaProducer
        Properties properties = new Properties();
        //kafka 服务器可以多个  0.0.0.0:8080,0.0.0.0:8080
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "test:9092");
        //key传递序列化规则
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //value传递序列化规则
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        //设置事务id  id必须唯一
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction-id" + UUID.randomUUID().toString());
        //配置Kafka批处理大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024);
        //等待5ms  如果batch中数据不足 1024大小
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 5);

        //配置Kafka重试机制和幂等性
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 20000);

        return new KafkaProducer<String, String>(properties);
    }

}
