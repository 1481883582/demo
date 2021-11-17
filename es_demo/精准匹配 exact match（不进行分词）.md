# 精准匹配 exact match（不进行分词）
## term 匹配和搜索完全相等的结果
由于小时 被分词 成"小"与"时"了，精准搜索是搜索不到的
```text  
GET contact/_search
{
  "query": {
    "term": {
      "subName": "小"
    }
  }
}
```
Java
```java
//精准搜索  搜索条件没被分词 如果搜索内容被分词，只能搜到单个词
QueryBuilder termQuery = QueryBuilders.termQuery("subName", "小");
Iterable<Contact> contacts = contactESService.search(termQuery);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```
想搜索到未被分词的结果(搜索条件 与 结构都不被分词)
```text  
GET contact/_search
{
  "query": {
    "term": {
      "brandName.keyword": "Medios"
    }
  }
}
```
## terms 搜错包含月或新的精准搜索
```text
GET contact/_search
{
  "query": {
    "terms": {
      "subName": [
        "月",
        "新"
      ]
    }
  }
}
```
Java
```java
// 精准搜索多个词
QueryBuilder termQuery = QueryBuilders.termsQuery("subName", "月", "新");
Iterable<Contact> contacts = contactESService.search(termQuery);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```