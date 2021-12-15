package java.util.concurrent;

import java.util.Collection;
import java.util.List;

/**
 * 线程相关接口
 */
public interface ExecutorService extends Executor {

    /**
     * 启动有序关闭，其中执行先前提交的任务，但不会接受新任务。如果已经关闭，调用没有额外的效果。
     * <p>该方法不会等待之前提交的任务完成执行。使用 {@link awaitTermination awaitTermination} 来做到这一点。
     *
     * @throws SecurityException 如果安全管理器存在并且关闭此 ExecutorService 可能会操纵调用者不允许修改的线程，
     * 因为它不持有 {@link RuntimePermission}{@code ("modifyThread")}，
     * 或安全管理器的 {@代码 checkAccess} 方法拒绝访问。
     *
     * 关闭线程
     */
    void shutdown();

    /**
     * 尝试停止所有正在执行的任务，停止等待任务的处理，并返回等待执行的任务列表。
     * <p>此方法不会等待主动执行的任务终止。使用 {@link awaitTermination awaitTermination} 来做到这一点。
     * <p>除了尽力尝试停止处理正在执行的任务之外，没有任何保证。例如，典型的实现将通过 {@link Threadinterrupt} 取消，
     * 因此任何未能响应中断的任务可能永远不会终止。
     * @return 从未开始执行的任务列表 @throws SecurityException
     * 如果安全管理器存在并且关闭此 ExecutorService 可能会操纵调用者不允许修改的线程，
     * 因为它不持有 {@link RuntimePermission}{@code ("modifyThread" )}，或者安全管理器的 {@code checkAccess} 方法拒绝访问。
     *
     * 立即关闭线程
     */
    List<Runnable> shutdownNow();

    /**
     * 如果此执行程序已关闭，则返回 {@code true}。
     * @return {@code true} 如果此执行程序已关闭
     *
     * 线程是否关闭
     */
    boolean isShutdown();

    /**
     * 如果关闭后所有任务都已完成，
     * 则返回 {@code true}。
     * 请注意，除非先调用 {@code shutdown} 或 {@code shutdownNow}，
     * 否则 {@code isTerminated} 永远不会{@code true}。
     * @return {@code true} 如果关闭后所有任务都已完成
     *
     * 线程是否终止，必须调用shutdown或shutdownNow，才有可能为true
     */
    boolean isTerminated();

    /**
     * 阻塞直到所有任务在关闭请求后完成执行，或发生超时，或当前线程被中断，以先发生者为准。
     * @param timeout 等待的最长时间
     * @param unit 超时参数的时间单位
     * @return {@code true} 如果此执行程序终止，{@code false}
     * 如果在终止前超时已过 @throws InterruptedException 如果在等待时中断
     *
     * 在设置时间内线程池变为终止状态后，返回true
     */
    boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 提交一个返回值的任务以供执行，并返回一个表示任务未决结果的 Future。
     * Future 的 {@code get} 方法将在成功完成后返回任务的结果。
     * <p> 如果你想立即阻塞等待任务，你可以使用形式 {@code result = exec.submit(aCallable).get();}
     * <p>注意：{@link Executors}类包含一组方法，可以将一些其他常见的类似闭包的对象，例如，
     * {@link java.security.PrivilegedAction} 转换为 {@link Callable} 形式，以便它们可以被提交。
     * @param task 要提交的任务 @param <T> 任务结果的类型
     * @return 表示任务未决完成的 Future @throws RejectedExecutionException 如果无法安排任务执行
     * @throws NullPointerException 如果任务为空
     *
     * 返回线程执行任务结果
     */
    <T> Future<T> submit(Callable<T> task);

    /**
     * 提交一个 Runnable 任务以供执行，并返回一个代表该任务的 Future。
     * Future 的 {@code get} 方法将在成功完成后返回给定的结果。
     * @param task 提交的任务 @param result 返回的结果
     * @param <T> 结果的类型 @return 表示待完成任务的 Future
     * @throws RejectedExecutionException 如果无法安排任务执行
     * @throws NullPointerException 如果任务为空
     *
     * 返回线程执行结果
     * @param <T> 结果类型
     */
    <T> Future<T> submit(Runnable task, T result);

    /**
     * 提交一个 Runnable 任务以供执行，并返回一个代表该任务的 Future。
     * Future 的 {@code get} 方法将在 <em>successful<em> 完成后返回 {@code null}。
     * @param task 要提交的任务 @return 表示任务未完成的 Future
     * @throws RejectedExecutionException 如果无法安排任务执行
     * @throws NullPointerException 如果任务为空
     *
     * 执行有返回值得任务，任务的返回值为null，当任务执行完成后调用get()才会返回
     */
    Future<?> submit(Runnable task);

    /**
     * Executes the given tasks, returning a list of Futures holding
     * their status and results when all complete.
     * {@link Future#isDone} is {@code true} for each
     * element of the returned list.
     * Note that a <em>completed</em> task could have
     * terminated either normally or by throwing an exception.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks the collection of tasks
     * @param <T> the type of the values returned from the tasks
     * @return a list of Futures representing the tasks, in the same
     *         sequential order as produced by the iterator for the
     *         given task list, each of which has completed
     * @throws InterruptedException if interrupted while waiting, in
     *         which case unfinished tasks are cancelled
     * @throws NullPointerException if tasks or any of its elements are {@code null}
     * @throws RejectedExecutionException if any task cannot be
     *         scheduled for execution
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;

    /**
     * Executes the given tasks, returning a list of Futures holding
     * their status and results
     * when all complete or the timeout expires, whichever happens first.
     * {@link Future#isDone} is {@code true} for each
     * element of the returned list.
     * Upon return, tasks that have not completed are cancelled.
     * Note that a <em>completed</em> task could have
     * terminated either normally or by throwing an exception.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @param <T> the type of the values returned from the tasks
     * @return a list of Futures representing the tasks, in the same
     *         sequential order as produced by the iterator for the
     *         given task list. If the operation did not time out,
     *         each task will have completed. If it did time out, some
     *         of these tasks will not have completed.
     * @throws InterruptedException if interrupted while waiting, in
     *         which case unfinished tasks are cancelled
     * @throws NullPointerException if tasks, any of its elements, or
     *         unit are {@code null}
     * @throws RejectedExecutionException if any task cannot be scheduled
     *         for execution
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Executes the given tasks, returning the result
     * of one that has completed successfully (i.e., without throwing
     * an exception), if any do. Upon normal or exceptional return,
     * tasks that have not completed are cancelled.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks the collection of tasks
     * @param <T> the type of the values returned from the tasks
     * @return the result returned by one of the tasks
     * @throws InterruptedException if interrupted while waiting
     * @throws NullPointerException if tasks or any element task
     *         subject to execution is {@code null}
     * @throws IllegalArgumentException if tasks is empty
     * @throws ExecutionException if no task successfully completes
     * @throws RejectedExecutionException if tasks cannot be scheduled
     *         for execution
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException;

    /**
     * Executes the given tasks, returning the result
     * of one that has completed successfully (i.e., without throwing
     * an exception), if any do before the given timeout elapses.
     * Upon normal or exceptional return, tasks that have not
     * completed are cancelled.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @param <T> the type of the values returned from the tasks
     * @return the result returned by one of the tasks
     * @throws InterruptedException if interrupted while waiting
     * @throws NullPointerException if tasks, or unit, or any element
     *         task subject to execution is {@code null}
     * @throws TimeoutException if the given timeout elapses before
     *         any task successfully completes
     * @throws ExecutionException if no task successfully completes
     * @throws RejectedExecutionException if tasks cannot be scheduled
     *         for execution
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
