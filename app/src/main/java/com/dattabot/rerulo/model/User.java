package com.dattabot.rerulo.model;

import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/24/17.
 */

public class User extends RealmObject {
    private String name, pass, noTelp, storeName, address, city, province, posCode, imgUrl;

    public User() {
    }

    public User(String name, String pass, String noTelp, String storeName, String address, String city, String province, String posCode, String imgUrl) {
        this.name = name;
        this.pass = pass;
        this.noTelp = noTelp;
        this.storeName = storeName;
        this.address = address;
        this.city = city;
        this.province = province;
        this.posCode = posCode;
        this.imgUrl = imgUrl;
    }

    public User(String name, String pass, String noTelp, String storeName, String address, String city, String province, String posCode) {
        this.name = name;
        this.pass = pass;
        this.noTelp = noTelp;
        this.storeName = storeName;
        this.address = address;
        this.city = city;
        this.province = province;
        this.posCode = posCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
}
