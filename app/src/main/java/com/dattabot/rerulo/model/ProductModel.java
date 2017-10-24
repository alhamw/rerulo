package com.dattabot.rerulo.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/20/17.
 */

public class ProductModel extends RealmObject{
    private Integer id;
    private String name, unit, url;
    private Integer quantity, price, idStore, idCategory;

    public ProductModel() {
    }

    public ProductModel(Integer id, String name, String unit, String url, Integer price, Integer idStore, Integer idCategory) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.url = url;
        this.price = price;
        this.idStore = idStore;
        this.idCategory = idCategory;
        this.quantity = 0;
    }

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }
}
