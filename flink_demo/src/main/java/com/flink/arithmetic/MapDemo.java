package com.flink.arithmetic;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * 返回 一个值
 */
public class MapDemo {
    public static void main(String[] args) throws Exception {
        //构建环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //通过字符串构建数据集
        DataSet<String> text = env.fromElements(
                "Who's there? I think I hear them. Stand, ho! Who's there?");


        //分割字符串、按照key进行分组、统计相同的key个数
        DataSet<Integer> map = text
                .map(new MapFunction<String, Integer>() {
                    @Override
                    public Integer map(String s) throws Exception {
                        String[] split = s.split(" ");
                        return split.length;
                    }
                });

        //打印
        map.print();
    }

// 输出结果
// 11
}
