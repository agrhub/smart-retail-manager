package com.agrhub.app.smart_retail.models;

import com.google.appengine.api.datastore.Entity;

import java.util.Date;
import java.util.List;

public class CategoryEntity extends BaseEntity {
    public static final String NAME = "name";
    public static final String IMAGES = "images";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";

    private String name;
    private String images;
    private String description;
    private Integer priority;
    private List<ProductEntity> products;

    public CategoryEntity() {
        super();
    }

    public CategoryEntity(Long id
            , String name
            , String images
            , String description
            , Integer priority
            , Date createAt
            , Long createBy
            , Date updateAt
            , Long updateBy
            , boolean isDeleted) {
        super(id, createAt, createBy, updateAt, updateBy, isDeleted);
        this.name = name;
        this.images = images;
        this.description = description;
        this.priority = priority;
    }

    public CategoryEntity(Entity entity) {
        super(entity);
        this.name = (String) entity.getProperty(CategoryEntity.NAME);
        this.images = (String) entity.getProperty(CategoryEntity.IMAGES);
        this.description = (String) entity.getProperty(CategoryEntity.DESCRIPTION);
        this.priority = ((Long)entity.getProperty(CategoryEntity.PRIORITY)).intValue();
    }

    public Entity getEntity(Entity entity) {
        entity = super.getEntity(entity);

        entity.setProperty(CategoryEntity.NAME, this.name);
        entity.setProperty(CategoryEntity.IMAGES, this.images);
        entity.setProperty(CategoryEntity.DESCRIPTION, this.description);
        entity.setProperty(CategoryEntity.PRIORITY, this.priority);

        return entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String image) {
        this.images = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}
