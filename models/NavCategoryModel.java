package com.example.smartgrocer.models;

public class NavCategoryModel {

    String id;
    String name;
    String description;
    String discount;
    String imgPath;

    public NavCategoryModel() {
    }

    public NavCategoryModel(String id, String name, String description, String discount, String imgPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.imgPath = imgPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
