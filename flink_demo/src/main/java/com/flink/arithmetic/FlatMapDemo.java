package com.flink.arithmetic;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 返回一列对象
 */
public class FlatMapDemo {
    public static void main(String[] args) throws Exception {
        //构建环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //通过字符串构建数据集
        DataSet<String> text = env.fromElements(
                "Who's there?",
                "I think I hear them. Stand, ho! Who's there?");


        //分割字符串、按照key进行分组、统计相同的key个数
        DataSet<Tuple2<String, Integer>> wordCounts = text
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
                        for (String word : s.split(" ")) {
                            out.collect(new Tuple2<String, Integer>(word, 1));
                        }
                    }
                });

        //打印
        wordCounts.print();
    }

// 输出结果
//            (Who's,1)
//            (there?,1)
//            (I,1)
//            (think,1)
//            (I,1)
//            (hear,1)
//            (them.,1)
//            (Stand,,1)
//            (ho!,1)
//            (Who's,1)
//            (there?,1)
}
