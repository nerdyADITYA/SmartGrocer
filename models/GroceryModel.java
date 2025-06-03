package com.example.smartgrocer.models;

public class GroceryModel {

    String gId;
    String imgUrl;
    String name;
    int price;
    int qty;
    String unit;

    public GroceryModel() {
    }

    public GroceryModel(String gId, String imgUrl, String name, int price, int qty, String unit) {
        this.gId = gId;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.unit = unit;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
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
