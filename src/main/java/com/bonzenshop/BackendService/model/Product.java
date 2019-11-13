package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private int id;
    private String name;
    private String desc;
    private String category;
    private double price;
    private String imgData;
    private String imgType;
    private int onStock;

    @JsonCreator
    public Product(@JsonProperty int id,
                   @JsonProperty String name,
                   @JsonProperty String desc,
                   @JsonProperty String category,
                   @JsonProperty double price,
                   @JsonProperty String imgData,
                   @JsonProperty String imgType,
                   @JsonProperty int onStock){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.price = price;
        this.imgData = imgData;
        this.imgType = imgType;
        this.onStock = onStock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getCategory() {
        return category;
    }

    public String getImgData() {
        return imgData;
    }

    public void setImgData(String imgData) {
        this.imgData = imgData;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public double getPrice() {
        return price;
    }

    public int getOnStock() {
        return onStock;
    }
}
