package com.boxnotfound.sinewavegenerator.andrones;

import android.support.annotation.NonNull;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.source.androne.AndroneDataSource;
import com.boxnotfound.sinewavegenerator.model.source.androne.AndroneRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AndronePresenter implements AndroneContract.Presenter {

    private AndroneRepository androneRepository;

    private AndroneContract.View androneView;

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
        androneRepository.getAllAndrones(new AndroneDataSource.LoadAndronesCallback() {
            @Override
            public void onAndronesLoaded(List<Androne> andrones) {
                if (andrones != null && !andrones.isEmpty()) {
                    androneView.displayAndrones(andrones);
                } else {
                    androneView.displayNoAndrones();
                }
            }

            @Override
            public void onAndronesNotAvailable() {
                androneView.displayNoAndrones();
            }
        });
    }

    @Override
    public void addAndrone(Androne androne) {
        androneRepository.addAndrone(androne);
        androneView.displayAddAndrone();
    }

    @Override
    public void setPitch(Androne androne, double frequency) {
        androne.setFrequency(frequency);
        updateAndrone(androne);
    }

    @Override
    public void setPitch(Androne androne, String pitchName) {
        androne.setPitchName(pitchName);
        updateAndrone(androne);
    }

    @Override
    public void incrementFrequency(Androne androne) {
        androne.incrementFrequency();
        updateAndrone(androne);
    }

    @Override
    public void decrementFrequency(Androne androne) {
        androne.decrementFrequency();
        updateAndrone(androne);
    }

    @Override
    public void incrementPitch(Androne androne) {
        androne.incrementPitch();
        updateAndrone(androne);
    }

    @Override
    public void decrementPitch(Androne androne) {
        androne.decrementPitch();
        updateAndrone(androne);
    }

    @Override
    public void setWaveform(Androne androne, Waveform waveform) {
        androne.setWaveform(waveform);
        updateAndrone(androne);
    }

    @Override
    public void setVolume(Androne androne, float volume) {
        androne.setVolume(volume);
        updateAndrone(androne);
    }

    @Override
    public void setVolume(Androne androne, int volumeProgress) {
        androne.setVolumeProgress(volumeProgress);
        updateAndrone(androne);
    }

    @Override
    public void playAndrone(Androne androne) {
        androne.startAndrone();
        updateAndrone(androne);
    }

    @Override
    public void stopAndrone(Androne androne) {
        androne.stopAndrone();
        updateAndrone(androne);
    }

    @Override
    public void deleteAndrone(Androne androne) {
        androneRepository.deleteAndrone(androne);
        androneView.displayDeleteAndrone();
    }

    @Override
    public void deleteAllAndrones() {
        androneRepository.deleteAllAndrones();
        androneView.displayNoAndrones();
    }

    @Override
    public void onDestroy() {
        androneRepository = null;
        androneView = null;
    }

    private void updateAndrone(Androne androne) {
        androneRepository.updateAndrone(androne);
        androneView.displayUpdateAndrone();
    }
}
