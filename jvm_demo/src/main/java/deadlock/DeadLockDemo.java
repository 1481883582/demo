package deadlock;

public class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock d1 = new DeadLock(true);
        DeadLock d2 = new DeadLock(false);
        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);
        t1.start();
        t2.start();
    }
} //定义锁对象

class MyLock {
    public static Object obj1 = new Object();
    public static Object obj2 = new Object();
} //死锁代码

class DeadLock implements Runnable {
    private boolean flag;

    DeadLock(boolean flag) {
        this.flag = flag;
    }

    public void run() {
        if (flag) {
            while (true) {
                synchronized (MyLock.obj1) {
                    System.out.println(Thread.currentThread().getName() + "----if获得obj1锁");
                    synchronized (MyLock.obj2) {
                        System.out.println(Thread.currentThread().getName() + "----if获得obj2锁");
                    }
                }
            }
        } else {
            while (true) {
                synchronized (MyLock.obj2) {
                    System.out.println(Thread.currentThread().getName() + "----否则获得obj2锁");
                    synchronized (MyLock.obj1) {
                        System.out.println(Thread.currentThread().getName() + "----否则获得obj1锁");
                    }
                }
            }
        }
    }
}


//bushixiandeMacBook-Pro:~ yuell102$ jstack 18075
//        2022-08-03 08:55:51
//        Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.201-b09 mixed mode):
//
//        "Attach Listener" #16 daemon prio=9 os_prio=31 tid=0x00007fc9f2844800 nid=0x4b0b waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "DestroyJavaVM" #15 prio=5 os_prio=31 tid=0x00007fc9f600c800 nid=0xf03 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "Thread-1" #14 prio=5 os_prio=31 tid=0x00007fc9f2025800 nid=0xa703 waiting for monitor entry [0x000070000b2b7000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//        at DeadLock.run(DeadLockDemo.java:39)
//        - waiting to lock <0x000000076aed9300> (a java.lang.Object)
//        - locked <0x000000076aed9310> (a java.lang.Object)
//        at java.lang.Thread.run(Thread.java:748)
//
//        "Thread-0" #13 prio=5 os_prio=31 tid=0x00007fc9f005d800 nid=0xa803 waiting for monitor entry [0x000070000b1b4000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//        at DeadLock.run(DeadLockDemo.java:30)
//        - waiting to lock <0x000000076aed9310> (a java.lang.Object)
//        - locked <0x000000076aed9300> (a java.lang.Object)
//        at java.lang.Thread.run(Thread.java:748)
//
//        "Service Thread" #12 daemon prio=9 os_prio=31 tid=0x00007fc9f600b000 nid=0x5b03 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C1 CompilerThread3" #11 daemon prio=9 os_prio=31 tid=0x00007fc9f0054000 nid=0x5903 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C2 CompilerThread2" #10 daemon prio=9 os_prio=31 tid=0x00007fc9f200c800 nid=0x5703 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C2 CompilerThread1" #9 daemon prio=9 os_prio=31 tid=0x00007fc9ee82f000 nid=0x5503 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C2 CompilerThread0" #8 daemon prio=9 os_prio=31 tid=0x00007fc9ee82e000 nid=0x4103 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "JDWP Command Reader" #7 daemon prio=10 os_prio=31 tid=0x00007fc9f282c000 nid=0x4303 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "JDWP Event Helper Thread" #6 daemon prio=10 os_prio=31 tid=0x00007fc9ee81a000 nid=0x3e03 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "JDWP Transport Listener: dt_socket" #5 daemon prio=10 os_prio=31 tid=0x00007fc9f6808800 nid=0x3d07 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007fc9f600a800 nid=0x4503 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fc9ee817800 nid=0x4d03 in Object.wait() [0x000070000a58d000]
//        java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
//        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
//        - locked <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
//        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
//        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
//
//        "Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fc9ee814800 nid=0x4f03 in Object.wait() [0x000070000a48a000]
//        java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
//        at java.lang.Object.wait(Object.java:502)
//        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
//        - locked <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
//        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
//
//        "VM Thread" os_prio=31 tid=0x00007fc9ef008800 nid=0x5003 runnable
//
//        "GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007fc9ee80d800 nid=0x1e07 runnable
//
//        "GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007fc9ee812800 nid=0x2203 runnable
//
//        "GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007fc9ee813000 nid=0x2003 runnable
//
//        "GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007fc9f1812000 nid=0x2a03 runnable
//
//        "GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007fc9f2008800 nid=0x2b03 runnable
//
//        "GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007fc9f1812800 nid=0x2c03 runnable
//
//        "GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007fc9f2009000 nid=0x2d03 runnable
//
//        "GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007fc9f1813800 nid=0x2f03 runnable
//
//        "GC task thread#8 (ParallelGC)" os_prio=31 tid=0x00007fc9ee813800 nid=0x3103 runnable
//
//        "GC task thread#9 (ParallelGC)" os_prio=31 tid=0x00007fc9f2808800 nid=0x5103 runnable
//
//        "VM Periodic Task Thread" os_prio=31 tid=0x00007fc9ef00d000 nid=0xa903 waiting on condition
//
//        JNI global references: 1438
//
//
//        Found one Java-level deadlock:
//        =============================
//        "Thread-1":
//        waiting to lock monitor 0x00007fc9f18328c8 (object 0x000000076aed9300, a java.lang.Object),
//        which is held by "Thread-0"
//        "Thread-0":
//        waiting to lock monitor 0x00007fc9f1834ff8 (object 0x000000076aed9310, a java.lang.Object),
//        which is held by "Thread-1"
//
//        Java stack information for the threads listed above:
//        ===================================================
//        "Thread-1":
//        at DeadLock.run(DeadLockDemo.java:39)
//        - waiting to lock <0x000000076aed9300> (a java.lang.Object)
//        - locked <0x000000076aed9310> (a java.lang.Object)
//        at java.lang.Thread.run(Thread.java:748)
//        "Thread-0":
//        at DeadLock.run(DeadLockDemo.java:30)
//        - waiting to lock <0x000000076aed9310> (a java.lang.Object)
//        - locked <0x000000076aed9300> (a java.lang.Object)
//        at java.lang.Thread.run(Thread.java:748)
//
//        Found 1 deadlock.