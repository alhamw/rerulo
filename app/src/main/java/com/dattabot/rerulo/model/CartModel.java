package com.dattabot.rerulo.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/21/17.
 */

public class CartModel extends RealmObject{
    private long id;
    private Integer total, idStore;
    private String address;
    private RealmList<ProductModel> list;
    private Boolean isFinished;

    public CartModel() {
    }

    public CartModel(long id, Integer total, Integer idStore, String address, RealmList<ProductModel> list) {
        this.id = id;
        this.total = total;
        this.idStore = idStore;
        this.address = address;
        this.list = list;
    }

    public CartModel(long id, Integer total, Integer idStore, String address, RealmList<ProductModel> list, Boolean isFinished) {
        this.id = id;
        this.total = total;
        this.idStore = idStore;
        this.address = address;
        this.list = list;
        this.isFinished = isFinished;
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RealmList<ProductModel> getList() {
        return list;
    }

    public void setList(RealmList<ProductModel> list) {
        this.list = list;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
