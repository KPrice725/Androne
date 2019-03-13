package com.boxnotfound.sinewavegenerator.instance;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

public class Androne {

    private AndroneThread androneThread;
    private double frequency = 400.0;
    private WaveForm waveForm = WaveForm.TRIANGLE;

    public Androne startAndrone() {
        if (androneThread == null) {
            androneThread = new AndroneThread();
            androneThread.setFrequency(frequency);
            androneThread.setWaveForm(waveForm);
            androneThread.start();
        }
        return this;
    }

    public void setFrequency(double f) {
        frequency = f;
        if (androneThread != null) {
            androneThread.setFrequency(f);
        }
    }

    public double getFrequency() {
        return frequency;
    }

    public void setWaveForm(WaveForm wf) {
        waveForm = wf;
        if (androneThread != null) {
            androneThread.setWaveForm(wf);
        }
    }

    public WaveForm getWaveForm() {
        return waveForm;
    }

    public void stopAndrone() {
        if (androneThread != null) {
            androneThread.stopSound();
            androneThread = null;
        }
    }

}
