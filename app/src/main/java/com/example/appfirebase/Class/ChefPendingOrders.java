package com.example.appfirebase.Class;

public class ChefPendingOrders {
    private String UserId;
    private String ChefId;
    private String DishId;
    private String DishName;
    private String DishQuantity;
    private String  Price;
    private String TotalProce;

    public ChefPendingOrders() {
    }

    public String getUserid() {
        return UserId;
    }

    public void setUserid(String userid) {
        UserId = userid;
    }

    public ChefPendingOrders(String chefId, String dishID, String dishName, String dishQuantity, String price, String totalProce, String Userid) {
        ChefId = chefId;
        DishId = dishID;
        DishName = dishName;
        DishQuantity = dishQuantity;
        Price = price;
        TotalProce = totalProce;
        this.UserId=Userid;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getDishID() {
        return DishId;
    }

    public void setDishID(String dishID) {
        DishId = dishID;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishQuantity() {
        return DishQuantity;
    }

    public void setDishQuantity(String dishQuantity) {
        DishQuantity = dishQuantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTotalProce() {
        return TotalProce;
    }

    public void setTotalProce(String totalProce) {
        TotalProce = totalProce;
    }
}
