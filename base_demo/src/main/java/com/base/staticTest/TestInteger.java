package com.base.staticTest;

public class TestInteger {
    public static void main(String[] args) {
        Integer i1 = 126;
        Integer i2 = 126;
        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);
    }
}
// 默认Integer high 范围-128-127之间
// 以下命令可以修改Integer缓存数值大小
// IDEA VM命令: -XX:AutoBoxCacheMax=1000
// java jar启动： -XX:AutoBoxCacheMax=1000