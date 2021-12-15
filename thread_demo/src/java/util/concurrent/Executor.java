package java.util.concurrent;
/**
 * 线程池最顶层接口
 */
public interface Executor {

    /**
     * 传入线程执行的具体内容
     * @param command  具体内容
     */
    void execute(Runnable command);
}
