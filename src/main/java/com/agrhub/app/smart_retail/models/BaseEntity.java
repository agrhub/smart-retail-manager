package com.agrhub.app.smart_retail.models;

import com.agrhub.app.smart_retail.repositories.ProductService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class BaseEntity {
    protected static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public static final String ID = "id";
    public static final String CREATE_AT = "create_at";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_AT = "update_at";
    public static final String UPDATE_BY = "update_by";
    public static final String IS_DELETED = "is_deleted";

    private Long id;
    @SerializedName("create_at")
    @JsonProperty(value = "create_at")
    private Date createAt;
    @SerializedName("create_by")
    @JsonProperty(value = "create_by")
    private Long createBy;
    @SerializedName("update_at")
    @JsonProperty(value = "update_at")
    private Date updateAt;
    @SerializedName("update_by")
    @JsonProperty(value = "update_by")
    private Long updateBy;
    @SerializedName("is_deleted")
    @JsonProperty(value = "is_deleted")
    private boolean isDeleted;

    public BaseEntity() {

    }

    public BaseEntity(Long id
            , Date createAt
            , Long createBy
            , Date updateAt
            , Long updateBy
            , boolean isDeleted) {
        this.id = id;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.isDeleted = isDeleted;
    }

    public BaseEntity(Entity entity) {
        this.id = entity.getKey().getId();
        this.createAt = (Date) entity.getProperty(BaseEntity.CREATE_AT);
        this.createBy = (Long) entity.getProperty(BaseEntity.CREATE_BY);
        this.updateAt = (Date) entity.getProperty(BaseEntity.UPDATE_AT);
        this.updateBy = (Long) entity.getProperty(BaseEntity.UPDATE_BY);
        this.isDeleted = (Boolean) entity.getProperty(BaseEntity.IS_DELETED);
    }

    public Entity getEntity(Entity entity) {
        Date currentDate = new Date();
        Long currentUser = 0L;
        if (this.id != null) {
            entity.setProperty(BaseEntity.ID, this.id);
        }
        if (this.createAt == null) {
            entity.setProperty(BaseEntity.CREATE_AT, currentDate);
        } else {
            entity.setProperty(BaseEntity.CREATE_AT, this.createAt);
        }
        if (this.createBy == null) {
            entity.setProperty(BaseEntity.CREATE_BY, currentUser);
        } else {
            entity.setProperty(BaseEntity.CREATE_BY, this.createBy);
        }
        entity.setProperty(BaseEntity.UPDATE_AT, currentDate);
        entity.setProperty(BaseEntity.UPDATE_BY, currentUser);
        entity.setProperty(BaseEntity.IS_DELETED, this.isDeleted);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
