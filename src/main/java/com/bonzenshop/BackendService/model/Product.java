package com.bonzenshop.BackendService.model;

public class Product {

    private int id;
    private String name;
    private String desc;
    private String category;
    private double price;
    // TODO: pictures
    private int onStock;

    public Product(int id, String name, String desc, String category, double price, int onStock) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public int getOnStock() {
        return onStock;
    }
}
