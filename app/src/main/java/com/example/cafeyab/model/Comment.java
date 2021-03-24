
package com.example.cafeyab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Comment {

    @Expose
    private String date;
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String text;
    @SerializedName("tour_id")
    private String tourId;

    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

}
