package com.example.deliveryboy;

import android.widget.ScrollView;

import java.util.List;

public class Food_POJO {
    String date;
    List<item> order;
    String name;
    String phone;
    String address;
    long status;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public Food_POJO(String date, List<item> order, String name, String phone, String address, long status) {
        this.date = date;
        this.order = order;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Food_POJO(String date, List<item> order, String name, String phone, String address) {
        this.date = date;
        this.order = order;
        this.name=name;
        this.phone=phone;
        this.address=address;
    }

    public Food_POJO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<item> getOrder() {
        return order;
    }

    public void setOrder(List<item> order) {
        this.order = order;
    }
}
