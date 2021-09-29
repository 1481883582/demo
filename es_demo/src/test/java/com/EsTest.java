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
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


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
		Iterable<Contact> all = contactESService.findAll();
		//打印所有数据
		all.forEach( a-> log.info(a.toString()));

		Page<Contact> page = contactESService.findAll(PageRequest.of(0, Math.max(1, 100)));
		//打印 0-100的数据
		page.get().forEach( a-> log.info(a.toString()));
	}

}
