package com.example.smartgrocer.models;

public class FruitModel {

    String Id;
    String imgUrl;
    String name;
    int price;
    int qty;
    String unit;

    public FruitModel() {
    }

    public FruitModel(String id, String imgUrl, String name, int price, int qty, String unit) {
        Id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.unit = unit;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
