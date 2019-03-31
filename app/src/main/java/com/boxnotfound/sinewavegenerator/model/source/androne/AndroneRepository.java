package com.boxnotfound.sinewavegenerator.model.source.androne;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.source.AppDatabase;

import java.util.List;

public class AndroneRepository implements AndroneDataSource {

    private AndroneDao androneDao;
    private LiveData<List<Androne>> allAndrones;

    public AndroneRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        androneDao = db.androneDao();
        allAndrones = androneDao.getAllAndrones();
    }
    @Override
    public void getAllAndrones(@NonNull AndroneDataSource.LoadAndronesCallback callback) {
        if (allAndrones == null) {
            allAndrones = androneDao.getAllAndrones();
        }
        callback.onAndronesLoaded(allAndrones);
    }

    @Override
    public void addAndrone(Androne androne) {
        new insertAndroneAsyncTask(androneDao).execute(androne);
    }

    private static class insertAndroneAsyncTask extends AsyncTask<Androne, Void, Void> {

        private AndroneDao asyncAndroneDao;

        insertAndroneAsyncTask(AndroneDao dao) { asyncAndroneDao = dao; }

        @Override
        protected Void doInBackground(Androne... params) {
            asyncAndroneDao.insert(params[0]);
            return null;
        }
    }

    @Override
    public void updateAndrone(final Androne androne) {
        new updateAndroneAsyncTask(androneDao).execute(androne);
    }

    private static class updateAndroneAsyncTask extends AsyncTask<Androne, Void, Void> {

        private AndroneDao asyncAndroneDao;

        updateAndroneAsyncTask(AndroneDao dao) { asyncAndroneDao = dao; }

        @Override
        protected Void doInBackground(Androne... params) {
            asyncAndroneDao.updateAndrone(params[0]);
            return null;
        }
    }

    @Override
    public void deleteAndrone(final Androne androne) {
        new deleteAndroneAsyncTask(androneDao).execute(androne);
    }

    private static class deleteAndroneAsyncTask extends AsyncTask<Androne, Void, Void> {

        private AndroneDao asyncAndroneDao;

        deleteAndroneAsyncTask(AndroneDao dao) { asyncAndroneDao = dao; }

        @Override
        protected Void doInBackground(Androne... params) {
            asyncAndroneDao.deleteAndrone(params[0]);
            return null;
        }
    }

    @Override
    public void deleteAllAndrones() {
        new deleteAllAndronesAsyncTask(androneDao).execute();
    }

    private static class deleteAllAndronesAsyncTask extends AsyncTask<Void, Void, Void> {

        private AndroneDao asyncAndroneDao;

        deleteAllAndronesAsyncTask(AndroneDao dao) { asyncAndroneDao = dao; }

        @Override
        protected Void doInBackground(Void... params) {
            asyncAndroneDao.deleteAllAndrones();
            return null;
        }
    }


}
