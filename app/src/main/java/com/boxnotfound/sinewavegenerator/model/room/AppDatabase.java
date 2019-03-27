package com.boxnotfound.sinewavegenerator.model.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.AndroneDao;
import com.boxnotfound.sinewavegenerator.model.Pitch;
import com.boxnotfound.sinewavegenerator.model.PitchDao;

@Database(entities = {Androne.class, Pitch.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AndroneDao androneDao();

    public abstract PitchDao pitchDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}