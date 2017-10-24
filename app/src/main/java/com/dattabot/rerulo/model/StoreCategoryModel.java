package com.dattabot.rerulo.model;

import io.realm.RealmObject;

/**
 * Created by alhamwa on 10/21/17.
 */

public class StoreCategoryModel extends RealmObject{
    private Integer storeId, categoryId;

    public StoreCategoryModel() {
    }

    public StoreCategoryModel(Integer storeId, Integer categoryId) {
        this.storeId = storeId;
        this.categoryId = categoryId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
