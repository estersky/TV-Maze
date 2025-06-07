package com.example.restapi_p7_sitiaisyah.data.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shows")
public class ShowEntity {
    @PrimaryKey
    @NonNull
    private String name;

    private String imageUrl;
    private Double rating;
    private String status;
    private String premiered;

    // Konstruktor
    public ShowEntity(@NonNull String name, String imageUrl, Double rating, String status, String premiered) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.status = status;
        this.premiered = premiered;
    }

    // Getter dan Setter
    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }
}
