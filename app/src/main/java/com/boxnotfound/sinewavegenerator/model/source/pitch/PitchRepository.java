package com.boxnotfound.sinewavegenerator.model.source;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.boxnotfound.sinewavegenerator.model.Pitch;
import com.boxnotfound.sinewavegenerator.model.room.AppDatabase;
import com.boxnotfound.sinewavegenerator.model.source.PitchDao;

import java.util.List;

public class PitchRepository {

    private PitchDao pitchDao;
    private LiveData<List<Pitch>> allPitches;

    public PitchRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        pitchDao = db.pitchDao();
        allPitches = pitchDao.getAllPitches();
    }

    public LiveData<List<Pitch>> getAllPitches() {
        return allPitches;
    }

    public void insert(final Pitch pitch) {
        new insertPitchAsyncTask(pitchDao).execute(pitch);
    }

    private static class insertPitchAsyncTask extends AsyncTask<Pitch, Void, Void> {
        PitchDao asyncPitchDao;

        insertPitchAsyncTask(PitchDao dao) {
            asyncPitchDao = dao;
        }

        @Override
        protected Void doInBackground(Pitch... params) {
            asyncPitchDao.insert(params[0]);
            return null;
        }
    }

    public LiveData<Pitch> getPitch(String pitchName) {
        return pitchDao.getPitch(pitchName);
    }

    public void deleteAllPitches() {
        new deleteAllPitchesAsyncTask(pitchDao).execute();
    }

    private static class deleteAllPitchesAsyncTask extends AsyncTask<Void, Void, Void> {
        PitchDao asyncPitchDao;

        deleteAllPitchesAsyncTask(PitchDao dao) {
            asyncPitchDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncPitchDao.deleteAllPitches();
            return null;
        }
    }

}
