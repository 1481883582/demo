package com;

import com.entity.Contact;
import com.es.ContactESService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


	@Resource
	private ContactESService contactESService;


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
		Optional<Contact> byId = contactESService.findById("3");
		log.info(byId.get().toString());

		//根据id List 查询详细对象
		List<String> list = new ArrayList<>();
		list.add("3");
		list.add("4");
		list.add("5");
		Iterable<Contact> allById = contactESService.findAllById(list);
		//打印 0-100的数据
		allById.forEach( a-> log.info(a.toString()));
	}

}
