//package com.controller;
//
//import com.entity.Contact;
//import com.service.ContactService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * (Contact)表控制层
// *
// * @author makejava
// * @since 2021-09-28 11:58:12
// */
//@RestController
//@RequestMapping("contact")
//public class ContactController {
//    /**
//     * 服务对象
//     */
//    @Resource
//    private ContactService contactService;
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("selectOne")
//    public Contact selectOne(Integer id) {
//        return this.contactService.queryById(id);
//    }
//
//}