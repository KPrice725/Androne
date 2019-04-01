package com.boxnotfound.sinewavegenerator.model.source.androne;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.model.Androne;

import java.util.List;

public interface AndroneDataSource {

    interface LoadAndronesCallback {
        void onAndronesLoaded(List<Androne> andrones);

        void onAndronesNotAvailable();
    }

    void getAllAndrones(@NonNull LoadAndronesCallback callback);

    void addAndrone(Androne androne);

    void updateAndrone(Androne androne);

    void deleteAndrone(Androne androne);

    void deleteAllAndrones();


}
