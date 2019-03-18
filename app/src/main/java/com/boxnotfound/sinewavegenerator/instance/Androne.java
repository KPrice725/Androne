package com.boxnotfound.sinewavegenerator.instance;

import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

public class Androne {

    private static final String LOG_TAG = Androne.class.getSimpleName();
    private AndroneThread androneThread;
    private Pitch pitch;
    private WaveForm waveForm;


    public static class Builder {
        //in case of no passed frequency and/or waveform, default to 440hz Sine Wave
        private Pitch pitch;
        private WaveForm waveForm = WaveForm.SINE;
        public Builder setPitch(double f) {
            pitch = new Pitch(f);
            return this;
        }
        public Builder setPitch(String pitchName) {
            pitch = new Pitch(pitchName);
            return this;
        }
        public Builder setWaveForm(WaveForm wf) {
            waveForm = wf;
            return this;
        }
        public Androne build() {
            return new Androne(pitch, waveForm);
        }
    }

    private Androne(Pitch p, WaveForm wf) {
        pitch = p;
        waveForm = wf;
    }

    public void startAndrone() {
        if (androneThread == null) {
            try {
                androneThread = new AndroneThread.Builder()
                        .setFrequency(pitch.getFrequency())
                        .setWaveForm(waveForm)
                        .build();
                androneThread.start();
            } catch (IllegalStateException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }

    public void setFrequency(double f) {
        pitch = new Pitch(f);
        if (androneThread != null) {
            androneThread.setFrequency(f);
        }
    }

    public double getFrequency() {
        return pitch.getFrequency();
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

    public String getCents() {
        // TODO
        int cents = pitch.getCents();
        if (cents == 0) {
            return "±0 cents";
        } else {
            return String.valueOf(cents) + " cents";
        }

    }

    public String getPitch() {
        return pitch.getPitch();
    }

    public void setPitch(String pitchName) {
        pitch = new Pitch(pitchName);
        if (androneThread != null) {
            androneThread.setFrequency(pitch.getFrequency());
        }
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
