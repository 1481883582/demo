package com;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.entity.Contact;
import com.es.ContactESService;
import com.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EsTest {

	/**
	 * ES
	 */
	@Resource
	private ContactESService contactESService;

	/**
	 * SQL
	 */
	@Resource
	private ContactService contactService;

	/**
	 * 保存
	 */
	@Test
	public void save(){
		//获取全部数据
		List<Contact> list = contactService.list(new LambdaUpdateWrapper<Contact>());
		//保存ES
		contactESService.saveAll(list);
		log.info("总数据:" + contactESService.count());


		//从数据库获取一个对象  如果ES不存在  保存到ES
		Contact contact = contactService.getOne(
				new LambdaQueryWrapper<Contact>()
						.eq(Contact::getId, 3)
						.last(" LIMIT 1 ")
		);
		//不存在增加数据
		boolean existsById = contactESService.existsById(contact.getId());
		if(!existsById){
			contactESService.save(contact);
		}
		log.info("总数据:" + contactESService.count());
	}

	/**
	 * 查询所有
	 */
	@Test
	public void findAll() {
		//打印所有数据
		Iterable<Contact> all = contactESService.findAll();
		all.forEach( a-> log.info(a.toString()));

		//打印 0-100的数据
		Page<Contact> page = contactESService.findAll(PageRequest.of(0, Math.max(1, 100)));
		page.get().forEach( a-> log.info(a.toString()));


		//根据 id  倒排序
		Sort descId = Sort.by(Sort.Order.desc("id"));
		Iterable<Contact> sortAll = contactESService.findAll(descId);
		sortAll.forEach( a-> log.info(a.toString()));

		//查询总数
		long count = contactESService.count();
		log.info("总数据:" + count);
	}

	/**
	 * 根据条件查询
	 */
	@Test
	public void findById() {
		//根据id 查询 详细对象
		Optional<Contact> byId = contactESService.findById(3);
		log.info(byId.get().toString());

		//根据id List 查询详细对象
		List<Integer> list = new ArrayList<>();
		list.add(3);
		list.add(4);
		list.add(5);
		Iterable<Contact> allById = contactESService.findAllById(list);
		//打印 0-100的数据
		allById.forEach( a-> log.info(a.toString()));
	}

	/**
	 * 删除ES
	 */
	@Test
	public void delete(){
		//删除指定id数据
		contactESService.deleteById(3);
		log.info("总数据:" + contactESService.count());


		//查到对象删除
		Optional<Contact> byId = contactESService.findById(4);
		Contact contact = byId.get();
		//删除实体对象
		contactESService.delete(contact);
		log.info("总数据:" + contactESService.count());


		//查到对象删除
		List<Integer> list = new ArrayList<>();
		list.add(5);
		list.add(6);
		list.add(7);
		Iterable<Contact> contacts = contactESService.findAllById(list);
		contactESService.deleteAll(contacts);
		log.info("总数据:" + contactESService.count());


		//删除所有
		contactESService.deleteAll();
		log.info("总数据:" + contactESService.count());
	}

	/**
	 * 精准匹配
	 */
	@Test
	public void termSearch(){
		// 方式1
//		TermQueryBuilder termQueryBuilder = new TermQueryBuilder("id", 3);

		// 方式2
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("id", 3);
		Iterable<Contact> contacts = contactESService.search(termQueryBuilder);

		contacts.forEach((c)->{
			System.out.println(c.toString());
		});
	}

	/**
	 * 搜索
	 */
	@Test
	public void search(){
		//查询所有
//		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

		//单一字段分词搜索
//		QueryBuilder queryBuilder = QueryBuilders.matchQuery("subName", "舒服");

		//subName or itemName contains "系列"
//		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("系列", "subName", "itemName");

		//搜索value不会被分词
		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("subName", "喜欢小直径");
		Iterable<Contact> contacts = contactESService.search(queryBuilder);

		contacts.forEach((c)->{
			System.out.println(c.toString());
		});
	}

	/**
	 * 精准搜索
	 */
	@Test
	public void term(){
		//精准搜索  搜索条件没被分词 如果搜索内容被分词，只能搜到单个词
//		QueryBuilder termQuery = QueryBuilders.termQuery("subName", "小");

		// 精准搜索多个词
//		QueryBuilder termQuery = QueryBuilders.termsQuery("subName", "月", "新");


		// 范围搜索  搜索大于等于10 并且小于等于200 的价格
		QueryBuilder termQuery = QueryBuilders.rangeQuery("martPrice").gte(10).lte(200);
		Iterable<Contact> contacts = contactESService.search(termQuery);

		contacts.forEach((c)->{
			System.out.println(c.toString());
		});
	}

	/**
	 * 包裹查询, 高于设定分数, 不计算相关性
	 */
	@Test
	public void filter(){
		//第一种嵌套filter
//		QueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("subName", "小"));

		//第二中嵌套filler
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("subName", "小"));
		log.info(queryBuilder.toString());
		Iterable<Contact> contacts = contactESService.search(queryBuilder);


		contacts.forEach((c)->{
			System.out.println(c.toString());
		});
	}



}
