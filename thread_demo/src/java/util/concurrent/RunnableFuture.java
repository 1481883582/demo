/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;

/**
 * {@link Future} 是 {@link Runnable}。
 * {@code run} 方法的成功执行会导致 {@code Future} 的完成并允许访问其结果。
 * @see FutureTask
 * @see Executor
 * @since 1.6
 * @author Doug Lea
 * @param <V> 这个 Future 的
 * {@code get} 方法返回的结果类型
 *
 * 继承 Runnable，Future
 */
public interface RunnableFuture<V> extends Runnable, Future<V> {
    /**
     * 将此 Future 设置为其计算结果，除非它已被取消。
     * 线程具体执行方法
     */
    void run();
}
