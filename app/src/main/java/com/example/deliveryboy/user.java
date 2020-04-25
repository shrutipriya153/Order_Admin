package com.example.deliveryboy;

public class user {
    String name,email,phone;
    Boolean isStaff;

    public user(String name, String email, String phone,Boolean isStaff) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isStaff=isStaff;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public user() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
