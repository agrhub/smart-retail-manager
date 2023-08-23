package com.agrhub.app.smart_retail.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DeviceEntity extends BaseEntity {
    public static final String DRAW_MODE_TOP_BOTTOM_LEFT_RIGHT = "top-bottom-left-right";
    public static final String DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP = "left-right-bottom-top";

    public static final String NAME = "name";
    public static final String MAC_ADDRESS = "mac_address";
    public static final String TYPE = "type";
    public static final String PRODUCT_ID = "product_id";
    public static final String DRAW_MODE = "draw_mode";

    private String name;
    @SerializedName("mac_address")
    @JsonProperty(value = "mac_address")
    private String macAddress;
    private Long type;
    @SerializedName("product_id")
    @JsonProperty(value = "product_id")
    private Long productId;
    @SerializedName("draw_mode")
    @JsonProperty(value = "draw_mode")
    private String drawMode;

    public DeviceEntity() {
        super();
    }

    public DeviceEntity(Long id
            , String name
            , String macAddress
            , Long type
            , Long productId
            , String drawMode
            , Date createAt
            , Long createBy
            , Date updateAt
            , Long updateBy
            , boolean isDeleted) {
        super(id, createAt, createBy, updateAt, updateBy, isDeleted);
        this.name = name;
        this.macAddress = macAddress;
        this.type = type;
        this.productId = productId;
        this.drawMode = drawMode;
    }

    public DeviceEntity(Entity entity) {
        super(entity);
        this.name = (String) entity.getProperty(DeviceEntity.NAME);
        this.macAddress = (String) entity.getProperty(DeviceEntity.MAC_ADDRESS);
        this.type = (Long) entity.getProperty(DeviceEntity.TYPE);
        this.productId = (Long) entity.getProperty(DeviceEntity.PRODUCT_ID);
        this.drawMode = (String) entity.getProperty(DeviceEntity.DRAW_MODE);
    }

    public Entity getEntity(Entity entity) {
        entity = super.getEntity(entity);

        entity.setProperty(DeviceEntity.NAME, this.name);
        entity.setProperty(DeviceEntity.MAC_ADDRESS, this.macAddress);
        entity.setProperty(DeviceEntity.TYPE, this.type);
        entity.setProperty(DeviceEntity.PRODUCT_ID, this.productId);
        entity.setProperty(DeviceEntity.DRAW_MODE, this.drawMode);

        return entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(String drawMode) {
        this.drawMode = drawMode;
    }

    public int getLCDWidth(int width) {
        if (DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(this.drawMode)) {
            return (int) Math.ceil(width / 8.0f);
        }
        return width;
    }

    public int getLCDHeight(int height) {
        if (DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(this.drawMode)) {
            return height;
        }
        return (int) Math.ceil(height / 8.0f);
    }
}
