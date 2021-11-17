# ES
## 版本选择
|  Spring Data  Release Train | Spring Data Elasticsearch  | Elasticsearch  | Spring Framework  | Spring Boot |
|  ----  | ----  |  ----  |  ----  |  ----  |
| 2021.0 (Pascal)  | 4.2.1 | 7.12.1 | 5.3.7 | 2.5.x |
| 2020.0 (Ockham)  | 4.1.x | 7.9.3 | 5.3.2 | 2.4.x | 
| Neumann  | 4.0.x | 7.6.2 | 5.2.12 | 2.3.x |
| Moore  | 3.2.x | 6.8.12 | 5.2.12 | 2.2.x |
| Lovelace[1]  | 3.1.x[1] | 6.2.2 | 5.1.19 | 2.1.x |
| Kay[1]  | 3.0.x[1] | 5.5.0 | 5.0.13 | 2.0.x |
| Ingalls[1]  | 2.1.x[1] | 2.4.0 | 4.3.25 | 1.5.x |


表格仅供参考，来自https://docs.spring.io/spring-data/elasticsearch/docs/4.2.4/reference/html/#preface.metadata
## 集群的健康检查
### 健康状态
| 颜色 | 状态 | 描述 |
| ---- | ---- | ---- |
| 绿色 | Green | 所有Primary和Replica均为active，集群状态健康 |
| 黄色 | Yellow | 至少一个Replica不可用，但是所有Primary均为active，数据仍然是可以保证完整性的  |
| 红色 | Red | 至少一个Primary为不可用状态，数据不完整，集群不可用 |
### 健康值查询
#### GET [_cat/health](127.0.0.1:9200/_cat/health)
```json
1633439110 13:05:10 docker-cluster green 1 1 1 1 0 0 0 0 - 100.0%
```
#### GET [_cat/health?v](http://cc:9200/_cat/health?v)
```json
epoch      timestamp cluster        status node.total node.data shards pri relo init unassign pending_tasks max_task_wait_time active_shards_percent
1633439147 13:05:47  docker-cluster green           1         1      1   1    0    0        0             0                  -                100.0%
```
```java
epoch // 1970年1月1日 到现在的毫秒数(换算北京时间需要+8小时)
timestamp //当前时间  (换算北京时间需要+8小时)
cluster//集群名称
status//集群当前的健康状态
node.total//当前包含的所有节点数
node.data//当前存放数据的节点
shards//当前分片数量
pri//主副本数量
relo//迁移中的分片数量
init//初始化中的分片数量
unassign//未分配的分片数量
pending_tasks//当前任务数量
max_task_wait_time//最大的任务等待时间
active_shards_percent//当前活动分片的百分比（当前工作分片的百分比）
```
#### GET [_cluster/health](127.0.0.1:9200/_cluster/health)
```json
{
  "cluster_name":"docker-cluster",
  "status":"green",
  "timed_out":false,
  "number_of_nodes":1,
  "number_of_data_nodes":1,
  "active_primary_shards":1,
  "active_shards":1,
  "relocating_shards":0,
  "initializing_shards":0,
  "unassigned_shards":0,
  "delayed_unassigned_shards":0,
  "number_of_pending_tasks":0,
  "number_of_in_flight_fetch":0,
  "task_max_waiting_in_queue_millis":0,
  "active_shards_percent_as_number":100
}
```
### 分片与副本
- index对应多个数据分片（分片组合等于完整数据）
- 数据分片对应多个数据副本（副本等于数据数据的复制）
- 所有副本数据均为active，active相同数据有一个Primary(主副本)与多个Replica(副本)
## Elasticsearch核心概念
### 全文搜索引擎
```text
自然语言处理、百度、谷歌、爬虫、大数据处理
```
### 垂直搜索引擎（垂直领域,有目的有范围的搜索）
```text
电商平台、OA、某领域
```
### 搜索引擎的要求？
#### 查询速度快（返回速度）
##### 高效的压缩算法
###### FOR压缩算法
##### 快速的编码和解码速度
#### 结果准度（准却度）
##### BM25
##### TF-IDF
#### 检索结果丰富（广度、召回率）
### 倒排数据结构图
![img_3.png](src/main/resources/img/img_3.png)
### Elasticsearch存储
从图片里看到uuid
![img.png](src/main/resources/img/img.png)
进入docker 中的 ES
```bash
docker exec -ti elasticsearch  /bin/bash
```
进入数据存放目录
```bash
cd data/nodes/0/indices/
```
输入 `ls -1`可以看到如下图片内容对应uuid
![img_1.png](src/main/resources/img/img_1.png)

进入对应`uuid`目录后
```bash 
cd 0/index
```
### Lucene全文检索流畅图（ES的底层是Lucene）
![img_2.png](src/main/resources/img/img_2.png)
### 倒排索引图
![3-1倒排索引2.jpg](src/main/resources/img/3-1倒排索引2.jpg)
到达对应index 数据存储目录
## 应用
### REST CRUD
#### 创建索引&插入数据
##### 创建索引 PUT [/[index]](127.0.0.1:9200/[index])
##### 创建索引 PUT [/[index]?pretty](127.0.0.1:9200/[index]?pretty)
##### （全量替换）覆盖式插入数据 PUT [/[index]/[type]/[id]](127.0.0.1:9200/[index]/[type]/[id])
```json
{
  "id":2001,
  "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d29b8512a04f.jpg",
  "itemName":"BarieCat“柚屿”系列",
  "subName":"舒适的非离子材质融合充满复古韵味的混血花纹；虚化的深色边缘与瞳孔的轮廓完美融合；搭配低明度高显色的基色将酷感混血进行到底。",
  "brandName":"Bariecat"
}
```
增加成功  返回json
```json
{
"_index": "contact",
"_type": "_doc",
"_id": "2001",
"_version": 1,
"result": "created",
"_shards": {
"total": 1,
"successful": 1,
"failed": 0
},
"_seq_no": 1497,
"_primary_term": 1
}
```
#### 查询索引
##### 查新索引 GET _cat/indices?v
返回数据
```text
health status index   uuid                   pri rep docs.count docs.deleted store.size pri.store.size
green  open   contact GrV-QY3cSXGoNNx9xlNDEw   1   0       1497            0    685.4kb        685.4kb
```
##### 查询所有数据 GET [/_search](127.0.0.1:9200/[_search])
查询json
```json
{
    "query":{
        "match_all":{

        }
    }
}
```
返回json
```json
{
 // 代表消耗 6毫秒
    "took":6,
  //代表当前请求是否超时
    "timed_out":false,
  //分片参数
    "_shards":{
      //一共1个
        "total":1,
      //成功了1个
        "successful":1,
      //跳过了0个
        "skipped":0,
      //失败了0个
        "failed":0
    },
  //返回结果
    "hits":{
        "total":{
          //查到的总数量
            "value":1497,
          //关系式
            "relation":"eq"
        },
      //当前数据最高评分
        "max_score":1,
      //返回结果
        "hits":[
            {
                //索引名
                "_index":"contact",
                //索引类型
                "_type":"_doc",
                //索引id
                "_id":"3",
                //当前数据评分
                "_score":1,
                //具体数据
                "_source":{
                    //对应的类
                    "_class":"com.entity.Contact",
                    //具体参数
                    "id":3,
                    "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d29b8512a04f.jpg",
                    "itemName":"BarieCat“柚屿”系列",
                    "subName":"舒适的非离子材质融合充满复古韵味的混血花纹；虚化的深色边缘与瞳孔的轮廓完美融合；搭配低明度高显色的基色将酷感混血进行到底。",
                    "brandName":"Bariecat"
                }
            },
            {
                "_index":"contact",
                "_type":"_doc",
                "_id":"4",
                "_score":1,
                "_source":{
                    "_class":"com.entity.Contact",
                    "id":4,
                    "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d297c0fa4f48.jpg",
                    "itemName":"溪悦creekeye呦呦灰_副本",
                    "subName":"进口MPC高保湿型非离子，轻薄无感！",
                    "brandName":"溪悦Creek eye"
                }
            },
            {
                "_index":"contact",
                "_type":"_doc",
                "_id":"5",
                "_score":1,
                "_source":{
                    "_class":"com.entity.Contact",
                    "id":5,
                    "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d297b1dab839.jpg",
                    "itemName":"溪悦creekeye呦呦灰",
                    "subName":"进口MPC高保湿型非离子，轻薄无感！",
                    "brandName":"溪悦Creek eye"
                }
            }
        ]
    }
}
```
##### 查询所有数据 GET [/[index]/_search](127.0.0.1:9200/[index]/_search)
查询json
```json
{
    "query":{
        "match_all":{

        }
    }
}
```
返回json
```json
{
  "took":6,
  "timed_out":false,
  "_shards":{
    "total":1,
    "successful":1,
    "skipped":0,
    "failed":0
  },
  "hits":{
    "total":{
      "value":1497,
      "relation":"eq"
    },
    "max_score":1,
    "hits":[
      {
        "_index":"contact",
        "_type":"_doc",
        "_id":"3",
        "_score":1,
        "_source":{
          "_class":"com.entity.Contact",
          "id":3,
          "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d29b8512a04f.jpg",
          "itemName":"BarieCat“柚屿”系列",
          "subName":"舒适的非离子材质融合充满复古韵味的混血花纹；虚化的深色边缘与瞳孔的轮廓完美融合；搭配低明度高显色的基色将酷感混血进行到底。",
          "brandName":"Bariecat"
        }
      },
      {
        "_index":"contact",
        "_type":"_doc",
        "_id":"4",
        "_score":1,
        "_source":{
          "_class":"com.entity.Contact",
          "id":4,
          "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d297c0fa4f48.jpg",
          "itemName":"溪悦creekeye呦呦灰_副本",
          "subName":"进口MPC高保湿型非离子，轻薄无感！",
          "brandName":"溪悦Creek eye"
        }
      },
      {
        "_index":"contact",
        "_type":"_doc",
        "_id":"5",
        "_score":1,
        "_source":{
          "_class":"com.entity.Contact",
          "id":5,
          "picUrl":"http://img01.02d.com/Public/Upload/image/20190713/5d297b1dab839.jpg",
          "itemName":"溪悦creekeye呦呦灰",
          "subName":"进口MPC高保湿型非离子，轻薄无感！",
          "brandName":"溪悦Creek eye"
        }
      }
    ]
  }
}
```
#### 删除索引
##### 删除索引 DELETE [/[index]?pretty](127.0.0.1:9200/[index])
##### 删除索引 DELETE [/[index]/[type]/[id]](127.0.0.1:9200/[index]/[type]/[id])
#### 修改
##### 指定数据修改 POST [/[index]/[type]/[id]/_update 或 /[index]/_update/[id]](127.0.0.1:9200/[index]/[type]/[id]/_update)
```json
{
  "doc": {
    "itemName":"BarieCat“柚屿”系列"
  }
}
```
## [Mapping](https://gitee.com/valuenull/java-demo/blob/master/es_demo/Mapping.md)
## [搜索和查询简述](https://gitee.com/valuenull/java-demo/blob/master/es_demo/%E6%90%9C%E7%B4%A2%E5%92%8C%E6%9F%A5%E8%AF%A2%E7%AE%80%E8%BF%B0.md)
## [全文检索 fulltext search || DSL（Domain Specific Language）](https://gitee.com/valuenull/java-demo/blob/master/es_demo/%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2%20fulltext%20search.md)
## [精准匹配 exact match（不进行分词）](https://gitee.com/valuenull/java-demo/blob/master/es_demo/%E7%B2%BE%E5%87%86%E5%8C%B9%E9%85%8D%20exact%20match%EF%BC%88%E4%B8%8D%E8%BF%9B%E8%A1%8C%E5%88%86%E8%AF%8D%EF%BC%89.md)
## [range 范围查找](https://gitee.com/valuenull/java-demo/blob/master/es_demo/range%20%E8%8C%83%E5%9B%B4%E6%9F%A5%E6%89%BE.md)
## [过滤器 filter （筛选数据）](https://gitee.com/valuenull/java-demo/blob/master/es_demo/%E8%BF%87%E6%BB%A4%E5%99%A8%20filter.md)
## [组合查询](https://gitee.com/valuenull/java-demo/blob/master/es_demo/%E7%BB%84%E5%90%88%E6%9F%A5%E8%AF%A2.md)
## [分词器](https://gitee.com/valuenull/java-demo/blob/master/es_demo/%E5%88%86%E8%AF%8D%E5%99%A8.md)
## [聚合]()
## [Elasticsearch 客户端 & Java API](https://gitee.com/valuenull/java-demo/blob/master/es_demo/Elasticsearch%20%E5%AE%A2%E6%88%B7%E7%AB%AF%20&%20Java%20API.md)
