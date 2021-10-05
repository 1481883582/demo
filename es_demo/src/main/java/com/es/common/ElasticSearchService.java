package com.es.common;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ElasticSearchService<T, ID> extends ElasticsearchRepository<T, ID> {
}
