package com.repository;

import com.entity.MsbCarInfo;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MsbCarInfoRepository extends ElasticsearchRepository<MsbCarInfo, String> {
    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    List<MsbCarInfo> findByName(String name);
}
