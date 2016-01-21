package com.tpjad.clienttpjad.models;

public class Product {
    private int id;
    private int stock;
    private String name;
    private String description;
    private Category category;

    public Product(int id, int stock, String name, String description, Category category) {
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Product(int stock, String name, String description, Category category) {
        this.stock = stock;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
