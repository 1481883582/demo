package com.kafka.kafka_demo.detailed;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Topic 详细操作  基本操作
 */
@Slf4j
public class TopicDetailed {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建KafkaAdminClient
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "cdh01.example.com:9092,cdh02.example.com:9092,cdh03.example.com:9092");

        KafkaAdminClient adminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);

//        //创建Topic  默认异常创建Topic   Topic名称  分区数据  复制因子
//        CreateTopicsResult clientTopics = adminClient.createTopics(Arrays.asList(new NewTopic("bb", 3, (short) 1)));
//        //使创建变为同步  等待其返回结果 同步创建
//        clientTopics.all().get();
//        log.info("创建Topic :" + clientTopics.values().toString());

//        //默认异步删除Topic
//        DeleteTopicsResult deleteTopics = adminClient.deleteTopics(Arrays.asList("ac"));
//        //等待其返回结果  再执行下一步  使其变为同步删除
//        deleteTopics.all().get();

        log.info("打印Topic列表================================================");
        //查看Topic列表
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Set<String> names = listTopicsResult.names().get();
        for (String name : names) {
            //打印topic
            log.info(name);
        }

        log.info("打印Topic详细信息=============================================");
        //Topic的详细信息  默认异步
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(names);
        //使其同步  并且大新详细信息
        describeTopicsResult.all().get().forEach((k,v)->{
            //topic名  aa
            log.info(k);
            //详细 (name=aa, internal=false, partitions=(partition=0, leader=8.130.166.84:9092 (id: 0 rack: null), replicas=8.130.166.84:9092 (id: 0 rack: null), isr=8.130.166.84:9092 (id: 0 rack: null)), authorizedOperations=null)
            log.info(v.toString());

        });


        //关闭adminClient
        adminClient.close();
    }
}
