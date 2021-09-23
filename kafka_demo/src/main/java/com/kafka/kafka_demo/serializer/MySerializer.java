package com.kafka.kafka_demo.serializer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

@Slf4j
public class MySerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        log.info("configure");
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return SerializationUtils.serialize((Serializable) data);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        log.info("close");
    }
}
