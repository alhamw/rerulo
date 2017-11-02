package com.dattabot.rerulo.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alhamwa on 10/24/17.
 */

public class CartItem extends RealmObject {
    @PrimaryKey
    private int idCartItem;
    private int idProduct;
    private String name, imgUrl, unit;
    private int price, total;

    public CartItem() {
    }

    public int getIdCartItem() {
        return idCartItem;
    }

    public void setIdCartItem(int idCartItem) {
        this.idCartItem = idCartItem;
    }

    public CartItem(int idCartItem, int idProduct, String name, String imgUrl, String unit, int price, int total) {

        this.idCartItem = idCartItem;
        this.idProduct = idProduct;
        this.name = name;
        this.imgUrl = imgUrl;
        this.unit = unit;
        this.price = price;
        this.total = total;
    }

    public CartItem(String name, String imgUrl, String unit, int price, int total) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.unit = unit;
        this.price = price;
        this.total = total;
    }

    public CartItem(int idProduct, String name, String imgUrl, String unit, int price, int total) {
        this.idProduct = idProduct;
        this.name = name;
        this.imgUrl = imgUrl;
        this.unit = unit;
        this.price = price;
        this.total = total;
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

}
