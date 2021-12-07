# Flume
主要应用于大数据传输
## Flume概念
```text
Flume 最早是Cludera提供的日志收集系统，后贡献给Apache。所以目前是Apache下的项目，Flume支持在日志系统中指定各类数据发送方，用于收集数据。

Flume 是一个高可用的，高可靠的，鲁棒性（robust健壮性），分布式的海量日志采集、聚合和传输的系统，Flume支持在日志系统中定制各类数据发送方，用于收集数据（source）；同时，Flume提供对数据进行简单处理，并写到各种数据接受仿的能力（sink）。
```
![img.png](src/main/resources/img/img.png)
```text
flume是分布式的日志收集系统，它将各个服务器中的数据收集起来并送到指定的地方去，比如说送到图中的HDFS，简单来说flume就是收集日志的。
```
### Agent
```text
Agent 是一个JVM进程，它以事件的形式将数据从源头发送至目的地
Agent 主要有3个部分组成，Source、Channel、Sink
```
### Source
```text
Source 是负责接口数据到Flume Agent的组件。Source组件可以处理各种类型、各种格式日志数据。包括avro、thrift、exec、jms、spooling directory、netcat、sequence generator、syslog、http、legacy。
```
### Sink
```text
Sink 不断的轮询Channel中的事件且批量的移除它们，并将这些时间批量写入到存储中或索引系统、或者被发送到另一个Flume Agent
Sink 组件目的包括hdfs、logger、acro、thrift、ipc、file、HBase、solr、自定义
```
### Channel
````text
Channel 是位于Source和Sink之间的缓冲区、因此，Channel允许Source和Sink运作在不同的速率上。Channel是线程安全的，可以同时处理几个Source的写入操作和几个Sink的读写操作
Flume 自带两种Channel，Memory Channel和File Channel以及KafkaChannel。
File Channel 将所有事件写到磁盘。因此在程序关闭或机器宕机的情况下不会丢失数据
````
### Event
```text
传输单元，Flume数据传输的基本单元，以Event的形式将珊瑚橘从源头送至目的地。Event由Header和Body两部分组成。Header用来存放改event的一些属性，为K-V结构。Body用来存放该数据。形式为字节数组。
```
![event.png](src/main/resources/img/event.png)
## 安装
## 配置
```bash
# example.conf: A single-node Flume configuration
# 每个文件都是一个Agent  每个Agent都是一个JVM
# Name the components on this agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source
# 数据源的配置
a1.sources.r1.type = netcat
a1.sources.r1.bind = localhost
a1.sources.r1.port = 44444

# Describe the sink
# sink配置
a1.sinks.k1.type = logger

# Use a channel which buffers events in memory
# channel的配置
# memory内存 类型
a1.channels.c1.type = memory
# capacity 通道中最大事件数
a1.channels.c1.capacity = 1000
# transactionCapacity 每个事务通道从源接受或发送给接收器的最大事件数
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel
# 绑定 sources-sinks-channels之间的关系
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
```