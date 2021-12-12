# Flume中的坑
错误打包方式
```text
1.打包不要用package
```
正确打包方式
```text
1.compiler:compiler
2.jar:jar
```
## 配置
D:\idea\apache-flume-1.8.0-bin\conf 下的配置文件参考

### example.conf
```bash
# example.conf: A single-node Flume configuration

# Name the components on this agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source
a1.sources.r1.type = netcat
a1.sources.r1.bind = localhost
a1.sources.r1.port = 44444

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
### 注意配置环境变量
### Windows命令
```bash 
flume-ng agent -c D:\idea\apache-flume-1.8.0-bin\conf -f D:\idea\apache-flume-1.8.0-bin\conf\example.conf -n a1
```
### 连接数据源发送exec数据命令
```bash
telnet localhost 44444
```