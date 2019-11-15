package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Image {
    private int id;
    private String imgData;
    private String imgType;

    @JsonCreator
    public Image(@JsonProperty int id, @JsonProperty String imgData, @JsonProperty String imgType) {
        this.id = id;
        this.imgData = imgData;
        this.imgType = imgType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
