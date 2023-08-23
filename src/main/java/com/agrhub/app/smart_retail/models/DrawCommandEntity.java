package com.agrhub.app.smart_retail.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.util.Date;

public class DrawCommandEntity extends BaseEntity {
    public static final String DEVICE_TYPE_ID = "device_type_id";
    public static final String TYPE = "type";
    public static final String X1 = "x1";
    public static final String Y1 = "y1";
    public static final String X2 = "x2";
    public static final String Y2 = "y2";
    public static final String VAL = "val";
    public static final String FONT_NAME = "font_name";
    public static final String FONT_SIZE = "font_size";
    public static final String MIN_FONT_SIZE = "min_font_size";
    public static final String FONT_BOLD = "font_bold";
    public static final String COLOR = "color";
    public static final String BACKGROUND_COLOR = "background_color";
    public static final String ALIGN = "align";
    public static final String VERTICAL_ALIGN = "vertical_align";
    public static final String ROTATE = "rotate";
    public static final String STROKE = "stroke";
    public static final String PRIORITY = "priority";

    @SerializedName("device_type_id")
    @JsonProperty(value = "device_type_id")
    private Long deviceTypeId;
    private String type; // text, line, qr_code
    private Integer x1;
    private Integer y1;
    private Integer x2;
    private Integer y2;
    private String val; // product_id, product_name, product_discount, product_price, product_unit or static string
    @SerializedName("font_name")
    @JsonProperty(value = "font_name")
    private String fontName;
    @SerializedName("font_size")
    @JsonProperty(value = "font_size")
    private Integer fontSize;
    @SerializedName("min_font_size")
    @JsonProperty(value = "min_font_size")
    private Integer minFontSize;
    @SerializedName("font_bold")
    @JsonProperty(value = "font_bold")
    private boolean fontBold;
    private String color;
    @SerializedName("background_color")
    @JsonProperty(value = "background_color")
    private String backgroundColor;
    private Integer align;
    @SerializedName("vertical_align")
    @JsonProperty(value = "vertical_align")
    private Integer verticalAlign;
    private Integer rotate;
    private Integer stroke;
    private Integer priority;

    public DrawCommandEntity() {
        super();
    }

    public DrawCommandEntity(Long id
            , Long deviceTypeId
            , String type
            , Integer x1
            , Integer y1
            , Integer x2
            , Integer y2
            , String val
            , String fontName
            , Integer fontSize
            , Integer minFontSize
            , boolean fontBold
            , String color
            , String backgroundColor
            , Integer align
            , Integer verticalAlign
            , Integer rotate
            , Integer stroke
            , Integer priority
            , Date createAt
            , Long createBy
            , Date updateAt
            , Long updateBy
            , boolean isDeleted) {
        super(id, createAt, createBy, updateAt, updateBy, isDeleted);
        this.deviceTypeId = deviceTypeId;
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.val = val;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.minFontSize = minFontSize;
        this.fontBold = fontBold;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.align = align;
        this.verticalAlign = verticalAlign;
        this.rotate = rotate;
        this.stroke = stroke;
        this.priority = priority;
    }

    public DrawCommandEntity(DrawCommandEntity cmd) {
        super(cmd.getId(), cmd.getCreateAt(), cmd.getCreateBy(), cmd.getUpdateAt(), cmd.getUpdateBy(), cmd.isDeleted());
        this.deviceTypeId = cmd.getDeviceTypeId();
        this.type = cmd.getType();
        this.x1 = cmd.getX1();
        this.y1 = cmd.getY1();
        this.x2 = cmd.getX2();
        this.y2 = cmd.getY2();
        this.val = cmd.getVal();
        this.fontName = cmd.getFontName();
        this.fontSize = cmd.getFontSize();
        this.minFontSize = cmd.getMinFontSize();
        this.fontBold = cmd.isFontBold();
        this.color = cmd.getColor();
        this.backgroundColor = cmd.getBackgroundColor();
        this.align = cmd.getAlign();
        this.verticalAlign = cmd.getVerticalAlign();
        this.rotate = cmd.getRotate();
        this.stroke = cmd.getStroke();
        this.priority = cmd.getPriority();
    }

    public DrawCommandEntity(Entity entity) {
        super(entity);
        this.deviceTypeId = (Long) entity.getProperty(DrawCommandEntity.DEVICE_TYPE_ID);
        this.type = (String) entity.getProperty(DrawCommandEntity.TYPE);
        this.x1 = ((Long) entity.getProperty(DrawCommandEntity.X1)).intValue();
        this.y1 = ((Long) entity.getProperty(DrawCommandEntity.Y1)).intValue();
        this.x2 = ((Long) entity.getProperty(DrawCommandEntity.X2)).intValue();
        this.y2 = ((Long) entity.getProperty(DrawCommandEntity.Y2)).intValue();
        this.val = (String) entity.getProperty(DrawCommandEntity.VAL);
        this.fontName = (String) entity.getProperty(DrawCommandEntity.FONT_NAME);
        this.fontSize = ((Long) entity.getProperty(DrawCommandEntity.FONT_SIZE)).intValue();
        this.minFontSize = ((Long) entity.getProperty(DrawCommandEntity.MIN_FONT_SIZE)).intValue();
        this.fontBold = (Boolean) entity.getProperty(DrawCommandEntity.FONT_BOLD);
        this.color = (String) entity.getProperty(DrawCommandEntity.COLOR);
        this.backgroundColor = (String) entity.getProperty(DrawCommandEntity.BACKGROUND_COLOR);
        this.align = ((Long) entity.getProperty(DrawCommandEntity.ALIGN)).intValue();
        this.verticalAlign = ((Long) entity.getProperty(DrawCommandEntity.VERTICAL_ALIGN)).intValue();
        this.rotate = ((Long) entity.getProperty(DrawCommandEntity.ROTATE)).intValue();
        this.stroke = ((Long) entity.getProperty(DrawCommandEntity.STROKE)).intValue();
        this.priority = ((Long) entity.getProperty(DrawCommandEntity.PRIORITY)).intValue();
    }

    public Entity getEntity(Entity entity) {
        entity = super.getEntity(entity);

        entity.setProperty(DrawCommandEntity.DEVICE_TYPE_ID, this.deviceTypeId);
        entity.setProperty(DrawCommandEntity.TYPE, this.type);
        entity.setProperty(DrawCommandEntity.X1, this.x1);
        entity.setProperty(DrawCommandEntity.Y1, this.y1);
        entity.setProperty(DrawCommandEntity.X2, this.x2);
        entity.setProperty(DrawCommandEntity.Y2, this.y2);
        entity.setProperty(DrawCommandEntity.VAL, this.val);
        entity.setProperty(DrawCommandEntity.FONT_NAME, this.fontName);
        entity.setProperty(DrawCommandEntity.FONT_SIZE, this.fontSize);
        entity.setProperty(DrawCommandEntity.MIN_FONT_SIZE, this.minFontSize);
        entity.setProperty(DrawCommandEntity.FONT_BOLD, this.fontBold);
        entity.setProperty(DrawCommandEntity.COLOR, this.color);
        entity.setProperty(DrawCommandEntity.BACKGROUND_COLOR, this.backgroundColor);
        entity.setProperty(DrawCommandEntity.ALIGN, this.align);
        entity.setProperty(DrawCommandEntity.VERTICAL_ALIGN, this.verticalAlign);
        entity.setProperty(DrawCommandEntity.ROTATE, this.rotate);
        entity.setProperty(DrawCommandEntity.STROKE, this.stroke);
        entity.setProperty(DrawCommandEntity.PRIORITY, this.priority);

        return entity;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getX2() {
        return x2;
    }

    public void setX2(Integer x2) {
        this.x2 = x2;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getMinFontSize() {
        return minFontSize;
    }

    public void setMinFontSize(Integer minFontSize) {
        this.minFontSize = minFontSize;
    }

    public boolean isFontBold() {
        return fontBold;
    }

    public void setFontBold(boolean fontBold) {
        this.fontBold = fontBold;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }

    public Integer getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(Integer verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public Integer getRotate() {
        return rotate;
    }

    public void setRotate(Integer rotate) {
        this.rotate = rotate;
    }

    public Integer getStroke() {
        return stroke;
    }

    public void setStroke(Integer stroke) {
        this.stroke = stroke;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @JsonIgnore
    public String getValFromProduct(ProductEntity product) {
        if ("product_id".equals(this.val)) {
            return String.valueOf(product.getId());
        } else if ("product_name".equals(this.val)) {
            return product.getName();
        } else if ("product_discount".equals(this.val)) {
            return String.format("%d%%", product.getDiscount());
        } else if ("product_price".equals(this.val)) {
            long priceDiscount = 0;
            if (product.getDiscount() > 0) {
                priceDiscount = Math.round(product.getPrice() * product.getDiscount() / 100);
            }
            return String.format("%,d", product.getPrice() - priceDiscount) + product.getCurrency();
        } else if ("product_unit".equals(this.val)) {
            return product.getUnit();
        }
        return this.val;
    }

    @JsonIgnore
    public Color getBackgroundColorObj() {
        return this.toColor(this.backgroundColor);
    }

    @JsonIgnore
    public Color getColorObj() {
        return this.toColor(this.color);
    }

    private Color toColor(String colorName) {
        if ("black".equals(colorName)) {
            return Color.black;
        } else if ("red".equals(colorName)) {
            return Color.red;
        } else if ("white".equals(colorName)) {
            return Color.white;
        }
        return null;
    }
}
