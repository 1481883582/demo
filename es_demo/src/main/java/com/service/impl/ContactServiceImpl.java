package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Contact;
import com.mapper.ContactMapper;
import com.service.ContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Contact)表服务实现类
 *
 * @author makejava
 * @since 2021-09-28 11:58:12
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
    @Resource
    private ContactMapper contactDao;
}