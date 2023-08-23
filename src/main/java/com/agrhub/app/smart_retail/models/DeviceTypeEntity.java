package com.agrhub.app.smart_retail.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DeviceTypeEntity extends BaseEntity {
    public static final String NAME = "name";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String RED_MODE = "red_mode";

    private String name;
    private Integer width;
    private Integer height;
    @SerializedName("red_mode")
    @JsonProperty(value = "red_mode")
    private Integer redMode;

    public DeviceTypeEntity() {
        super();
    }

    public DeviceTypeEntity(Long id
            , String name
            , Integer width
            , Integer height
            , Integer redMode
            , Date createAt
            , Long createBy
            , Date updateAt
            , Long updateBy
            , boolean isDeleted) {
        super(id, createAt, createBy, updateAt, updateBy, isDeleted);
        this.name = name;
        this.width = width;
        this.height = height;
        this.redMode = redMode;
    }

    public DeviceTypeEntity(Entity entity) {
        super(entity);
        this.name = (String) entity.getProperty(DeviceTypeEntity.NAME);
        this.width = ((Long) entity.getProperty(DeviceTypeEntity.WIDTH)).intValue();
        this.height = ((Long) entity.getProperty(DeviceTypeEntity.HEIGHT)).intValue();
        this.redMode = ((Long) entity.getProperty(DeviceTypeEntity.RED_MODE)).intValue();
    }

    public Entity getEntity(Entity entity) {
        entity = super.getEntity(entity);

        entity.setProperty(DeviceTypeEntity.NAME, this.name);
        entity.setProperty(DeviceTypeEntity.WIDTH, this.width);
        entity.setProperty(DeviceTypeEntity.HEIGHT, this.height);
        entity.setProperty(DeviceTypeEntity.RED_MODE, this.redMode);

        return entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getRedMode() {
        return redMode;
    }

    public void setRedMode(Integer redMode) {
        this.redMode = redMode;
    }
}
