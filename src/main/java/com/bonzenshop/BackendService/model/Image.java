package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Die Klasse Image stellt ein Produktbild dar
 */
public class Image {
    /**
     * ID des Produktbildes
     */
    private int id;
    /**
     * Daten des Produktbildes (base64-codierter String der Bilddaten)
     */
    private String imgData;
    /**
     * Format des Produktbildes (png/jpg)
     */
    private String imgType;

    /**
     * Konstruktor, um ein Objekt mit allen Attributen zu erstellen.
     * Kann auch bei einem Endpunkt verwendet werden, wo das Bild im Body als JSON übergeben wird.
     * @param id ID
     * @param imgData Bilddaten
     * @param imgType Bildformat
     */
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
