package com.example.deliveryboy;

public class item {
    private int itemId;
    String item_name, item_price, item_quantity;

    public item() {
    }

    public item( String name, String price, String quantity) {

        this.item_name = name;
        this.item_price = price;
        this.item_quantity = quantity;
    }



    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }
}
