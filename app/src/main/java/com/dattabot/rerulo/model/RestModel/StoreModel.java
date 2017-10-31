package com.dattabot.rerulo.model.RestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alhamwa on 10/30/17.
 */

public class StoreModel {

    @SerializedName("w_code")
    @Expose
    private String wCode;
    @SerializedName("w_name")
    @Expose
    private String wName;
    @SerializedName("w_img")
    @Expose
    private String wImg;
    @SerializedName("w_phone")
    @Expose
    private String wPhone;
    @SerializedName("w_email")
    @Expose
    private String wEmail;
    @SerializedName("w_address")
    @Expose
    private String wAddress;
    @SerializedName("w_province")
    @Expose
    private String wProvince;
    @SerializedName("w_district")
    @Expose
    private String wDistrict;
    @SerializedName("w_latitude")
    @Expose
    private String wLatitude;
    @SerializedName("w_longitude")
    @Expose
    private String wLongitude;
    @SerializedName("w_id")
    @Expose
    private String wId;

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

    public String getWImg() {
        return wImg;
    }

    public void setWImg(String wImg) {
        this.wImg = wImg;
    }

    public String getWPhone() {
        return wPhone;
    }

    public void setWPhone(String wPhone) {
        this.wPhone = wPhone;
    }

    public String getWEmail() {
        return wEmail;
    }

    public void setWEmail(String wEmail) {
        this.wEmail = wEmail;
    }

    public String getWAddress() {
        return wAddress;
    }

    public void setWAddress(String wAddress) {
        this.wAddress = wAddress;
    }

    public String getWProvince() {
        return wProvince;
    }

    public void setWProvince(String wProvince) {
        this.wProvince = wProvince;
    }

    public String getWDistrict() {
        return wDistrict;
    }

    public void setWDistrict(String wDistrict) {
        this.wDistrict = wDistrict;
    }

    public String getWLatitude() {
        return wLatitude;
    }

    public void setWLatitude(String wLatitude) {
        this.wLatitude = wLatitude;
    }

    public String getWLongitude() {
        return wLongitude;
    }

    public void setWLongitude(String wLongitude) {
        this.wLongitude = wLongitude;
    }

    public String getWId() {
        return wId;
    }

    public void setWId(String wId) {
        this.wId = wId;
    }

}
