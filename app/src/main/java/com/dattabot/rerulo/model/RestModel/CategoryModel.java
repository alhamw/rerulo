package com.dattabot.rerulo.model.RestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("w_code")
    @Expose
    private String wCode;
    @SerializedName("w_name")
    @Expose
    private String wName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public String getWCode() {
        return wCode;
    }

    public void setWCode(String wCode) {
        this.wCode = wCode;
    }

    public String getWName() {
        return wName;
    }

    public void setWName(String wName) {
        this.wName = wName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
