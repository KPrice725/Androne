package com.boxnotfound.sinewavegenerator.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PitchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pitch pitch);

    @Query("SELECT * FROM pitch_table ORDER BY id ASC")
    LiveData<List<Pitch>> getAllPitches();

    @Query("SELECT * FROM pitch_table WHERE pitch_name = :pitchName")
    LiveData<Pitch> getPitch(String pitchName);

    @Query("DELETE FROM pitch_table")
    void deleteAllPitches();
}
