package com.boxnotfound.sinewavegenerator.model.source.androne;

import com.boxnotfound.sinewavegenerator.model.Androne;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AndroneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Androne androne);

    @Query("SELECT * FROM androne_table ORDER BY id ASC")
    List<Androne> getAllAndrones();

    @Query("SELECT * FROM androne_table WHERE id = :id")
    default Androne getAndroneById(int id) {
        return null;
    }

    @Update
    void updateAndrone(Androne androne);

    @Delete
    void deleteAndrone(Androne androne);

    @Query("DELETE FROM androne_table")
    void deleteAllAndrones();
}
