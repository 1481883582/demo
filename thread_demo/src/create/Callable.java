package create;

/**
 * 返回结果并可能引发异常的任务。 实现者定义了一个没有参数的单一方法，称为call 。
 * Callable接口类似于Runnable ，因为两者都是为实例可能由另一个线程执行的类而设计的。 但是， Runnable不返回结果并且不能抛出已检查的异常。
 * Executors类包含将其他常见形式转换为Callable类的实用方法。
 * 自从：
 * 1.5
 * 也可以看看：
 * Executor
 * 作者：
 * 道格·利亚
 * 类型参数：
 * @param <V> - 方法call的结果类型
 */
@FunctionalInterface
public interface Callable<V> {
    /**
     * 计算结果，如果无法计算则抛出异常。
     * 返回：
     * 计算结果
     * 抛出：
     * Exception - 如果无法计算结果
     */
    V call() throws Exception;
}