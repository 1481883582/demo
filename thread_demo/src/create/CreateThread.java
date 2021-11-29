package create;

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
//Thread-1我是第二种
//Thread-0我是第一种
//Thread-2我是第三种
//1
