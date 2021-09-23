package com.kafka.kafka_demo.listenner;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Arrays;
import java.util.stream.Collectors;

@EnableKafkaStreams
@EnableKafka
@Slf4j
public class KafkaListenner {

    /**
     * 接受 aa Topic 处理后发送给 bb Topic
     * @param value
     * @return
     */
    @KafkaListeners(value = {@KafkaListener(topics = {"aa"})})
    @SendTo(value = {"topic05"})
    public String listenner(ConsumerRecord<?, ?> value) {

        return value.value()+"我从aa处理后过来的！";
    }


    /**
     * 监听 aa Topic
     * @param value
     */
    @KafkaListeners(value = {@KafkaListener(topics = {"aa"})})
    public void singleListenner(ConsumerRecord<?, ?> value) {
        log.info(value.value().toString());
    }


    @Bean
    public KStream<String, String> kStream(StreamsBuilder kStreamBuilder) {

        KStream<String, String> stream = kStreamBuilder.stream(
                "topic02",
                Consumed.with(Serdes.String(),
                        Serdes.String()));

        stream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(String s) {
                        return Arrays.stream(s.split(" ")).collect(Collectors.toList());
                    }
                })
                .selectKey((k,v)->v)
                .groupByKey(Serialized.with(Serdes.String(),Serdes.String()))
                .count(Materialized.<String,Long, KeyValueStore<Bytes, byte[]>>as("wordcount"))
                .toStream()
                .print(Printed.toSysOut());

        return stream;
    }
}
