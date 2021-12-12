package com.flume.custom_interceptor.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.mortbay.util.ajax.JSON;

import java.util.List;


@Slf4j
public class CustomInterceptor implements Interceptor {

    /**
     * 拦截器所需的任何初始化/启动。
     */
    public void initialize() {
        log.info("开始加载自定义拦截器....");
    }

    /**
     * 拦截单个事件。
     * 参数： 事件–要拦截的事件
     * 返回： 原始或修改的事件，如果要删除该事件（即过滤掉），则为null
     */
    public Event intercept(Event event) {
        log.info(JSON.toString(event));
        event.getHeaders().put("自定义", "自定义");
        return event;
    }

    /**
     *拦截一批事件。
     * 参数： 事件–输入事件列表
     * 返回： 输出事件列表。输出列表的大小不得大于输入列表的大小（即仅转换和删除）。
     * 此外，此方法不能返回null。
     * 如果删除所有事件，则返回空列表。
     */
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }
        return list;
    }

    /**
     * 执行拦截器所需的任何关闭/关闭。
     */
    public void close() {
        log.info("销毁自定义拦截器....");
    }

    public static class Builder implements Interceptor.Builder {
        public Interceptor build() {
            return new CustomInterceptor();
        }

        public void configure(Context context) {
        }
    }
}
