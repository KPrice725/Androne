package com.boxnotfound.sinewavegenerator.instance;

import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

public class Androne {

    private static final String LOG_TAG = Androne.class.getSimpleName();
    private AndroneThread androneThread;
    private Pitch pitch;
    private WaveForm waveForm;
    private float volume;


    public static class Builder {
        private Pitch pitch;
        private WaveForm waveForm;
        public Builder setPitch(double f) {
            pitch = new Pitch(f);
            return this;
        }
        public Builder setPitch(String pitchName) {
            pitch = new Pitch(pitchName);
            return this;
        }
        public Builder setPitch(Pitch p) {
            pitch = p;
            return this;
        }
        public Builder setWaveForm(WaveForm wf) {
            waveForm = wf;
            return this;
        }
        public Androne build() {
            if (pitch == null || waveForm == null) {
                throw new IllegalStateException("pitch and waveform are both required");
            }
            return new Androne(pitch, waveForm);
        }
    }

    private Androne(Pitch p, WaveForm wf) {
        pitch = p;
        waveForm = wf;
        volume = 1.0f;
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
        int cents = pitch.getCents();
        if (cents == 0) {
            return "±0 ¢";
        } else {
            return String.valueOf(cents) + " ¢";
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

    public void setVolumeProgress(int progress) {
        volume = progress / 100.0f;
        Log.d(LOG_TAG, "Set Volume to: " + volume);
    }

    public int getVolumeProgress() {
        int volumeProgress = (int) volume * 100;
        Log.d(LOG_TAG, "Current volume received by seekbar: " + volumeProgress);
        return volumeProgress;
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
