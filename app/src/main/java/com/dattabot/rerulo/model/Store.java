package com.dattabot.rerulo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alhamwa on 10/23/17.
 */

public class Store extends RealmObject{
    private String idStore;
    private String name, address, city, province, imgUrl;
    private RealmList<Category> categories;

    public Store() {
    }

    public Store(String idStore, String name, String address, String city, String province, String imgUrl, RealmList<Category> categories) {
        this.idStore = idStore;
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.imgUrl = imgUrl;
        this.categories = categories;
    }

    public Store(String idStore, String name, String address, String city, String province, String imgUrl) {
        this.idStore = idStore;
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.imgUrl = imgUrl;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RealmList<Category> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<Category> categories) {
        this.categories = categories;
    }
}
