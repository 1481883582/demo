package com.kafka.kafka_demo.serializer;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class User implements Serializable {
    private Integer id;
    private String name;
    private Date birthDay;

    public User(Integer id, String name, Date birthDay) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
    }
}
