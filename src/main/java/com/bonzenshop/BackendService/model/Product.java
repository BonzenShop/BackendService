package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stellt ein Produkt dar
 */
public class Product {
    /**
     * ID des Produktes
     */
    private int id;
    /**
     * Name des Produktes
     */
    private String name;
    /**
     * Beschreibung des Produktes
     */
    private String desc;
    /**
     * Kategorie des Produktes
     */
    private String category;
    /**
     * Preis des Produktes
     */
    private double price;
    /**
     * ID des Produktbildes
     */
    private int image;
    /**
     * Angabe, wieviele Einheiten des Produktes auf Lager sind
     */
    private int onStock;

    /**
     * Konstruktor, bei dem alle Attribute übergeben werden müssen.
     * @param id ID
     * @param name Name
     * @param desc Beschreibung
     * @param category Kategorie
     * @param price Preis
     * @param image Bild-ID
     * @param onStock Menge auf Lager
     */
    @JsonCreator
    public Product(@JsonProperty int id,
                   @JsonProperty String name,
                   @JsonProperty String desc,
                   @JsonProperty String category,
                   @JsonProperty double price,
                   @JsonProperty int image,
                   @JsonProperty int onStock){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.price = price;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public int getOnStock() {
        return onStock;
    }
}
