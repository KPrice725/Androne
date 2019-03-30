package com.boxnotfound.sinewavegenerator.model.source.pitch;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.model.Pitch;
import com.boxnotfound.sinewavegenerator.model.source.AppDatabase;
import com.boxnotfound.sinewavegenerator.model.source.pitch.PitchDao;

import java.util.List;

public class PitchRepository implements PitchDataSource {

    private PitchDao pitchDao;
    private LiveData<List<Pitch>> allPitches;

    public PitchRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        pitchDao = db.pitchDao();
        allPitches = pitchDao.getAllPitches();
    }

    @Override
    public void getAllPitches(@NonNull PitchDataSource.LoadPitchesCallback callback) {
        if (allPitches == null) {
            allPitches = pitchDao.getAllPitches();
        }
        if (allPitches == null || allPitches.getValue().size() == 0) {
            callback.onPitchesNotAvailable();
        } else {
            callback.onPitchesLoaded(allPitches);
        }
    }

    @Override
    public void getPitch(@NonNull  String pitchName, @NonNull LoadPitchCallback callback) {
        LiveData<Pitch> pitch = pitchDao.getPitch(pitchName);
        if (pitch != null) {
            callback.onPitchLoaded(pitch);
        } else {
            callback.onPitchNotAvailable();
        }
    }

    @Override
    public void addPitch(final Pitch pitch) {
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

    @Override
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
