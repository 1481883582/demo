package com.flink.arithmetic;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 获取最大值 最小值 平均值  等等
 */
public class KeyByDemo2 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // this can be used in a streaming program like this (assuming we have a StreamExecutionEnvironment env)
        env.fromElements(Tuple2.of(2L, 3L), Tuple2.of(1L, 3L), Tuple2.of(3L, 3L), Tuple2.of(2L, 4L), Tuple2.of(1L, 4L), Tuple2.of(1L, 3L))
                .keyBy(0) // 以数组的第一个元素作为key
//                .keyBy(1) // 以数组的第一个元素作为key
                .map((MapFunction<Tuple2<Long, Long>, String>) longLongTuple2 -> "key:" + longLongTuple2.f0 + ",value:" + longLongTuple2.f1).uid("map")
                .print();

        env.execute("execute");

        //keyBy 为0的运行结果
//        8> key:2,value:3
//        8> key:3,value:3
//        8> key:2,value:4
//        6> key:1,value:3
//        6> key:1,value:4
//        6> key:1,value:3

        //keyBy 为1的运行结果
//        8> key:2,value:3
//        1> key:2,value:4
//        1> key:1,value:4
//        8> key:1,value:3
//        8> key:3,value:3
//        8> key:1,value:3
    }
}
