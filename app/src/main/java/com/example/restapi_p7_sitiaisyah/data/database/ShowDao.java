package com.example.restapi_p7_sitiaisyah.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ShowDao {
    @Query("SELECT * FROM shows")
    LiveData<List<ShowEntity>> getAllShows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShows(List<ShowEntity> shows);

    @Query("SELECT name, imageUrl, rating, status, premiered FROM shows")
    LiveData<List<ShowEntity>> getShowsWithDetails();

    @Query("SELECT * FROM shows WHERE name LIKE '%' || :query || '%'")
    LiveData<List<ShowEntity>> searchShows(String query);
}
