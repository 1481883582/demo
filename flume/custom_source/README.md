# [官网demo](https://flume.apache.org/releases/content/1.9.0/FlumeDeveloperGuide.html#source)
## 配置
example.conf
```bash
# example.conf: A single-node Flume configuration

# Name the components on this agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source
a1.sources.r1.type = com.custom_source.source.CustomSource
a1.sources.r1.prefix = 1
a1.sources.r1.suffix = 1

# 拦截器
a1.sources.r1.interceptors = i1
a1.sources.r1.interceptors.i1.type = com.flume.custom_interceptor.interceptor.CustomInterceptor$Builder

# Describe the sink
a1.sinks.k1.type = logger

# Use a channel which buffers events in memory
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
```