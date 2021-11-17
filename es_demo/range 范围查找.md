# range 范围查找
gte：　　大于或等于
gt：　　 大于
lte：　　小于或等于
lt：　　   小于
boost：　　设置查询的提升值，默认为1.0
```text
GET contact/_search
{
  "query": {
    "range": {
      "martPrice": {
        "gte": 10,
        "lte": 200
      }
    }
  }
}
```
Java
```java
// 范围搜索  搜索大于等于10 并且小于等于200 的价格
QueryBuilder termQuery = QueryBuilders.rangeQuery("martPrice").gte(10).lte(200);
Iterable<Contact> contacts = contactESService.search(termQuery);

contacts.forEach((c)->{
    System.out.println(c.toString());
});
```
分词结果
```json
{
  "tokens" : [
    {
      "token" : "王",
      "start_offset" : 0,
      "end_offset" : 1,
      "type" : "<IDEOGRAPHIC>",
      "position" : 0
    },
    {
      "token" : "牌",
      "start_offset" : 1,
      "end_offset" : 2,
      "type" : "<IDEOGRAPHIC>",
      "position" : 1
    }
  ]
}
```