package com.boxnotfound.sinewavegenerator.andrones;

import com.boxnotfound.sinewavegenerator.BasePresenter;
import com.boxnotfound.sinewavegenerator.BaseView;
import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.instance.Androne;

import java.util.List;

public interface AndroneContract {

    interface Presenter extends BasePresenter {

        void loadAndrones();

        void addAndrone(Androne androne);

        void setPitch(double frequency);

        void setPitch(String pitchName);

        void incrementFrequency();

        void decrementFrequency();

        void incrementPitch();

        void decrementPitch();

        void setWaveform(Waveform waveform);

        void setVolume(float volume);

        void setVolume(int volumeProgress);

        void playAndrone();

        void stopAndrone();

        void deleteAndrone();

        void deleteAllAndrones();

    }

    interface View extends BaseView<Presenter> {

        void displayAndrones(List<Androne> list);

        void displayNoAndrones();

        void displayAddAndrone();

        void displayDeleteAndrone();

        void displayAndronePitch();

        void displayAndroneWaveform();

        void displayAndroneVolume();

        void displayAndronePlaying();

        void displayAndroneStopped();



    }
}
