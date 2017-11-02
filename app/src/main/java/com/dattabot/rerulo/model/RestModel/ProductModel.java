package com.dattabot.rerulo.model.RestModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {

    @SerializedName("w_code")
    @Expose
    private String wCode;
    @SerializedName("products_id")
    @Expose
    private String productsId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("prod_name")
    @Expose
    private String prodName;
    @SerializedName("imgurl")
    @Expose
    private String imgurl;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("price")
    @Expose
    private String price;

    public String getWCode() {
        return wCode;
    }

    public void setWCode(String wCode) {
        this.wCode = wCode;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
