package com.service;

import com.entity.Contact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ContactESService extends ElasticsearchRepository<Contact, Integer> {
}
