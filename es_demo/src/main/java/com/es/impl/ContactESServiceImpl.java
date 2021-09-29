package com.es.impl;

import com.entity.Contact;
import com.es.ContactESService;
import com.es.common.impl.AbstractElasticSearchServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;


@Service
public class ContactESServiceImpl extends AbstractElasticSearchServiceImpl<Contact, Integer> implements ContactESService {

    @Autowired
    public ContactESServiceImpl(RestHighLevelClient client, ElasticsearchOperations operations) {
        super(client, operations);
    }
}
