package com.example.restapi_p7_sitiaisyah.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistFilmDao {
    @Insert
    void insert(PlaylistFilm playlistFilm);

    @Query("SELECT * FROM playlist_film ORDER BY id DESC")
    List<PlaylistFilm> getAllPlaylist();
}

