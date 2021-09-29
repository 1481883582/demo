package com.es.common;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.IOException;
import java.util.List;

@NoRepositoryBean
public interface ElasticSearchService<T, ID> extends ElasticsearchRepository<T, ID> {

    public JSONObject buildIndexSyntax(String IndexName);

    public boolean createIndex(String indexSyntax, String indexName);

    public boolean isExist(String indexName) throws IOException;

    public boolean updateIndex(String indexSyntax, String indexName);

    public int batchInsert(List<Class<?>> data);

    public IndexResponse insert(Class<?> data);

    public void deleteDataByQuery(String index, TermQueryBuilder query);

    public void deleteDataByQuery(String index, BoolQueryBuilder query);

    public SearchResponse search(SearchRequest searchRequest);

    public SearchResponse scrollSearch(SearchScrollRequest searchScrollRequest);

    public void searchAsy(SearchRequest searchRequest, ActionListener<SearchResponse> listener);

    public ClearScrollResponse clearScroll(ClearScrollRequest clearScrollRequest);

    public IndexResponse esPipelineFile(String json, String index, String pipeline) throws IOException;
}
