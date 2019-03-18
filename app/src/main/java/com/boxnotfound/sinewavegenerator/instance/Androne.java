package com.boxnotfound.sinewavegenerator.instance;

import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

public class Androne {

    private static final String LOG_TAG = Androne.class.getSimpleName();
    private AndroneThread androneThread;
    private double frequency;
    private String pitch;
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
            try {
                androneThread = new AndroneThread.Builder()
                        .setFrequency(frequency)
                        .setWaveForm(waveForm)
                        .build();
                androneThread.start();
            } catch (IllegalStateException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
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

    public String getFrequencyDifferenceInCents() {
        // TODO
        return "± 0 cents";
    }

    public String getPitch() {
        // TODO
        return "A4";
    }

    public void setPitch() {
        // TODO
    }

    public void incrementFrequency() {
        // TODO
    }

    public void decrementFrequency() {
        // TODO
    }

    public void incrementPitch() {
        // TODO
    }

    public void decrementPitch() {
        // TODO
    }
}
