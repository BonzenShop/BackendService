package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    private int id;
    private int user;
    private String orderDate;
    private String name;
    private String category;
    private double price;
    private int amount;
    private double totalPrice;

    @JsonCreator
    public Order(@JsonProperty int user,
                 @JsonProperty String orderDate,
                 @JsonProperty String name,
                 @JsonProperty String category,
                 @JsonProperty double price,
                 @JsonProperty int amount,
                 @JsonProperty double totalPrice){
        this.id = 0;
        this.user = user;
        this.orderDate = orderDate;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Order(int id, int user, String orderDate, String name, String category, double price, int amount, double totalPrice) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
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
}
