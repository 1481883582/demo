package com.flink.arithmetic;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * 测输出流
 */
public class SideOutputDemo {

    public static void main(String[] args) throws Exception {
        LocalStreamEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        DataStream<Tuple2<Long, Long>> dataStream = env
                .fromElements(
                        Tuple2.of(2L, 3L),
                        Tuple2.of(1L, 3L),
                        Tuple2.of(3L, 3L),
                        Tuple2.of(2L, 4L),
                        Tuple2.of(1L, 4L),
                        Tuple2.of(4L, 4L),
                        Tuple2.of(5L, 4L),
                        Tuple2.of(6L, 4L),
                        Tuple2.of(1L, 3L)
                );

        SingleOutputStreamOperator<String> process = dataStream.process(
                new ProcessFunction<Tuple2<Long, Long>, String>() {
                    @Override
                    public void processElement(Tuple2<Long, Long> v, Context context, Collector<String> collector) throws Exception {
                        switch (v.f0.toString()){
                            case "1":
                                context.output(new OutputTag<String>("1"){}, v + "属于1L");
                                break;
                            case "2":
                                context.output(new OutputTag<String>("2"){}, v + "属于2L");
                                break;
                            case "3":
                                context.output(new OutputTag<String>("3"){}, v + "属于3L");
                                break;
                            case "4":
                                context.output(new OutputTag<String>("4"){}, v + "属于4L");
                                break;
                            default:
                                collector.collect(v + "不小于5L");
                                break;
                        }
                    }
                }
        );
        process.print("5");
        process.getSideOutput(new OutputTag<String>("1"){}).print("1");
        process.getSideOutput(new OutputTag<String>("2"){}).print("2");
        process.getSideOutput(new OutputTag<String>("3"){}).print("3");
        process.getSideOutput(new OutputTag<String>("4"){}).print("4");

        env.execute("execute");
    }
}
//2:4> (2,3)属于2L
//1:5> (1,3)属于1L
//1:4> (1,3)属于1L
//4:1> (4,4)属于4L
//3:6> (3,3)属于3L
//2:7> (2,4)属于2L
//1:8> (1,4)属于1L
//5:3> (6,4)不小于5L
//5:2> (5,4)不小于5L
