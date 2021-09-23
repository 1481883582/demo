package com.flink.arithmetic;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.client.program.StreamContextEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 分区或者分并行度处理
 */
public class KeyByDemo1 {
    public static void main(String[] args) throws Exception {
        //构建环境
        StreamExecutionEnvironment env = StreamContextEnvironment.getExecutionEnvironment();
        //设置分区
//        env.setParallelism(1);
        DataStream<String> stream = env.fromElements("Who's there? I think I hear them. Stand, ho! Who's there?");

        //算子运算
        DataStream<Tuple2<String, Integer>> dataStream = stream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
                        for (String word : s.split(" ")) {
                            out.collect(new Tuple2<String, Integer>(word, 1));
                        }
                    }
                })
                //设置id
                .uid("map")
                //设置分区
//                .setParallelism(1)

//                //根据value 分组
//                .keyBy(1)
//                //根据value 聚合
//                .sum(1);

                //根据key 分组
                .keyBy(0);



        //打印
        dataStream.print();

        //执行
        env.execute();

        //运行结果
//        1> (hear,1)
//        7> (think,1)
//        7> (Stand,,1)
//        6> (Who's,1)
//        6> (there?,1)
//        6> (Who's,1)
//        6> (there?,1)
//        3> (I,1)
//        3> (I,1)
//        3> (them.,1)
//        3> (ho!,1)
    }
}
