package com.dattabot.rerulo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alhamwa on 10/23/17.
 */

public class Category extends RealmObject {
    private int idCat;
    private String catName;
    private String catId;
    private String idStore;
    private RealmList<Product> products;

    public Category() {
    }

    public Category(int idCat, String catName, String catId, String idStore) {
        this.idCat = idCat;
        this.catName = catName;
        this.catId = catId;
        this.idStore = idStore;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public RealmList<Product> getProducts() {
        return products;
    }

    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }
}
