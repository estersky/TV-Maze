package com.example.restapi_p7_sitiaisyah.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
public class ReminderEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String date;
    private String time;
    private String imageUri;

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
