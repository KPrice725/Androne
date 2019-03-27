package com.boxnotfound.sinewavegenerator.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AndroneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Androne androne);

    @Query("SELECT * FROM androne_table ORDER BY id ASC")
    LiveData<List<Androne>> getAllAndrones();

    @Update
    void updateAndrone(Androne androne);

    @Delete
    void deleteAndrone(Androne androne);

    @Query("DELETE FROM androne_table")
    void deleteAllAndrones();
}
