package com.boxnotfound.sinewavegenerator.instance;

import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

public class Androne {

    private static final String LOG_TAG = Androne.class.getSimpleName();
    private AndroneThread androneThread;
    private Pitch pitch;
    private Waveform Waveform;
    private float volume;
    private boolean isPlaying;

    public static class Builder {
        private Pitch pitch;
        private Waveform Waveform;

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

        public Builder setWaveform(Waveform wf) {
            Waveform = wf;
            return this;
        }

        public Androne build() {
            if (pitch == null || Waveform == null) {
                throw new IllegalStateException("pitch and Waveform are both required");
            }
            return new Androne(pitch, Waveform);
        }
    }

    private Androne(Pitch p, Waveform wf) {
        pitch = p;
        Waveform = wf;
        volume = 1.0f;
        isPlaying = false;
    }

    public void startAndrone() {
        if (androneThread == null) {
            try {
                isPlaying = true;
                androneThread = new AndroneThread.Builder()
                        .setFrequency(pitch.getFrequency())
                        .setWaveform(Waveform)
                        .setVolume(volume)
                        .build();
                androneThread.start();
            } catch (IllegalStateException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }

    public void setFrequency(double f) throws IllegalArgumentException {
        pitch = new Pitch(f);
        if (androneThread != null) {
            androneThread.setFrequency(f);
        }
    }

    public double getFrequency() {
        return pitch.getFrequency();
    }

    public void setWaveform(Waveform wf) {
        Waveform = wf;
        if (androneThread != null) {
            androneThread.setWaveform(wf);
        }
    }

    public Waveform getWaveform() {
        return Waveform;
    }

    public void stopAndrone() {
        if (androneThread != null) {
            androneThread.stopSound();
            androneThread = null;
            isPlaying = false;
        }
    }

    public String getCents() {
        int cents = pitch.getCents();
        if (cents == 0) {
            return "± 0 ¢";
        } else if (cents > 0) {
            return "+ " + cents + " ¢";
        } else {
            return "- " + Math.abs(cents) + " ¢";
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
        if (progress > 100) {
            progress = 100;
        } else if (progress < 0) {
            progress = 0;
        }
        volume = progress / 100.0f;
        if (androneThread != null) {
            androneThread.setVolume(volume);
        }
        Log.d(LOG_TAG, "Set Volume to: " + volume);
    }

    public int getVolumeProgress() {
        int volumeProgress = (int) (volume * 100);
        Log.d(LOG_TAG, "Current volume received by seekbar: " + volumeProgress);
        return volumeProgress;
    }

    public void incrementFrequency() throws IllegalArgumentException {

        pitch = new Pitch(getFrequency() + 1);

        if (androneThread != null) {
            androneThread.setFrequency(pitch.getFrequency());
        }
    }

    public void decrementFrequency() throws IllegalArgumentException {

        pitch = new Pitch(getFrequency() - 1);

        if (androneThread != null) {
            androneThread.setFrequency(pitch.getFrequency());
        }
    }

    public void incrementPitch() {

        if (pitch.getPitchListIndex() + 1 < Pitch.getPitchListSize()) {
            pitch = Pitch.atIndex(pitch.getPitchListIndex() + 1);
            if (androneThread != null) {
                androneThread.setFrequency(pitch.getFrequency());
            }
        }
    }

    public void decrementPitch() {

        if (pitch.getPitchListIndex() - 1 >= 0) {
            pitch = Pitch.atIndex(pitch.getPitchListIndex() - 1);
            if (androneThread != null) {
                androneThread.setFrequency(pitch.getFrequency());
            }
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
