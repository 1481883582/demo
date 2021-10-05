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
}
