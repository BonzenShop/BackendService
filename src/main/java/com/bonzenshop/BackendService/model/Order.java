package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stellt eine Bestellung dar.
 * Eine Bestellung besteht immer aus genau einem Produkt (in einer bestimmten Menge)
 */
public class Order {
    /**
     * Bestellungs-ID
     */
    private int id;
    /**
     * ID des Benutzers, der die Bestellung abgegeben hat
     */
    private int user;
    /**
     * Bestelldatum
     */
    private String orderDate;
    /**
     * Name des bestellten Produktes
     */
    private String name;
    /**
     * Kategorie des bestellten Produktes
     */
    private String category;
    /**
     * Einzelpreis des bestellten Produktes
     */
    private double price;
    /**
     * Menge des bestellten Produktes
     */
    private int amount;
    /**
     * Gesamtpreis der Bestellung (price*amount)
     */
    private double totalPrice;
    /**
     * ID des Bildes des bestellten Produktes
     */
    private int image;

    /**
     * Konstruktor, bei dem alle Attribute außer der ID übergeben werden.
     * Notwendig, wenn eine neue Bestellung erstellt werden soll, die noch keine ID hat.
     * @param user Kunden-ID
     * @param orderDate Bestelldatum
     * @param name Produktname
     * @param category Kategorie
     * @param price Einzelpreis
     * @param amount Menge
     * @param totalPrice Gesamtpreis
     * @param image Bild-ID
     */
    @JsonCreator
    public Order(@JsonProperty int user,
                 @JsonProperty String orderDate,
                 @JsonProperty String name,
                 @JsonProperty String category,
                 @JsonProperty double price,
                 @JsonProperty int amount,
                 @JsonProperty double totalPrice,
                 @JsonProperty int image){
        this.id = 0;
        this.user = user;
        this.orderDate = orderDate;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.image = image;
    }

    /**
     * Konstruktor, bei dem alle Attribute übergeben werden.
     * Wird beim Erzeugen einer bereits existierenden Bestellung benötigt.
     * @param id Bestellungs-ID
     * @param user Kunden-ID
     * @param orderDate Bestelldatum
     * @param name Produktname
     * @param category Kategorie
     * @param price Einzelpreis
     * @param amount Menge
     * @param totalPrice Gesamtpreis
     * @param image Bild-ID
     */
    public Order(int id, int user, String orderDate, String name, String category, double price, int amount, double totalPrice, int image) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        user = user;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
