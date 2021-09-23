package com.kafka.kafka_demo.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class MyProducerInterceptor implements ProducerInterceptor {
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        ProducerRecord wrapRecord = new ProducerRecord(record.topic(), record.key(), record.value());
        wrapRecord.headers().add("user", "自定义拦截器".getBytes());
        return wrapRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        log.info("metadata:"+metadata+",exception:"+exception);
    }

    @Override
    public void close() {
        log.info("close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        log.info("configs");
    }
}
