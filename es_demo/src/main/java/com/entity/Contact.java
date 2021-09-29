package com.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * (Contact)实体类
 *
 * @author makejava
 * @since 2021-09-28 11:58:12
 */
@ToString
@Data
@Document(indexName = "contact", replicas = 0, refreshInterval = "5s")
public class Contact implements Serializable {
    private static final long serialVersionUID = 385461087955235751L;

    @Id
    @Field(name = "id", type = FieldType.Auto)
    @TableField("id")
    private Integer id;

    @Field(name = "picUrl", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @TableField("pic_url")
    private String picUrl;

    @Field(name = "itemName", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @TableField("item_name")
    private String itemName;

    @Field(name = "subName", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @TableField("sub_name")
    private String subName;

//    @Field(name = "martPrice",type = FieldType.Text)
////    @Field(name = "martPrice", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
//    @TableField("mart_price")
//    private Double martPrice;

    @Field(name = "brandName", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @TableField("brand_name")
    private String brandName;
}