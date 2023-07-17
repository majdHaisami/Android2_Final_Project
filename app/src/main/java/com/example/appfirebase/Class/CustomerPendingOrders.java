package com.example.appfirebase.Class;

public class CustomerPendingOrders {
    private String ChefId;
    private String DishID;
    private String DishName;
    private String DishQuantity;
    private String  Price;
    private String TotalProce;


    public CustomerPendingOrders() {
    }

    public CustomerPendingOrders(String chefId, String dishID, String dishName, String dishQuantity, String price, String totalProce) {
        ChefId = chefId;
        DishID = dishID;
        DishName = dishName;
        DishQuantity = dishQuantity;
        Price = price;
        TotalProce = totalProce;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getDishID() {
        return DishID;
    }

    public void setDishID(String dishID) {
        DishID = dishID;
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
