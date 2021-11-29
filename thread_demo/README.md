# 线程池
## [线程的三种创建方式](https://gitee.com/valuenull/thread-pool/blob/master/src/create/CreateThread.java)
```java
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThread extends Thread{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "我是第二种");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "我是第一种");
        }).start();

        new CreateThread().start();


        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "我是第三种");
            return 1;
        });

        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
```
运行结果
```java
//Thread-1我是第二种
//Thread-0我是第一种
//Thread-2我是第三种
//1
```
主要是实现了[Callable](https://gitee.com/valuenull/thread-pool/blob/master/src/create/Callable.java)与[Runnable](https://gitee.com/valuenull/thread-pool/blob/master/src/create/Runnable.java)接口 重写了run()与call()
## 线程体系架构
```java
1.Executor: 线程池顶级接口;
2.ExecutorService:线程池次级接口,对Executor做了些扩展,增加了些功能:
3.ScheduledExecutorService:对ExecutorService做了一些扩 展,增加一些定时任务相关的功能:
4.AbstractExecutorService:抽象类,运用模板方法设计模式实现了部分方法:
5.ThreadPoolExecutor:普通线程池类,包含最基本的一些线程池操作相关的方法实现:
6.ScheduledThreadPoolExecutor:定时任务线程池类,用于实现定时任务相关功能;
7.ForkJoinPool:新型线程池类,java7中 新增的线程池类,基于工作窃取理论实现,运用于大任务拆小任务,任务无限多的场景;
```
![img.png](resources/img.png)
## ThreadPoolExecutor
## ScheduledThreadPoolExecutor
## ForkJoinPool
## ForkJoinTask
## RecurisveAction
## RecursiveTask
## CountedCompleter
## Completion
## CompletableFuture