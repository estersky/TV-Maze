package com.example.restapi_p7_sitiaisyah.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert
    void insertReminder(ReminderEntity reminder);

    @Query("SELECT * FROM reminder_table ORDER BY id DESC")
    List<ReminderEntity> getAllReminders();

    @Delete
    void deleteReminder(ReminderEntity reminder);
}
