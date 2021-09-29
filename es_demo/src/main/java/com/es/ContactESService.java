package com.es;

import com.entity.Contact;
import com.es.common.ElasticSearchService;

public interface ContactESService extends ElasticSearchService<Contact, String> {
}
