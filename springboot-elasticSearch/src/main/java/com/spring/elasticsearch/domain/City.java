package com.spring.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "province", type = "city")
public class City implements Serializable {

    private long id;
    private String name;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City() {
    }

    public City(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
