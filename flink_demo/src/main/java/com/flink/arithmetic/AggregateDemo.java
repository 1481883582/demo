package com.flink.arithmetic;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * 获取最大值 最小值 平均值  等等
 */
public class AggregateDemo {
    public static void main(String[] args) throws Exception {

        //创建环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //批计算
        env.fromElements(
                Tuple2.of(1L, 4L),
                Tuple2.of(1L, 5L),
                Tuple2.of(1L, 6L),
                Tuple2.of(2L, 7L),
                Tuple2.of(2L, 3L),
                Tuple2.of(2L, 4L)
                )
                //根据key分组
                .groupBy(0)
                //查询每组数据key最大  value最小的值
                .aggregate(Aggregations.MAX, 0)
                .and(Aggregations.MIN, 1)
                .print();
    }
}
//运行结果
//  (1,4)
//  (2,3)