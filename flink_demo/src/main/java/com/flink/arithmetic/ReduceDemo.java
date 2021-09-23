package com.flink.arithmetic;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 根据业务合并流
 */
public class ReduceDemo {
    public static void main(String[] args) throws Exception {
        //创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //流计算
        env.fromElements(Tuple2.of(2L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(2L, 4L), Tuple2.of(1L, 2L))
                .keyBy(0) // 以数组的第一个元素作为key
                .reduce((ReduceFunction<Tuple2<Long, Long>>) (t1, t2) -> new Tuple2<>(t1.f0, t1.f1 + t2.f1)).uid("reduce") // value做累加
                .print();

        //执行
        env.execute("execute");
    }
}
//    sudo shutdown -r now
//    合并value
//        6> (1,5)
//        6> (1,12)
//        6> (1,14)
//        8> (2,3)
//        8> (2,7)