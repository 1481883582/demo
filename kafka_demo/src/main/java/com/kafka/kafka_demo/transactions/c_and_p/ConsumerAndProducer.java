package com.kafka.kafka_demo.transactions.c_and_p;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 简单生产者   subscribe自动管理消息分配给消费组中的消费者
 */
@Slf4j
public class ConsumerAndProducer {
    public static void main(String[] args) {
        KafkaProducer<String, String> producer = buildKafkaProducer();
        KafkaConsumer<String, String> consumer = buildKafkaConsumer("a3");


        producer.initTransactions();//初始化事务
        consumer.subscribe(Pattern.compile("^ac.*"));

        while (true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            if(!consumerRecords.isEmpty()){
                HashMap<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                Iterator<ConsumerRecord<String, String>> recordIterator = consumerRecords.iterator();
                //开启事务控制
                producer.beginTransaction();

                try{
                    //迭代数据，进行业务处理
                    while (recordIterator.hasNext()){
                        ConsumerRecord<String, String> record = recordIterator.next();
                        //构建存储元数据
                        offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()+1));

                        log.info("接收到的数据：" + record.value());
                        //业务处理
                        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("bb", record.key(), record.value() + "业务处理过了！");
                        log.info("发送的数据：" + producerRecord.toString());
                        //发送数据
                        producer.send(producerRecord);

                    }
                    //提交消费者的偏移量
                    producer.sendOffsetsToTransaction(offsets, "a3");
                    producer.commitTransaction();//提交事务
                }catch (Exception e){
                    producer.abortTransaction();//关闭事务
                }finally {
                    //关闭生产者
//                    producer.close();
                }

            }
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

    public static KafkaConsumer<String, String> buildKafkaConsumer(String groupId){
        //创建KafkaAdminClient
        Properties properties = new Properties();
        //kafka 服务器可以多个  0.0.0.0:8080,0.0.0.0:8080
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "test:9092");
        //key传递反序列化规则
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //value传递反序列化规则
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //指定消费者分组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //消费事务的隔离级别read_committed  读已提交
        properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        //必须关闭消费者端的 offset  关闭自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new KafkaConsumer<String, String>(properties);

    }

}
