package com.es.common.impl;


import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.es.common.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import static org.elasticsearch.index.query.QueryBuilders.idsQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Slf4j
public abstract class AbstractElasticSearchServiceImpl<T, ID> implements ElasticSearchService<T, ID>{

    protected RestHighLevelClient restHighLevelClient;

    //??????????????? RestHighLevelClient  ElasticsearchRestTemplate
    protected ElasticsearchOperations operations;

    protected ElasticsearchRestTemplate elasticsearchRestTemplate;

    //?????? ????????????  ??????????????????????????????
    protected IndexOperations indexOperations;

    //???????????????
    protected Class<T> entityClass;

    //????????????
    protected @Nullable ElasticsearchEntityInformation<T, ID> entityInformation;

    //??????
    protected RepositoryFactorySupport elasticsearchRepositoryFactory;


    public AbstractElasticSearchServiceImpl(RestHighLevelClient client, ElasticsearchOperations operations) {
        this.entityClass = resolveReturnedClassFromGenericType();
        this.restHighLevelClient = client;
        this.operations = operations;
        this.indexOperations = operations.indexOps(this.entityClass);
        this.elasticsearchRepositoryFactory = new ElasticsearchRepositoryFactory(this.operations);
        this.entityInformation = (ElasticsearchEntityInformation<T, ID>) elasticsearchRepositoryFactory.getEntityInformation(entityClass);

        //????????? ElasticsearchRestTemplate
        if(this.operations instanceof ElasticsearchRestTemplate) this.elasticsearchRestTemplate = (ElasticsearchRestTemplate) this.operations;

        try {
            if (createIndexAndMapping() && !indexOperations.exists()) {
                createIndex();
                putMapping();
            }
        } catch (Exception exception) {
            log.warn("Cannot create index: {}", exception.getMessage());
        }
    }

    @Nullable
    protected ID extractIdFromBean(T entity) {
        return entityInformation.getId(entity);
    }

    /**
     * ????????????
     */
    public void createIndex() {
        indexOperations.create();
    }

    /**
     * ????????????
     */
    public void putMapping() {
        indexOperations.putMapping(indexOperations.createMapping(entityClass));
    }

    private boolean createIndexAndMapping() {

        final ElasticsearchPersistentEntity<?> entity = operations.getElasticsearchConverter().getMappingContext()
                .getRequiredPersistentEntity(getEntityClass());
        return entity.isCreateIndexAndMapping();
    }

    /**
     * @return RestHighLevelClient
     */
    public RestHighLevelClient getRestHighLevelClient(){
        return this.restHighLevelClient;
    }

    /**
     *
     * @return ElasticsearchRestTemplate
     */
    public ElasticsearchRestTemplate getElasticsearchRestTemplate(){
        return this.elasticsearchRestTemplate;
    }

    /**
     * ??????id ??????
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(operations.get(stringIdRepresentation(id), getEntityClass(), getIndexCoordinates()));
    }

    /**
     * ????????????  ???????????????   ???????????????  ????????????????????????
     * @return  ??????
     */
    @Override
    public Iterable<T> findAll() {
        //????????????
        int itemCount = (int) this.count();

        //?????????????????? 0  ?????????List
        if (itemCount == 0) return new PageImpl<>(Collections.emptyList());

        //????????????
        return this.findAll(PageRequest.of(0, Math.max(1, itemCount)));
    }

    /**
     * ?????????????????????  ???0?????????100???
     * 0-100?????????
     * @param pageable PageRequest.of(0, Math.max(1, 100))
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findAll(Pageable pageable) {
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).withPageable(pageable).build();
        SearchHits<T> searchHits = operations.search(query, getEntityClass(), getIndexCoordinates());
        AggregatedPage<SearchHit<T>> page = SearchHitSupport.page(searchHits, query.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    /**
     * ??????????????????
     * @param sort ????????????
     * @return  ????????????
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterable<T> findAll(Sort sort) {
        int itemCount = (int) this.count();

        if (itemCount == 0) {
            return new PageImpl<>(Collections.emptyList());
        }
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withPageable(PageRequest.of(0, itemCount, sort)).build();
        List<SearchHit<T>> searchHitList = operations.search(query, getEntityClass(), getIndexCoordinates())
                .getSearchHits();
        return (List<T>) SearchHitSupport.unwrapSearchHits(searchHitList);
    }

    /**
     * ????????????id ????????????
     * @param ids  ??????id
     * @return  ??????
     */
    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {

        Assert.notNull(ids, "ids can't be null.");

        List<T> result = new ArrayList<>();
        List<String> stringIds = stringIdsRepresentation(ids);

        if (stringIds.isEmpty()) {
            return result;
        }

        NativeSearchQuery query = new NativeSearchQueryBuilder().withIds(stringIds).build();
        List<T> multiGetEntities = operations.multiGet(query, getEntityClass(), getIndexCoordinates());

        multiGetEntities.forEach(entity -> {

            if (entity != null) {
                result.add(entity);
            }
        });

        return result;
    }

    /**
     * ??????????????????
     * @return
     */
    @Override
    public long count() {
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        return operations.count(query, getEntityClass(), getIndexCoordinates());
    }

    /**
     * ????????????
     * @param entity  ??????
     * @param <S>
     * @return
     */
    @Override
    public <S extends T> S save(S entity) {

        Assert.notNull(entity, "Cannot save 'null' entity.");

        operations.save(entity, getIndexCoordinates());
        operations.indexOps(entity.getClass()).refresh();
        return entity;
    }

    public <S extends T> List<S> save(List<S> entities) {

        Assert.notNull(entities, "Cannot insert 'null' as a List.");

        return Streamable.of(saveAll(entities)).stream().collect(Collectors.toList());
    }

    @Override
    @Deprecated
    public <S extends T> S indexWithoutRefresh(S entity) {
        Assert.notNull(entity, "Cannot save 'null' entity.");
        operations.save(entity);
        return entity;
    }

    /**
     * ????????????
     * @param entities
     * @param <S>
     * @return
     */
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {

        Assert.notNull(entities, "Cannot insert 'null' as a List.");

        IndexCoordinates indexCoordinates = getIndexCoordinates();
        operations.save(entities, indexCoordinates);
        operations.indexOps(indexCoordinates).refresh();

        return entities;
    }

    /**
     * ??????id?????????????????????ES
     * @param id
     * @return
     */
    @Override
    public boolean existsById(ID id) {
        return operations.exists(stringIdRepresentation(id), getIndexCoordinates());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<T> search(QueryBuilder query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        int count = (int) operations.count(searchQuery, getEntityClass(), getIndexCoordinates());

        if (count == 0) {
            return new PageImpl<>(Collections.emptyList());
        }
        searchQuery.setPageable(PageRequest.of(0, count));
        SearchHits<T> searchHits = operations.search(searchQuery, getEntityClass(), getIndexCoordinates());
        AggregatedPage<SearchHit<T>> page = SearchHitSupport.page(searchHits, searchQuery.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<T> search(QueryBuilder query, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).build();
        SearchHits<T> searchHits = operations.search(searchQuery, getEntityClass(), getIndexCoordinates());
        AggregatedPage<SearchHit<T>> page = SearchHitSupport.page(searchHits, searchQuery.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<T> search(Query query) {
        SearchHits<T> searchHits = operations.search(query, getEntityClass(), getIndexCoordinates());
        AggregatedPage<SearchHit<T>> page = SearchHitSupport.page(searchHits, query.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<T> searchSimilar(T entity, @Nullable String[] fields, Pageable pageable) {

        Assert.notNull(entity, "Cannot search similar records for 'null'.");
        Assert.notNull(pageable, "'pageable' cannot be 'null'");

        MoreLikeThisQuery query = new MoreLikeThisQuery();
//        query.setId(stringIdRepresentation(extractIdFromBean(entity)));
        query.setPageable(pageable);

        if (fields != null) {
            query.addFields(fields);
        }

        SearchHits<T> searchHits = operations.search(query, getEntityClass(), getIndexCoordinates());
        AggregatedPage<SearchHit<T>> page = SearchHitSupport.page(searchHits, pageable);
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    /**
     * ??????id??????
     * @param id  id
     */
    @Override
    public void deleteById(ID id) {

        Assert.notNull(id, "Cannot delete entity with id 'null'.");

        IndexCoordinates indexCoordinates = getIndexCoordinates();
        doDelete(id, indexCoordinates);
        indexOperations.refresh();
    }

    /**
     * ??????????????????
     * @param entity ??????
     */
    @Override
    public void delete(T entity) {

        Assert.notNull(entity, "Cannot delete 'null' entity.");

        IndexCoordinates indexCoordinates = getIndexCoordinates();
        doDelete(extractIdFromBean(entity), indexCoordinates);
        indexOperations.refresh();
    }

    /**
     * ????????????
     * @param entities  ??????
     */
    @Override
    public void deleteAll(Iterable<? extends T> entities) {

        Assert.notNull(entities, "Cannot delete 'null' list.");

        IndexCoordinates indexCoordinates = getIndexCoordinates();
        IdsQueryBuilder idsQueryBuilder = idsQuery();
        for (T entity : entities) {
            ID id = extractIdFromBean(entity);
            if (id != null) {
                idsQueryBuilder.addIds(stringIdRepresentation(id));
            }
        }

        if (idsQueryBuilder.ids().isEmpty()) {
            return;
        }

        Query query = new NativeSearchQueryBuilder().withQuery(idsQueryBuilder).build();

        operations.delete(query, getEntityClass(), indexCoordinates);
        indexOperations.refresh();
    }

    private void doDelete(@Nullable ID id, IndexCoordinates indexCoordinates) {
        if (id != null) {
            operations.delete(stringIdRepresentation(id), indexCoordinates);
        }
    }

    /**
     * ????????????
     */
    @Override
    public void deleteAll() {
        IndexCoordinates indexCoordinates = getIndexCoordinates();
        Query query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();

        operations.delete(query, getEntityClass(), indexCoordinates);
        indexOperations.refresh();
    }

    /**
     * ????????????
     */
    @Override
    public void refresh() {
        indexOperations.refresh();
    }

    @SuppressWarnings("unchecked")
    private Class<T> resolveReturnedClassFromGenericType() {
        ParameterizedType parameterizedType = resolveReturnedClassFromGenericType(getClass());
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    private ParameterizedType resolveReturnedClassFromGenericType(Class<?> clazz) {
        ParameterizedTypeImpl genericSuperclass = (ParameterizedTypeImpl) clazz.getGenericSuperclass();
        return genericSuperclass;
    }

    /**
     * ????????????Class
     * @return
     */
    protected Class<T> getEntityClass() {

        if (!isEntityClassSet()) {
            try {
                this.entityClass = resolveReturnedClassFromGenericType();
            } catch (Exception e) {
                throw new InvalidDataAccessApiUsageException("Unable to resolve EntityClass. Please use according setter!", e);
            }
        }
        return entityClass;
    }

    private boolean isEntityClassSet() {
        return entityClass != null;
    }

    private List<String> stringIdsRepresentation(Iterable<ID> ids) {
        Assert.notNull(ids, "ids can't be null.");
        List<String> stringIds = new ArrayList<>();
        for (ID id : ids) {
            stringIds.add(stringIdRepresentation(id));
        }

        return stringIds;
    }

    protected @Nullable String stringIdRepresentation(@Nullable ID id) {
        return operations.stringIdRepresentation(id);
    }

    private IndexCoordinates getIndexCoordinates() {
        return operations.getIndexCoordinatesFor(getEntityClass());
    }
}
