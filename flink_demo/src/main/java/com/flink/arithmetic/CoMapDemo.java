package com.flink.arithmetic;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * 假合并+map
 */
public class CoMapDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Long, String>> source1 = env.fromElements(
                Tuple2.of(1L, "张三"),
                Tuple2.of(2L, "李四"));

        DataStream<Tuple2<Integer, String>> source2 = env.fromElements(
                Tuple2.of(1, "王五"),
                Tuple2.of(2, "曹六"));

        //合并  并且打印
        DataStream<Tuple2<Integer, String>> dataStream = source1.connect(source2).map(
            new CoMapFunction<Tuple2<Long, String>, Tuple2<Integer, String>, Tuple2<Integer, String>>() {
                @Override
                public Tuple2<Integer, String> map1(Tuple2<Long, String> value) throws Exception {
                    return new Tuple2<Integer, String>(Math.toIntExact(value.f0), value.f1);
                }

                @Override
                public Tuple2<Integer, String> map2(Tuple2<Integer, String> value) throws Exception {
                    return new Tuple2<Integer, String>(Math.toIntExact(value.f0), value.f1);
                }
            }
        );

        //打印
        dataStream.print();

        //执行
        env.execute();
    }
}

//1> (1,张三)
//7> (2,曹六)
//6> (1,王五)
//2> (2,李四)
