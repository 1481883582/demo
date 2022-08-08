package com.base.staticTest;

public class TestStatic1 {
    private static int i = 0;

    public int get() {
        return ++i;
    }

    public static void main(String[] args) {
        new TestStatic1().get();

        TestStatic1 i2 = new TestStatic1();
        i2.get();
        System.out.println(i2.get());
    }
}
