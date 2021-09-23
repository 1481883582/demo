package com.flink.arithmetic;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * 真合并  强一致同类型才可以合并
 */
public class UnionDemo {
    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<Long, String>> source1 = env.fromElements(
                Tuple2.of(1L, "张三"),
                Tuple2.of(2L, "李四"));

        DataSet<Tuple2<Long, String>> source2 = env.fromElements(
                Tuple2.of(1L, "王五"),
                Tuple2.of(2L, "曹六"));

        //合并  并且打印
        source1.union(source2).print();

    }
}
//(1,张三)
//(1,王五)
//(2,李四)
//(2,曹六)
