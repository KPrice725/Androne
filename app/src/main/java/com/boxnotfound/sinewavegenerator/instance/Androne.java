package com.boxnotfound.sinewavegenerator.instance;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

public class Androne {

    private AndroneThread androneThread;
    private double frequency;
    private WaveForm waveForm;

    public static class Builder {
        //in case of no passed frequency and/or waveform, default to 440hz Sine Wave
        private double frequency = 440.0;
        private WaveForm waveForm = WaveForm.SINE;
        public Builder setFrequency(double f) {
            frequency = f;
            return this;
        }
        public Builder setWaveForm(WaveForm wf) {
            waveForm = wf;
            return this;
        }
        public Androne build() {
            return new Androne(frequency, waveForm);
        }
    }

    private Androne(double f, WaveForm wf) {
        frequency = f;
        waveForm = wf;
    }

    public void startAndrone() {
        if (androneThread == null) {
            androneThread = new AndroneThread();
            androneThread.setFrequency(frequency);
            androneThread.setWaveForm(waveForm);
            androneThread.start();
        }
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
