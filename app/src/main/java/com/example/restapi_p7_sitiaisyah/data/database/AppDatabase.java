package com.example.restapi_p7_sitiaisyah.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Import semua entitas dan DAO
import com.example.restapi_p7_sitiaisyah.data.database.ShowEntity;
import com.example.restapi_p7_sitiaisyah.data.database.PlaylistFilm;
import com.example.restapi_p7_sitiaisyah.data.database.ReminderEntity;

import com.example.restapi_p7_sitiaisyah.data.database.ShowDao;
import com.example.restapi_p7_sitiaisyah.data.database.PlaylistFilmDao;
import com.example.restapi_p7_sitiaisyah.data.database.ReminderDao;

@Database(entities = {ShowEntity.class, PlaylistFilm.class, ReminderEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ShowDao showDao();
    public abstract PlaylistFilmDao playlistFilmDao();
    public abstract ReminderDao reminderDao(); // Tambahan DAO untuk Reminder

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "show_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
