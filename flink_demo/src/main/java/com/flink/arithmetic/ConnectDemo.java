package com.flink.arithmetic;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 假合并  不同类型可以合并
 */
public class ConnectDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Long, String>> source1 = env.fromElements(
                Tuple2.of(1L, "张三"),
                Tuple2.of(2L, "李四"));

        DataStream<Tuple2<Integer, String>> source2 = env.fromElements(
                Tuple2.of(1, "王五"),
                Tuple2.of(2, "曹六"));

        //合并  并且打印
        source1.connect(source2);

        source1.print();

        env.execute("execute");
    }
}
//执行结果
//2> (1,张三)
//3> (2,李四)
