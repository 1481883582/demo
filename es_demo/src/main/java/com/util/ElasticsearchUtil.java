//package com.util;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.Strings;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.common.unit.DistanceUnit;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @Description: Elasticsearch工具类
// */
//
///**
// * 精确查询（必须完全匹配上）
// * 单个匹配termQuery
// *
// * //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词.
// * QueryBuilder queryBuilder=QueryBuilders.termQuery("fieldName", "fieldlValue");
// * //分词查询，采用默认的分词器
// * QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("fieldName", "fieldlValue");
// *
// * 多个匹配
// * //不分词查询，参数1： 字段名，参数2：多个字段查询值,因为不分词，所以汉字只能查询一个字，英语是一个单词.
// * QueryBuilder queryBuilder=QueryBuilders.termsQuery("fieldName", "fieldlValue1","fieldlValue2...");
// * //分词查询，采用默认的分词器
// * QueryBuilder queryBuilder= QueryBuilders.multiMatchQuery("fieldlValue", "fieldName1", "fieldName2", "fieldName3");
// * //匹配所有文件，相当于就没有设置查询条件
// * QueryBuilder queryBuilder=QueryBuilders.matchAllQuery();
// */
//
///**
// * 模糊查询（只要包含即可）
// *  //模糊查询常见的5个方法如下
// *         //1.常用的字符串查询
// *         QueryBuilders.queryStringQuery("fieldValue").field("fieldName");//左右模糊
// *         //2.常用的用于推荐相似内容的查询
// *         QueryBuilders.moreLikeThisQuery(new String[] {"fieldName"}).addLikeText("pipeidhua");//如果不指定filedName，则默认全部，常用在相似内容的推荐上
// *         //3.前缀查询  如果字段没分词，就匹配整个字段前缀
// *         QueryBuilders.prefixQuery("fieldName","fieldValue");
// *         //4.fuzzy query:分词模糊查询，通过增加fuzziness模糊属性来查询,如能够匹配hotelName为tel前或后加一个字母的文档，fuzziness 的含义是检索的term 前后增加或减少n个单词的匹配查询
// *         QueryBuilders.fuzzyQuery("hotelName", "tel").fuzziness(Fuzziness.ONE);
// *         //5.wildcard query:通配符查询，支持* 任意字符串；？任意一个字符
// *         QueryBuilders.wildcardQuery("fieldName","ctr*");//前面是fieldname，后面是带匹配字符的字符串
// *         QueryBuilders.wildcardQuery("fieldName","c?r?");
// */
//
///**
// * 范围查询
// *         //闭区间查询
// *         QueryBuilder queryBuilder0 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");
// *         //开区间查询
// *         QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2").includeUpper(false).includeLower(false);//默认是true，也就是包含
// *         //大于
// *         QueryBuilder queryBuilder2 = QueryBuilders.rangeQuery("fieldName").gt("fieldValue");
// *         //大于等于
// *         QueryBuilder queryBuilder3 = QueryBuilders.rangeQuery("fieldName").gte("fieldValue");
// *         //小于
// *         QueryBuilder queryBuilder4 = QueryBuilders.rangeQuery("fieldName").lt("fieldValue");
// *         //小于等于
// *         QueryBuilder queryBuilder5 = QueryBuilders.rangeQuery("fieldName").lte("fieldValue");
// */
//
///**
// * 组合查询/多条件查询/布尔查询
// *
// * QueryBuilders.boolQuery()
// * QueryBuilders.boolQuery().must();//文档必须完全匹配条件，相当于and
// * QueryBuilders.boolQuery().mustNot();//文档必须不匹配条件，相当于not
// * QueryBuilders.boolQuery().should();//至少满足一个条件，这个文档就符合should，相当于or
// */
//@Service
//@Slf4j
//public class ElasticsearchUtil {
//    //最好不要自定义id 会影响插入速度。
//
//    @Resource
//    @Qualifier("restHighLevelClient")
//    private RestHighLevelClient restHighLevelClient;
//
//    /**
//     * 创建索引
//     * @param index
//     * @return
//     */
//    public  boolean createIndex(String index) throws IOException {
//        if(isIndexExist(index)){
//            log.error("Index is  exits!");
//            return false;
//        }
//        //1.创建索引请求
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        //2.执行客户端请求
//        CreateIndexResponse response = restHighLevelClient.indices()
//                .create(request, RequestOptions.DEFAULT);
//        return response.isAcknowledged();
//    }
//
//    /**
//     * 判断索引是否存在
//     * @param index
//     * @return
//     */
//    public  boolean isIndexExist(String index) throws IOException {
//        GetIndexRequest request = new GetIndexRequest(index);
//        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
//    }
//
//    /**
//     * 删除索引
//     * @param index
//     * @return
//     */
//    public  boolean deleteIndex(String index) throws IOException {
//        if(!isIndexExist(index)) {
//            log.error("Index is not exits!");
//            return false;
//        }
//        DeleteIndexRequest request = new DeleteIndexRequest(index);
//        AcknowledgedResponse delete = restHighLevelClient.indices()
//                .delete(request, RequestOptions.DEFAULT);
//        return delete.isAcknowledged();
//    }
//
//    /**
//     * 数据添加，自定义id
//     * @param object 要增加的数据
//     * @param index      索引，类似数据库
//     * @param id         数据ID,为null时es随机生成
//     * @return
//     */
//    public String addData(Object object, String index, String id) throws IOException {
//        //创建请求
//        IndexRequest request = new IndexRequest(index);
//        //规则 put /test_index/_doc/1
//        request.id(id);
//        request.timeout(TimeValue.timeValueSeconds(1));
//        //将数据放入请求 json
//        IndexRequest source = request.source(JSON.toJSONString(object), XContentType.JSON);
//        //客户端发送请求
//        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
//        return response.getId();
//    }
//
//    /**
//     * 数据添加 随机id
//     * @param object 要增加的数据
//     * @param index      索引，类似数据库
//     * @return
//     */
//    public String addData(Object object, String index) throws IOException {
//        return addData(object, index, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
//    }
//
//    /**
//     * 通过ID删除数据
//     * @param index 索引，类似数据库
//     * @param id    数据ID
//     * @return
//     */
//    public void deleteDataById(String index,String id) throws IOException {
//        DeleteRequest request = new DeleteRequest(index, id);
//        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
//    }
//
//    /**
//     * 通过ID 更新数据
//     * @param object     要更新数据
//     * @param index      索引，类似数据库
//     * @param id         数据ID
//     * @return
//     */
//    public void updateDataById(Object object, String index, String id) throws IOException {
//        UpdateRequest update = new UpdateRequest(index, id);
//        update.timeout("1s");
//        update.doc(JSON.toJSONString(object), XContentType.JSON);
//        restHighLevelClient.update(update, RequestOptions.DEFAULT);
//    }
//
//    /**
//     * 通过ID获取数据
//     * @param index  索引，类似数据库
//     * @param id     数据ID
//     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
//     * @return
//     */
//    public Map<String,Object> searchDataById(String index, String id, String fields) throws IOException {
//        GetRequest request = new GetRequest(index, id);
//        if (StringUtils.isNotEmpty(fields)){
//            //只查询特定字段。如果需要查询所有字段则不设置该项。
//            request.fetchSourceContext(new FetchSourceContext(true,fields.split(","), Strings.EMPTY_ARRAY));
//        }
//        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
//        return response.getSource();
//    }
//
//    /**
//     * 通过ID判断文档是否存在
//     * @param index  索引，类似数据库
//     * @param id     数据ID
//     * @return
//     */
//    public boolean existsById(String index,String id) throws IOException {
//        GetRequest request = new GetRequest(index, id);
//        //不获取返回的_source的上下文
//        request.fetchSourceContext(new FetchSourceContext(false));
//        request.storedFields("_none_");
//        return restHighLevelClient.exists(request, RequestOptions.DEFAULT);
//    }
//
//    /**
//     * 批量插入false成功
//     * @param index  索引，类似数据库
//     * @param objects     数据
//     * @return
//     */
//    public boolean bulkPost(String index, List<?> objects) {
//        BulkRequest bulkRequest = new BulkRequest();
//        BulkResponse response=null;
//        //最大数量不得超过20万
//        for (Object object: objects) {
//            IndexRequest request = new IndexRequest(index);
//            request.source(JSON.toJSONString(object), XContentType.JSON);
//            bulkRequest.add(request);
//        }
//        try {
//            response=restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response.hasFailures();
//    }
//
//    /**
//     * 根据经纬度查询范围查找location 经纬度字段，distance 距离中心范围KM，lat  lon 圆心经纬度
//     * @param index
//     * @param longitude
//     * @param latitude
//     * @param distance
//     * @return
//     */
//    public SearchResponse geoDistanceQuery(String index, Float  longitude, Float latitude, String distance) throws IOException {
//
//        if(longitude == null || latitude == null){
//            return null;
//        }
//        //拼接条件
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
////        QueryBuilder isdeleteBuilder = QueryBuilders.termQuery("isdelete", false);
//        // 以某点为中心，搜索指定范围
//        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
//        distanceQueryBuilder.point(latitude, longitude);
//        //查询单位：km
//        distanceQueryBuilder.distance(distance, DistanceUnit.KILOMETERS);
//        boolQueryBuilder.filter(distanceQueryBuilder);
////        boolQueryBuilder.must(isdeleteBuilder);
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(boolQueryBuilder);
//
//        SearchRequest searchRequest = new SearchRequest(index);
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
//        return searchResponse;
//    }
//
//    /**
//     * 获取低水平客户端
//     * @return
//     */
//    public RestClient getLowLevelClient() {
//        return restHighLevelClient.getLowLevelClient();
//    }
//
//    /**
//     * 高亮结果集 特殊处理
//     * map转对象 JSONObject.parseObject(JSONObject.toJSONString(map), Content.class)
//     * @param searchResponse
//     * @param highlightField
//     */
//    private  List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
//        //解析结果
//        ArrayList<Map<String,Object>> list = new ArrayList<>();
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            Map<String, HighlightField> high = hit.getHighlightFields();
//            HighlightField title = high.get(highlightField);
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//原来的结果
//            //解析高亮字段,将原来的字段换为高亮字段
//            if (title!=null){
//                Text[] texts = title.fragments();
//                String nTitle="";
//                for (Text text : texts) {
//                    nTitle+=text;
//                }
//                //替换
//                sourceAsMap.put(highlightField,nTitle);
//            }
//            list.add(sourceAsMap);
//        }
//        return list;
//    }
//
//    /**
//     * 查询并分页
//     * @param index          索引名称
//     * @param query          查询条件
//     * @param size           文档大小限制
//     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
//     * @param sortField      排序字段
//     * @param highlightField 高亮字段
//     * @return
//     */
//    public  List<Map<String, Object>> searchListData(String index,
//                                                     SearchSourceBuilder query,
//                                                     Integer size,
//                                                     Integer from,
//                                                     String fields,
//                                                     String sortField,
//                                                     String highlightField) throws IOException {
//        SearchRequest request = new SearchRequest(index);
//        SearchSourceBuilder builder = query;
//        if (StringUtils.isNotEmpty(fields)){
//            //只查询特定字段。如果需要查询所有字段则不设置该项。
//            builder.fetchSource(new FetchSourceContext(true,fields.split(","),Strings.EMPTY_ARRAY));
//        }
//        from = from <= 0 ? 0 : from*size;
//        //设置确定结果要从哪个索引开始搜索的from选项，默认为0
//        builder.from(from);
//        builder.size(size);
//        if (StringUtils.isNotEmpty(sortField)){
//            //排序字段，注意如果proposal_no是text类型会默认带有keyword性质，需要拼接.keyword
//            builder.sort(sortField+".keyword", SortOrder.ASC);
//        }
//        //高亮
//        HighlightBuilder highlight = new HighlightBuilder();
//        highlight.field(highlightField);
//        //关闭多个高亮
//        highlight.requireFieldMatch(false);
//        highlight.preTags("<span style='color:red'>");
//        highlight.postTags("</span>");
//        builder.highlighter(highlight);
//        //不返回源数据。只有条数之类的数据。
////        builder.fetchSource(false);
//        request.source(builder);
//        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//        log.error("=="+response.getHits().getTotalHits());
//        if (response.status().getStatus() == 200) {
//            // 解析对象
//            return setSearchResponse(response, highlightField);
//        }
//        return null;
//    }
//}
//
