package com.custom_source.source;

import lombok.SneakyThrows;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.nio.charset.StandardCharsets;

/**
 * 自定义数据源
 */
public class CustomSource extends AbstractSource implements Configurable, PollableSource {
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private String prefix;
    private String suffix;

    /**
     * 获取配置文件中的信息
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
        // Initialize the connection to the external client
    }

    @Override
    public void stop() {
        // Disconnect from external client and do any additional cleanup
        // (e.g. releasing resources or nulling-out field values) ..
    }

    /**
     * 发送数据源消息
     * @return
     * @throws EventDeliveryException
     */
    @SneakyThrows
    @Override
    public Status process() throws EventDeliveryException {

        Status status = null;

        try {

            //拼接数据
            StringBuilder sb = new StringBuilder()
                    .append(prefix)
                    .append(String.valueOf(System.currentTimeMillis()))
                    .append(suffix);


            //加载数据 并添加到事件中
            Event event = new SimpleEvent();
            event.setBody(sb.toString().getBytes(StandardCharsets.UTF_8));


            // 将事件存储到此源的关联通道中
            // 事件发送到数据源关联的对应通道
            getChannelProcessor().processEvent(event);

            //设置准备好状态
            status = Status.READY;
        } catch (Throwable t) {
            //日志异常，根据需要处理个别异常
            //设置退避状态
            status = Status.BACKOFF;

            //重新抛出所有错误
            if (t instanceof Error) {
                throw (Error) t;
            }
        }

        //半秒睡眠一次
        Thread.sleep(500);
        return status;
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }
}