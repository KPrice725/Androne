package com.boxnotfound.sinewavegenerator.andrones;

import com.boxnotfound.sinewavegenerator.BasePresenter;
import com.boxnotfound.sinewavegenerator.BaseView;
import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;

import java.util.List;

public interface AndroneContract {

    interface Presenter extends BasePresenter {

        void loadAndrones();

        void addAndrone(Androne androne);

        void setPitch(Androne androne, double frequency);

        void setPitch(Androne androne, String pitchName);

        void incrementFrequency(Androne androne);

        void decrementFrequency(Androne androne);

        void incrementPitch(Androne androne);

        void decrementPitch(Androne androne);

        void setWaveform(Androne androne, Waveform waveform);

        void setVolume(Androne androne, float volume);

        void setVolume(Androne androne, int volumeProgress);

        void togglePlay(Androne androne);
        
        void deleteAndrone(Androne androne);

        void deleteAllAndrones();

    }

    interface View extends BaseView<Presenter> {

        void displayAndrones(List<Androne> list);

        void displayNoAndrones();

        void displayAddAndrone();

        void displayDeleteAndrone();

        void displayUpdateAndrone();
    }
}
