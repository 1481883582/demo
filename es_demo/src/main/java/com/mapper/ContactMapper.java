package com.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Contact;
import org.apache.ibatis.annotations.Mapper;
/**
 * (Contact)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-28 11:58:12
 */
@Mapper
public interface ContactMapper extends BaseMapper<Contact> {
}