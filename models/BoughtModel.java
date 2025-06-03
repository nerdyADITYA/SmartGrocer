package com.example.smartgrocer.models;

public class BoughtModel
{
    String bId;
    String name;
    String mail;
    int qty;
    int amount;
    String date;
    String time;

    public BoughtModel() {
    }

    public BoughtModel(String bId, String name,String mail, int qty, int amount, String date, String time) {
        this.bId = bId;
        this.name = name;
        this.mail = mail;
        this.qty = qty;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
