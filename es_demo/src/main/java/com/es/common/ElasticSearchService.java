package com.es.common;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ElasticSearchService<T, ID> extends ElasticsearchRepository<T, ID> {
    /**
     * @return RestHighLevelClient
     */
    RestHighLevelClient getRestHighLevelClient();

    /**
     *
     * @return ElasticsearchRestTemplate
     */
    ElasticsearchRestTemplate getElasticsearchRestTemplate();
}
