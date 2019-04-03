package com.boxnotfound.sinewavegenerator.model.source.androne;

import com.boxnotfound.sinewavegenerator.model.Androne;

import java.util.List;

import androidx.annotation.NonNull;

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
