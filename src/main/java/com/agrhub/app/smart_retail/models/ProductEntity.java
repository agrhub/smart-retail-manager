package com.agrhub.app.smart_retail.models;

import com.agrhub.app.smart_retail.repositories.ProductService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ProductEntity extends BaseEntity {
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String IMAGES = "images";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_ID = "category_id";
    public static final String TAGS = "tags";
    public static final String UNIT = "unit";
    public static final String PRICE = "price";
    public static final String DISCOUNT = "discount";
    public static final String CURRENCY = "currency";
    public static final String CHILD_PRODUCTS = "child_products";

    private String code;
    private String name;
    private String images;
    private String description;
    @SerializedName("category_id")
    @JsonProperty(value = "category_id")
    private Long categoryId;
    private String tags;
    private String unit;
    private Long price;
    private Integer discount;
    private String currency;
    @SerializedName("child_products")
    @JsonProperty(value = "child_products")
    private String childProducts;
    @SerializedName("child_products_obj")
    @JsonProperty(value = "child_products_obj")
    private List<ProductEntity> childProductsObj;

    public ProductEntity() {
        super();
    }

    public ProductEntity(Long id
            , String code
            , String name
            , String images
            , String description
            , Long categoryId
            , String tags
            , String unit
            , Long price
            , Integer discount
            , String currency
            , String childProducts
            , Date createAt
            , Long createBy
            , Date updateAt
            , Long updateBy
            , boolean isDeleted) {
        super(id, createAt, createBy, updateAt, updateBy, isDeleted);
        this.code = code;
        this.name = name;
        this.images = images;
        this.description = description;
        this.categoryId = categoryId;
        this.tags = tags;
        this.unit = unit;
        this.price = price;
        this.discount = discount;
        this.currency = currency;
        this.childProducts = childProducts;
    }

    public ProductEntity(Entity entity, ProductService service) {
        super(entity);
        this.code = (String) entity.getProperty(ProductEntity.CODE);
        this.name = (String) entity.getProperty(ProductEntity.NAME);
        this.images = (String) entity.getProperty(ProductEntity.IMAGES);
        this.description = (String) entity.getProperty(ProductEntity.DESCRIPTION);
        this.categoryId = (Long) entity.getProperty(ProductEntity.CATEGORY_ID);
        this.tags = (String) entity.getProperty(ProductEntity.TAGS);
        this.unit = (String) entity.getProperty(ProductEntity.UNIT);
        this.price = (Long) entity.getProperty(ProductEntity.PRICE);
        this.discount = ((Long) entity.getProperty(ProductEntity.DISCOUNT)).intValue();
        this.currency = (String) entity.getProperty(ProductEntity.CURRENCY);
        this.childProducts = (String) entity.getProperty(ProductEntity.CHILD_PRODUCTS);

        logger.info("ProductEntity init: " + this.childProducts);
        if (this.childProducts != null && !this.childProducts.isEmpty()) {
            this.childProductsObj = service.getProductsWithIds(this.childProducts);
        }
    }

    public Entity getEntity(Entity entity) {
        entity = super.getEntity(entity);

        entity.setProperty(ProductEntity.CODE, this.code);
        entity.setProperty(ProductEntity.NAME, this.name);
        entity.setProperty(ProductEntity.IMAGES, this.images);
        entity.setProperty(ProductEntity.DESCRIPTION, this.description);
        entity.setProperty(ProductEntity.CATEGORY_ID, this.categoryId);
        entity.setProperty(ProductEntity.TAGS, this.tags);
        entity.setProperty(ProductEntity.UNIT, this.unit);
        entity.setProperty(ProductEntity.PRICE, this.price);
        entity.setProperty(ProductEntity.DISCOUNT, this.discount);
        entity.setProperty(ProductEntity.CURRENCY, this.currency);
        entity.setProperty(ProductEntity.CHILD_PRODUCTS, this.childProducts);

        return entity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getChildProducts() {
        return childProducts;
    }

    public void setChildProducts(String childProducts) {
        this.childProducts = childProducts;
    }

    public List<ProductEntity> getChildProductsObj() {
        return childProductsObj;
    }

    public void setChildProductsObj(List<ProductEntity> childProductsObj) {
        this.childProductsObj = childProductsObj;
    }
}
