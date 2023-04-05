package com.redis_demo;

import com.alibaba.fastjson.JSON;
import com.redis_demo.bean.Student;
import com.redis_demo.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest
class RedisDemoApplicationTests {

    @Resource
    private RedisService redisService;

    @Test
    void simple(){
        //---------------String start
        //String类型 新增值
        redisService.set("simple_str", 222);
        //String类型 新增值 增加过期时间
        redisService.set("simple_str_time", 111, 60L);
        //String类型 获取
        log.info(redisService.get("simple_str").toString());
        //---------------String end


        //---------------hash start
        //hash类型 新增
        redisService.hmSet("simple_hash", "test_1", 11, null);
        redisService.hmSet("simple_hash", "test_2", 11, null);
        //hash类型 新增 增加过期时间
        redisService.hmSet("simple_hash", "test_2", 11, 60L);
        //hash类型 获取
        log.info(redisService.hmGet("simple_hash", "test_2").toString());
        //---------------hash end


        //---------------set start
        //set类型 新增
        redisService.add("simple_set", 11);
        redisService.add("simple_set", 22);
        //TODO hash类型 新增 增加过期时间 因为懒没有封装
        //set类型 获取
        log.info(redisService.setMembers("simple_set").toString());
        //---------------set end

        //---------------zset start
        //zset类型 新增
        redisService.zAdd("simple_z_set", 11, 1);
        redisService.zAdd("simple_z_set", 22,2);
        //TODO zset类型 新增 增加过期时间 因为懒没有封装
        //zset类型 获取
        log.info(redisService.rangeByScore("simple_z_set", 0, 1).toString());
        //---------------zset end

        //---------------list start
        //list类型 新增
        redisService.lPush("simple_list" ,1);
        redisService.lPush("simple_list" ,2);
        //TODO list类型 新增 增加过期时间 因为懒没有封装
        //list类型 获取
        log.info(redisService.getTList("simple_list").toString());
        //---------------list end

        //---------------常用基础 start
        //判断key是否存在  存在TRUE 不存在FALSE
        log.info(String.valueOf(redisService.exists("simple_str")));

        //删除指定key
//        redisService.remove("simple_str");

        //删除集合key
//        redisService.remove(new String[]{"simple_hash","simple_set"});

        //删除所有simple_开头的key
//        redisService.removePattern("simple_*");
        //---------------list end
    }

    /**
     * 自增redis  || 设置原子过期时间
     */
    @Test
    void increment() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long increment = redisService.increment("123", 60L);
            log.info(increment + "");
        }
    }

    /**
     * redis 订阅发布
     */
    @Test
    void release() {

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisService.convertAndSend("cat", "布偶猫" + i);
        }


        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisService.convertAndSend("dog", "哈士奇" + i);
        }

    }

    /**
     * setHash
     */
    @Test
    void setHash() {
        Student student = Student.builder().age(1).name("张三").build();
        redisService.hmSet("hash_1", "hashKey_1", JSON.toJSONString(student), 60L);
        log.info(redisService.hmGet("hash_1", "hashKey_1").toString());
    }

    /**
     * 测试List泛型
     */
    @Test
    void list(){
        for (int i = 0; i < 10; i++) {
            Student build = Student.builder().age(i).name(i+"").build();
            redisService.lPush("1", JSON.toJSONString(build));
        }
        List<Student> studentList = redisService.getList("1");
        log.info(studentList.toString());
    }

    /**
     * 演示一般使用redis  应用列表
     */
    @Test
    void reqList(){
        //模拟传入id
        String id = "list_1";

        //逻辑key
        String key = "key_" + id;
        //看redis中是否存在
        if(redisService.exists(key)){
            //打印模拟返回
//            return redisService.getTList(key);
            log.info(redisService.getTList(key).toString());
        }

        //数据库中查询数据
        List<Student> students = this.mySqlGetList(id);

        //查询完保存到redis中
        students.stream().filter(Objects::nonNull).forEach(i ->{
            redisService.lPush(key, JSON.toJSONString(i));
        });


//        return students;
        //打印模拟返回
        log.info(students.toString());
    }

    private List<Student> mySqlGetList(String id) {
        ArrayList<Student> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(Student.builder().age(i).name("name" + i).build());
        }
        return list;
    }
}
