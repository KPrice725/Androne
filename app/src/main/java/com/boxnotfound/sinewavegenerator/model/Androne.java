package com.boxnotfound.sinewavegenerator.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.room.PitchConverter;
import com.boxnotfound.sinewavegenerator.model.room.WaveformConverter;
import com.boxnotfound.sinewavegenerator.thread.AndroneThread;

@Entity(tableName = "androne_table")
public class Androne {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "androne_name")
    private String androneName;

    @ColumnInfo(name = "frequency")
    @TypeConverters({PitchConverter.class})
    private Pitch pitch;

    @ColumnInfo(name = "waveform")
    @TypeConverters({WaveformConverter.class})
    private Waveform waveform;

    @ColumnInfo(name = "current_volume")
    private float volume;

    @Ignore
    private AndroneThread androneThread;

    @Ignore
    private boolean isPlaying;

    @Ignore
    private static final String LOG_TAG = Androne.class.getSimpleName();

    public static class Builder {
        private Pitch pitch;
        private Waveform waveform;

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
            waveform = wf;
            return this;
        }

        public Androne build() {
            if (pitch == null || waveform == null) {
                throw new IllegalStateException("pitch and Waveform are both required");
            }
            return new Androne(pitch, waveform);
        }
    }

    private Androne(Pitch p, Waveform wf) {
        pitch = p;
        waveform = wf;
        volume = 1.0f;
        androneName = "default name";
        isPlaying = false;
    }

    public void startAndrone() {
        if (androneThread == null) {
            try {
                isPlaying = true;
                androneThread = new AndroneThread.Builder()
                        .setFrequency(pitch.getFrequency())
                        .setWaveform(waveform)
                        .setVolume(volume)
                        .build();
                androneThread.start();
            } catch (IllegalStateException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }

    public int getId() {
        return id;
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
        waveform = wf;
        if (androneThread != null) {
            androneThread.setWaveform(wf);
        }
    }

    public Waveform getWaveform() {
        return waveform;
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

    public void setVolume(float v) {
        volume = v;
    }

    public float getVolume() {
        return volume;
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

    public String getAndroneName() {
        return androneName;
    }

    public void setAndroneName(String androneName) {
        this.androneName = androneName;
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
