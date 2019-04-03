package com.boxnotfound.sinewavegenerator.andrones;

import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.source.androne.AndroneDataSource;
import com.boxnotfound.sinewavegenerator.model.source.androne.AndroneRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AndronePresenter implements AndroneContract.Presenter {

    private final AndroneRepository androneRepository;

    private final AndroneContract.View androneView;

    public AndronePresenter(@NonNull AndroneRepository androneRepository, @NonNull AndroneContract.View androneView) {
        this.androneRepository = checkNotNull(androneRepository, "androneRepo cannot be null!");
        this.androneView = checkNotNull(androneView, "androneView cannot be null!");

        androneView.setPresenter(this);
    }

    @Override
    public void onStart() {
        loadAndrones();
    }

    @Override
    public void loadAndrones() {

    }

    @Override
    public void addAndrone(Androne androne) {

    }

    @Override
    public void setPitch(Androne androne, double frequency) {

    }

    @Override
    public void setPitch(Androne androne, String pitchName) {

    }

    @Override
    public void incrementFrequency(Androne androne) {

    }

    @Override
    public void decrementFrequency(Androne androne) {

    }

    @Override
    public void incrementPitch(Androne androne) {

    }

    @Override
    public void decrementPitch(Androne androne) {

    }

    @Override
    public void setWaveform(Androne androne, Waveform waveform) {

    }

    @Override
    public void setVolume(Androne androne, float volume) {

    }

    @Override
    public void setVolume(Androne androne, int volumeProgress) {

    }

    @Override
    public void playAndrone(Androne androne) {

    }

    @Override
    public void stopAndrone(Androne androne) {

    }

    @Override
    public void deleteAndrone(Androne androne) {

    }

    @Override
    public void deleteAllAndrones() {

    }

    @Override
    public void onDestroy() {

    }
}
