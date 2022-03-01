import lombok.extern.slf4j.Slf4j;
import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import java.util.Arrays;

/**
 * 接收Sink
 */
@Slf4j
public class FileSink extends AbstractSink implements Configurable {
    /**
     * 是否开启日志常量
     */
    private static final String LOG_FLAG = "logFlag";
    /**
     * 是否打开日志标记
     */
    private Boolean logFlag = Boolean.FALSE;

    /**
     * 获取配置文件
     *
     * @param context
     */
    @Override
    public void configure(Context context) {
        //获取是否打开日志
        logFlag = context.getBoolean(LOG_FLAG, Boolean.FALSE);
    }

    /**
     * 处理存储逻辑
     *
     * @return
     */
    @Override
    public Status process() {
        Status status;
        //获取通道
        Channel ch = getChannel();
        //创建事务
        Transaction txn = ch.getTransaction();
        //开启事务
        txn.begin();
        try {
            //获取事件
            Event event = ch.take();
            //执行任务
            task(event);
            // 事务提交
            txn.commit();
            // 设置状态
            status = Status.READY;
        } catch (Throwable t) {
            // 事务回滚
            txn.rollback();
            // 退避
            status = Status.BACKOFF;
            if (t instanceof Error) {
                log.error(t.getLocalizedMessage(), t);
                throw (Error) t;
            }
            log.error("保存数据异常, 数据正常回滚！ {}", t);
        } finally {
            //关闭事务
            txn.close();
        }
        return status;
    }

    /**
     * 执行任务
     * @param event
     */
    private void task(Event event){

        if ((event != null && event.getBody() != null)) {

            //打印日志
            if (logFlag) log.info(Arrays.toString(event.getBody()));

            //接受数据
            log.info(Arrays.toString(event.getBody()));
        }
    }
}


