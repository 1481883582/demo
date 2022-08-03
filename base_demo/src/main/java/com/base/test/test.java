package com.base.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class test {
    private static List<String> list = new ArrayList<String>();

    public static void main(String[] args) {


        String[] names = new String[]{
                "瑞友科技",
                "中软国际",
                "软通动力",
                "拓保软件",
                "海南钦诚",
                "上海思芮",
                "上海复深蓝",
                "远涛信息",
                "多赋信息",
                "北京华路时代",
                "法本",
                "鑫揽",
                "上海亮金信息",
                "雀翼科技",
                "兴融联科技",
                "同方鼎欣",
                "武汉佰钧成",
                "华钦软件",
                "新致软件",
                "博彦科技",
                "上海睿民科技",
                "重庆新致金服",
                "中科创达",
                "博鼠",
                "迈志科技",
                "上海弘雅"
        };

        while (true){
            System.out.println("请输入公司名称:");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();

            for (String name : names) {
                if(name.contains(next)){
                    list.add(name);
                }
            }

            if(!list.isEmpty()){
                System.out.println("包含外包公司:" + list);
            }
            list.clear();
        }


    }
}
