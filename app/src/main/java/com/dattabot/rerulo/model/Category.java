package com.dattabot.rerulo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alhamwa on 10/23/17.
 */

public class Category extends RealmObject {
    @PrimaryKey
    private int idCat;
    private String name;
    private RealmList<Product> products;

    public Category() {
    }

    public Category(String name, RealmList<Product> products) {
        this.name = name;
        this.products = products;
    }

    public Category(int idCat, String name) {
        this.idCat = idCat;
        this.name = name;
    }

    public Category(int idCat, String name, RealmList<Product> products) {
        this.idCat = idCat;
        this.name = name;
        this.products = products;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Product> getProducts() {
        return products;
    }

    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }
}
