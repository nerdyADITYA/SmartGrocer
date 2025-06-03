package com.example.smartgrocer.models;

public class CategoryModel {

    String cat_id;
    String name;
    String type;
    String img_url;


    public CategoryModel() {
    }

    public CategoryModel(String cat_id, String name, String type, String img_url) {
        this.cat_id = cat_id;
        this.name = name;
        this.type = type;
        this.img_url = img_url;
    }


    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
