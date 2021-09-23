package com.kafka.kafka_demo.partitioner;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区
 */
@Slf4j
public class MyPartitioner implements Partitioner {
    /**
     *  返回分区号
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        log.info("MyPartitioner:" + topic.toString());
        log.info("MyPartitioner:" + key);
        log.info("MyPartitioner:" + keyBytes);
        log.info("MyPartitioner:" + value.toString());
        log.info("MyPartitioner:" + valueBytes.toString());
        log.info("MyPartitioner:" + cluster.toString());
        return 0;
    }

    @Override
    public void close() {
        log.info("MyPartitioner:close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        log.info("MyPartitioner:configs" + configs.toString());
    }
}
