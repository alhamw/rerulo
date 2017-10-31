package com.dattabot.rerulo.model.RestModel;

/**
 * Created by alhamwa on 10/30/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class UserModel extends RealmObject {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_name_full")
    @Expose
    private String userNameFull;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("phone_umber")
    @Expose
    private String phoneUmber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("img_photo")
    @Expose
    private String imgPhoto;
    @SerializedName("img_store")
    @Expose
    private String imgStore;
    @SerializedName("img_ktp")
    @Expose
    private String imgKtp;

    public UserModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameFull() {
        return userNameFull;
    }

    public void setUserNameFull(String userNameFull) {
        this.userNameFull = userNameFull;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getPhoneUmber() {
        return phoneUmber;
    }

    public void setPhoneUmber(String phoneUmber) {
        this.phoneUmber = phoneUmber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgPhoto() {
        return imgPhoto;
    }

    public void setImgPhoto(String imgPhoto) {
        this.imgPhoto = imgPhoto;
    }

    public String getImgStore() {
        return imgStore;
    }

    public void setImgStore(String imgStore) {
        this.imgStore = imgStore;
    }

    public String getImgKtp() {
        return imgKtp;
    }

    public void setImgKtp(String imgKtp) {
        this.imgKtp = imgKtp;
    }

}
