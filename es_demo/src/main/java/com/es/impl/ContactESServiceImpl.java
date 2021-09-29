package com.es.impl;

import com.entity.Contact;
import com.es.ContactESService;
import com.es.common.impl.AbstractElasticSearchServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ContactESServiceImpl extends AbstractElasticSearchServiceImpl<Contact, String> implements ContactESService {
}
