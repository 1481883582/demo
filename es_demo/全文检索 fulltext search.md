# 全文检索 fulltext search || DSL（Domain Specific Language）
## match: 普通搜索，分词包含最多的最前
按照评分排序
```text
GET contact/_search
{
  "query": {
    "match": {
      "subName": "舒服"
    }
  }
}
```
Java
```Java
//单一字段分词搜索
QueryBuilder queryBuilder = QueryBuilders.matchQuery("subName", "舒服");
Iterable<Contact> contacts = contactESService.search(matchQueryBuilder);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```
## match_all: 搜索所有
```text
GET contact/_search
{
  "query": {
    "match_all": {}
  }
}
```
Java
```java
//查询所有
QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
Iterable<Contact> contacts = contactESService.search(queryBuilder);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```
## multi_match: 多个字段搜索一个内容
类似于  mysql   a in（1）or b in（1）
```text
GET contact/_search
{
  "query": {
    "multi_match": {
      "query": "系列",
      "fields": ["itemName", "subName"]
    }
  }
}
```
Java
```java
//subName or itemName contains "系列"
QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("系列", "subName", "itemName");
Iterable<Contact> contacts = contactESService.search(queryBuilder);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```
## match_phrase: 短语搜索，搜索语句不会被分词
```text
GET contact/_search
{
  "query": {
    "match_phrase": {
      "subName": "喜欢小直径"
    }
  }
}
```
Java
```java
//搜索value不会被分词  
QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("subName", "喜欢小直径");
Iterable<Contact> contacts = contactESService.search(queryBuilder);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```