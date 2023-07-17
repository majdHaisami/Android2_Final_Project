package com.example.appfirebase.Class;

public class Cart {
    private String chefId;
    private String dishID;
    private String dishname;
    private String dishQuntity;
    private String price;
    private String totalprice;

    public Cart(String chefId, String dishID, String dishname, String dishQuntity, String price, String totalprice) {
        this.chefId = chefId;
        this.dishID = dishID;
        this.dishname = dishname;
        this.dishQuntity = dishQuntity;
        this.price = price;
        this.totalprice = totalprice;
    }

    public Cart() {
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getDishQuntity() {
        return dishQuntity;
    }

    public void setDishQuntity(String dishQuntity) {
        this.dishQuntity = dishQuntity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
