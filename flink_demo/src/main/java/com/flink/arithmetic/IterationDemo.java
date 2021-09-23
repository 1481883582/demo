package com.flink.arithmetic;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * Iteration
 */
public class IterationDemo {

    public static void main(String[] args) throws Exception {
        //创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //创建1到10的数据
        DataStream<Long> dataStream = env.fromSequence(1L, 10L);
        IterativeStream<Long> iteration = dataStream.iterate();
        DataStream<Long> iterationBody = iteration.map (x->x);
        DataStream<Long> feedback = iterationBody.filter(new FilterFunction<Long>(){
            @Override
            public boolean filter(Long value) throws Exception {
                return value > 9;
            }
        });
        //放入 closeWith中会一直在循环
        iteration.closeWith(feedback);
        DataStream<Long> output = iterationBody.filter(new FilterFunction<Long>(){
            @Override
            public boolean filter(Long value) throws Exception {
                return value < 2;
            }
        });
        //放入 closeWith中会一直在循环
        iteration.closeWith(output);

        output.print();
        iteration.print();

        //执行
        env.execute();
    }
}
//输出 1和 10 无线循环
//5> 1
//5> 1
//8> 10
//5> 1
//5> 1
//8> 10
//5> 1
//5> 1
//8> 10
//5> 1
//5> 1
//8> 10
//5> 1
//5> 1
//8> 10