package com.example.appfirebase.Class;

public class MealDetailse {
    private String Dishes;
    private String Quantity;
    private String Price;
    private String Description;
    private String ImageURL;
    private String RandomUID;
    private String ChefId;

    public MealDetailse(String dishes, String quantity, String price, String description, String imageURL, String randomUID, String chefId) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID = randomUID;
        ChefId = chefId;
    }

    public MealDetailse() {
    }

    public String getDishes() {
        return Dishes;
    }

    public void setDishes(String dishes) {
        Dishes = dishes;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }
}
