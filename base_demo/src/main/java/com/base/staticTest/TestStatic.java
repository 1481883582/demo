package com.base.staticTest;

public class TestStatic {
    static {
        //1.main方法所在类的静态代码块，打印static；
        System.out.println("static");
    }
    //开始执行main方法；
    public static void main(String[] args) {
        //2.执行main方法中的代码。new一个B的实例，进入到类B；
        new ClassB();
    }
}

class ClassA{
    ClassD d;
    ClassF classF = new ClassF();
    static ClassE classE = new ClassE();
    static {
        //3.从ClassB进入从父类ClassA的静态代码块，打印ClassA 1；
        System.out.println("ClassA 1");
    }
    {
        //7.执行父类的构造代码块，打印ClassA 2；
        System.out.println("ClassA 2");
        //8.此处开始new对象（非静态相关）。
        d = new ClassD();
    }
    public ClassA(){
        //11.执行完ClassA的构造代码块，执行构造方法，打印ClassA 3；
        System.out.println("ClassA 3");
    }
}
//ClassB有父类ClassA
class ClassB extends ClassA{
    //4.调用完了父类的静态相关来到子类的静态相关
    static ClassC classC = new ClassC();
    static {
        //6.按照顺序来调用自己的静态代码块，到此子类的所有静态都执行完毕接下来将会执行非静态相关，打印ClassB 1；
        System.out.println("ClassB 1");
    }
    {
        //12.执行完父类构造方法，执行子类的非静态块，打印ClassB 2；
        System.out.println("ClassB 2");
    }
    public ClassB() {
        //13.调用完自己的非静态块调用自己的构造方法，打印ClassB 3。
        System.out.println("ClassB 3");
    }
}

class ClassC{
    public ClassC() {
        //5.new ClassC的时候，ClassC没有父类,没有静态代码块，没有构造代码块，直接执行构造方法，打印ClassC；
        //9.在new ClassD的时候，ClassD有父类ClassC，父类中没有静态代码，子类中没有静态代码；父类中也没有构造代码块，执行父类的构造方法，打印ClassC；
        System.out.println("ClassC");
    }
}
//ClassD有父类 ClassC
class ClassD extends ClassC{
    public ClassD() {
        //10.调用完父类构造方法，由于子类没有构造代码块，直接执行子类构造方法，打印ClassD
        System.out.println("ClassD");
    }
}

class ClassE{
    public ClassE() {
        System.out.println("ClassE");
    }
}

class ClassF{
    public ClassF() {
        System.out.println("ClassF");
    }
}

//同等级变量>代码块>构造
//父类>子类
//静态>普通
//
//总结 父类静态变量>父类静态代码块>子类静态变量>子类静态代码块>父类变量>父类代码块>父类构造>子类变量>子类代码块>子类变量