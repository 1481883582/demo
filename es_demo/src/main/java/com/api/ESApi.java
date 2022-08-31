package com.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.entity.Contact;
import com.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/es")
public class ESApi {

    /**
     * 数据库
     */
    @Resource
    private ContactService contactService;

    @GetMapping("/getList")
    public Object getRoleList(){
        List<Contact> list = contactService.list(new LambdaQueryWrapper<Contact>());
        return list;
    }
}
