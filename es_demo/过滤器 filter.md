# 过滤器 filter （筛选数据）
与query相比
query：过程导向，计算评分消耗性能
filter：结果导向，不计算评分，有缓存机制
## 第一种嵌套filter
```text
GET contact/_search
{
  "query": {
    "constant_score": {
      "filter": {
        "term": {
          "subName": {
            "value": "小",
            "boost": 1
          }
        }
      },
      "boost": 1
    }
  }
}
```
Java
```java
QueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("subName", "小"));
log.info(queryBuilder.toString());
Iterable<Contact> contacts = contactESService.search(queryBuilder);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```
## 第二种嵌套 filter
```text
GET contact/_search
{
  "query": {
    "bool": {
      "filter": [
        {
          "term": {
            "subName": {
              "value": "小",
              "boost": 1
            }
          }
        }
      ],
      "adjust_pure_negative": true,
      "boost": 1
    }
  }
}
```
Java
```java
QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("subName", "小"));
log.info(queryBuilder.toString());
Iterable<Contact> contacts = contactESService.search(queryBuilder);


contacts.forEach((c)->{
    System.out.println(c.toString());
});
```