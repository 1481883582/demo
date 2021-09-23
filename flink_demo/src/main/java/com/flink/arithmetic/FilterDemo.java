package com.flink.arithmetic;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.FilterOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 根据条件过滤
 */
public class FilterDemo {
    public static void main(String[] args) throws Exception {
        //构建环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //通过字符串构建数据集
        DataSet<String> text = env.fromElements(
                "Who's there? I think I hear them. Stand, ho! Who's there?");


        //分割字符串、按照key进行分组、统计相同的key个数
        DataSet<Tuple2<String, Integer>> map = text
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s,  Collector<Tuple2<String, Integer>> out) throws Exception {
                        for (String s1 : s.split(" ")) {
                            out.collect(new Tuple2<String, Integer>(s1.toString(), 1));
                        }
                    }
                })
                .filter((s) -> !s.toString().contains("I"));

        //打印
        map.print();
    }

// 输出结果
//            (Who's,1)
//            (there?,1)
//            (think,1)
//            (hear,1)
//            (them.,1)
//            (Stand,,1)
//            (ho!,1)
//            (Who's,1)
//            (there?,1)
}
