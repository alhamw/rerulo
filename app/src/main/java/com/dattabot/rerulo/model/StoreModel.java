package com.dattabot.rerulo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/20/17.
 */

public class StoreModel extends RealmObject{
    private Integer id;
    private String name, address, city, province, url;

    public StoreModel() {
    }

    public StoreModel(Integer id, String name, String address, String city, String province, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
