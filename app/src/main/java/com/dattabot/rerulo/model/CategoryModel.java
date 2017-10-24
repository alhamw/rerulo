package com.dattabot.rerulo.model;

import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/21/17.
 */

public class CategoryModel extends RealmObject{
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel(Integer id, String name) {

        this.id = id;
        this.name = name;
    }

    public CategoryModel() {

    }
}
