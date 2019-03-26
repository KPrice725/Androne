package com.boxnotfound.sinewavegenerator.andrones;

import com.boxnotfound.sinewavegenerator.BasePresenter;
import com.boxnotfound.sinewavegenerator.BaseView;
import com.boxnotfound.sinewavegenerator.instance.Androne;

import java.util.List;

public interface AndroneContract {

    interface Presenter implements BasePresenter {

    }

    interface View implements BaseView<Presenter> {

        void displayAndrones(List<Androne> list);

        void displayNoAndrones();

        void displayAddAndrone();

        void displayDeleteAndrone();

        void displaySetPitch();

        void displaySetWaveform();

        void displaySetVolume();



    }
}
