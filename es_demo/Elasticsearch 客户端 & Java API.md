# Elasticsearch 客户端 & Java API
## 简单介绍
- 与语言无关 支持的语言API https://www.elastic.co/guide/en/elasticsearch/client/index.html
- 官网 JavaAPI弃用 8.0版本会被删除 https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-api.html
- 推荐使用Java REST https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html
## 注意事项
- `TransportClient` 使用`transport`模块（**9300端口**）远程连接到 Elasticsearch 集群，客户端并不加入集群，而是通过获取单个或者多个transport地址来以轮询的方式与他们通信。
- `TransportClient`使用`transport`协议与Elasticsearch节点通信，如果客户端的版本和与其通信的ES实例的版本不同，就会出现兼容性问题。而`low-level REST`使用的是HTTP协议，可以与任意版本ES集群通信。`high-level REST`是基于`low-level REST`的。
## Maven依赖
```xml
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>7.12.1</version>
</dependency>
```
## [文档地址https://www.elastic.co/guide/en/elasticsearch/reference/current/documents-indices.html?baymax=rec&rogue=rec-1&elektra=guide](https://www.elastic.co/guide/en/elasticsearch/reference/current/documents-indices.html?baymax=rec&rogue=rec-1&elektra=guide)
# [Spring Data Elasticsearch](https://docs.spring.io/spring-data/elasticsearch/docs/4.2.4/reference/html/#preface)

## 官网

https://spring.io/projects/spring-data-elasticsearch

## 兼容性(必看)

https://docs.spring.io/spring-data/elasticsearch/docs/4.2.1/reference/html/#preface.requirements

## 文档地址

https://docs.spring.io/spring-data/elasticsearch/docs/4.2.1/reference/html/#reference

## 优缺点

- 优点：用统一的接口，适配所有不同的存储类型，学习成本低。
- 缺点：适配的版本要比原生的 API 要慢。这个取决于 Spring Data Elasticsearch 团队的开发速度。无法使用ES的一些新特性

## 注解

```java
@Document：在类级别应用，以指示该类是映射到数据库的候选类。最重要的属性包括：

indexName：用于存储此实体的索引的名称。它可以包含类似于“日志-#{T（java.time.LocalDate）.now（）.toString（）}”

type :映射类型。如果未设置，则使用该类的小写简单名称。（自4.0版起已弃用）

createIndex：标记是否在存储库引导时创建索引。默认值为true。请参阅自动创建带有相应映射的索引

versionType：版本管理的配置。默认值为外部 .

@Id：在字段级别应用，以标记用于标识的字段。

@Transient：默认情况下，存储或检索文档时，所有字段都映射到文档，此批注不包括该字段。

@PersistenceConstructor：标记在从数据库实例化对象时要使用的给定构造函数（甚至是包受保护的构造函数）。构造函数参数按名称映射到检索文档中的键值。

@Field：应用于字段级别并定义字段的属性，大多数属性映射到相应的Elasticsearch映射定义（以下列表不完整，请查看注释Javadoc以获取完整的参考）：

name：将在Elasticsearch文档中表示的字段的名称，如果未设置，则使用Java字段名称。

type：字段类型，可以是Text，关键字，Long，Integer，Short，Byte，Double，Float，Half_Float，Scaled_Float，日期，日期Nanos，Boolean，Binary，Integer_Range，Float_Range，Long_Range，DoubleˉRange，DateˉRange，Object，Nested，Ip，TokenCount，percollator，flatten，搜索。请参阅Elasticsearch映射类型

format：一个或多个内置日期格式，请参阅下一节格式数据映射 .

pattern：一个或多个自定义日期格式，请参阅下一节格式数据映射 .

store：标志是否应将原始字段值存储在Elasticsearch中，默认值为假 .

analyzer ,搜索分析器 ,normalizer用于指定自定义分析器和规格化器。

@GeoPoint：将字段标记为地理点如果字段是GeoPoint班级
```

## 操作类型

Spring Data Elasticsearch 使用多个接口来定义可以针对 Elasticsearch 索引调用的操作。

- `IndexOperations`定义索引级别的操作，例如创建或删除索引。
- `DocumentOperations`定义基于 id 存储、更新和检索实体的操作。
- `SearchOperations`定义使用查询搜索多个实体的操作
- `ElasticsearchOperations`结合了`DocumentOperations`和`SearchOperations`接口。