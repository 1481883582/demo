package com.custom_sink.sink;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

/**
 * 自定义Sink
 */
@Slf4j
public class CustomSink extends AbstractSink implements Configurable {
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private String prefix;
    private String suffix;

    /**
     * 配置获取
     *
     * @param context
     */
    @Override
    public void configure(Context context) {
        //获取前缀 有默认值
        prefix = context.getString(PREFIX, "defaultPrefix");
        //获取后缀  无默认值
        suffix = context.getString(SUFFIX);
    }

    @Override
    public void start() {
        // Initialize the connection to the external repository (e.g. HDFS) that
        // this Sink will forward Events to ..
    }

    @Override
    public void stop() {
        // Disconnect from the external respository and do any
        // additional cleanup (e.g. releasing resources or nulling-out
        // field values) ..
    }

    /**
     * 数据接受
     *
     * @return
     * @throws EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        // 获取Channel
        Channel ch = getChannel();
        // 开启事务
        Transaction txn = ch.getTransaction();
        txn.begin();
        try {
            // This try clause includes whatever Channel operations you want to do

            Event event = ch.take();
            log.info(prefix + event.getBody().toString() + suffix);

            // Send the Event to the external repository.
            // storeSomeData(e);

            txn.commit();
            status = Status.READY;
        } catch (Throwable t) {
            txn.rollback();

            // Log exception, handle individual exceptions as needed

            status = Status.BACKOFF;

            // re-throw all Errors
            if (t instanceof Error) {
                throw (Error) t;
            }
        } finally {
            if (txn != null) {
                //关闭事务
                txn.close();
            }
        }
        return status;
    }
}
