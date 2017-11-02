package com.dattabot.rerulo.model;

import io.realm.RealmObject;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alhamwa on 10/23/17.
 */

public class Product extends RealmObject {
    @PrimaryKey
    private int idProduct;
    private String name, imgUrl, unit;
    private int price, total;
    private boolean isBuyed;
    private String idStore;
    private int idCat;

    public Product() {
    }

    public Product(String name, String imgUrl, String unit, int price, int total, boolean isBuyed) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.unit = unit;
        this.price = price;
        this.total = total;
        this.isBuyed = isBuyed;
    }

    public Product(int idProduct, String name, String imgUrl, String unit, int price, int total, boolean isBuyed) {
        this.idProduct = idProduct;
        this.name = name;
        this.imgUrl = imgUrl;
        this.unit = unit;
        this.price = price;
        this.total = total;
        this.isBuyed = isBuyed;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isBuyed() {
        return isBuyed;
    }

    public void setBuyed(boolean buyed) {
        isBuyed = buyed;
    }

}
