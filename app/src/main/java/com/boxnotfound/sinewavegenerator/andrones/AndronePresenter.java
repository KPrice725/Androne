package com.boxnotfound.sinewavegenerator.andrones;

import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;

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
    public void setPitch(double frequency) {

    }

    @Override
    public void setPitch(String pitchName) {

    }

    @Override
    public void incrementFrequency() {

    }

    @Override
    public void decrementFrequency() {

    }

    @Override
    public void incrementPitch() {

    }

    @Override
    public void decrementPitch() {

    }

    @Override
    public void setWaveform(Waveform waveform) {

    }

    @Override
    public void setVolume(float volume) {

    }

    @Override
    public void setVolume(int volumeProgress) {

    }

    @Override
    public void playAndrone() {

    }

    @Override
    public void stopAndrone() {

    }

    @Override
    public void deleteAndrone() {

    }

    @Override
    public void deleteAllAndrones() {

    }

    @Override
    public void onDestroy() {

    }
}
