//package com.controller;
//
//import com.util.ElasticsearchUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//
//@Slf4j
//@RestController
//@RequestMapping("/test")
//public class EsController {
//
//    @Resource
//    private ElasticsearchUtil elasticsearchUtil;
//
//    @GetMapping("/get")
//    public void get() throws IOException {
//        String index = "one";
//
//        //判断索引是否存在
//        if(elasticsearchUtil.isIndexExist(index)){
//            log.info("索引存在！");
//
//            //删除索引
//            elasticsearchUtil.deleteIndex(index);
//            log.info("删除索引！");
//        }
//
//        //创建索引
//        elasticsearchUtil.createIndex(index);
//        log.info("创建索引！");
//    }
//
//}
