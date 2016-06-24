package com.thyago.retrofit;

/**
 * Created by thyago on 6/21/16.
 */
public class Store {
    private int apiId;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String address;
    private String brand;
    private ImageLinks image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ImageLinks getImage() {
        return image;
    }

    public void setImage(ImageLinks image) {
        this.image = image;
    }
}
