package com.agrhub.app.smart_retail.entity;

import com.agrhub.app.smart_retail.models.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class PostProductDevice {
    @SerializedName("device_mac_address")
    @JsonProperty(value = "device_mac_address")
    private String deviceMacAddress;

    @SerializedName("product")
    @JsonProperty(value = "product")
    private ProductEntity product;

    public PostProductDevice() {
        this.deviceMacAddress = "";
        this.product = null;
    }

    public PostProductDevice(String deviceMacAddress, ProductEntity product) {
        this.deviceMacAddress = deviceMacAddress;
        this.product = product;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
