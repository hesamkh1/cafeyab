
package com.example.cafeyab.model;


import com.google.gson.annotations.Expose;


@SuppressWarnings("unused")
public class Cafe {

    //for favourite cafes favouritefragment
    public Cafe(String id, String name, String imageurl) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
    }

    @Expose
    private String id;
    @Expose
    private String latitude;
    @Expose
    private String longitude;
    @Expose
    private String name;
    @Expose
    private String imageurl;
    @Expose
    private String opentime;
    @Expose
    private String closetime;
    @Expose
    private String address;
    @Expose
    private String capacity;
    @Expose
    private String phone_number;
    @Expose
    private String info;
    @Expose
    private String category;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getClosetime() {
        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
