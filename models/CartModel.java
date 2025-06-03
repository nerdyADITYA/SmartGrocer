package com.example.smartgrocer.models;

public class CartModel
{

    String cId;
    String mail;
    String ImgUrl;
    String name;
    int price;
    int qty;
//    String unit;

    public CartModel() {
    }

    public CartModel(String cId,String mail, String imgUrl, String name, int price, int qty) {
        this.cId = cId;
        this.mail = mail;
        this.ImgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.qty = qty;
//        this.unit = unit;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
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

//    public String getUnit() {
//        return unit;
//    }
//
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
