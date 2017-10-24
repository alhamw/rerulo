package com.dattabot.rerulo.model;

import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/21/17.
 */

public class TestModel extends RealmObject {
    private String name, age;

    public TestModel() {
    }

    public TestModel(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
