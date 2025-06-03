package com.example.smartgrocer.models;

public class RecommendedModel {

    String rec_id;
    String name;
    String description;
    String rating;
    String img_url;


    public RecommendedModel() {
    }


    public RecommendedModel(String rec_id, String name, String description, String rating, String img_url) {
        this.rec_id = rec_id;
        this.name = name;
        this.description = description;
        this.rating = rating;

        this.img_url = img_url;
    }


    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
