package com.agrhub.app.smart_retail.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceImage {
    @JsonProperty(value = "product_id")
    private Long productId;
    @JsonProperty(value = "device_id")
    private Long deviceId;
    @JsonProperty(value = "device_mac_address")
    private String macAddress;
    @JsonProperty(value = "image")
    private byte[] image;

    public DeviceImage() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
