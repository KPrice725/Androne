package com.boxnotfound.sinewavegenerator.model.source.pitch;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.model.Pitch;

import java.util.List;

public interface PitchDataSource {

    interface LoadPitchesCallback {
        void onPitchesLoaded(LiveData<List<Pitch>> pitches);

        void onPitchesNotAvailable();
    }

    interface LoadPitchCallback {
        void onPitchLoaded(LiveData<Pitch> pitch);

        void onPitchNotAvailable();
    }

    void getAllPitches(@NonNull LoadPitchesCallback callback);

    void getPitch(String pitchName, @NonNull LoadPitchCallback callback);

    void addPitch(Pitch pitch);

    void deleteAllPitches();
}
