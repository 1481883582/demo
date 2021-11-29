package create;


/**
 * Runnable接口应该由其实例旨在由线程执行的任何类实现。 该类必须定义一个名为run的无参数方法。
 * 此接口旨在为希望在活动时执行代码的对象提供通用协议。 例如， Runnable是由类Thread实现的。 处于活动状态仅意味着线程已启动且尚未停止。
 * 此外， Runnable提供了使类处于活动状态而不是子类化Thread 。 通过实例化Thread实例并将自身作为目标传入，实现Runnable的类可以在不继承Thread的情况下运行。 在大多数情况下，如果您只打算覆盖run()方法而不打算覆盖其他Thread方法，则应该使用Runnable接口。 这很重要，因为除非程序员打算修改或增强类的基本行为，否则类不应被子类化。
 * 自从：
 * JDK1.0
 * 也可以看看：
 * Thread ， java.util.concurrent.Callable
 * 作者：
 * 阿瑟·范霍夫
 * @author  Arthur van Hoff
 * @see     Thread
 * @see     java.util.concurrent.Callable
 * @since   JDK1.0
 */
@FunctionalInterface
public interface Runnable {
    /**
     * 当使用实现接口Runnable的对象创建线程时，启动线程会导致在单独执行的线程中调用对象的run方法。
     * 方法run的一般约定是它可以采取任何行动。
     * 也可以看看：
     * Thread.run()
     * @see     Thread#run()
     */
    public abstract void run();
}
