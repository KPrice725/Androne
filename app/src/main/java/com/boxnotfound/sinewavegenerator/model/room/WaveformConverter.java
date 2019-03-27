package com.boxnotfound.sinewavegenerator.model.room;

import android.arch.persistence.room.TypeConverter;

import com.boxnotfound.sinewavegenerator.constants.Waveform;

public class WaveformConverter {
    @TypeConverter
    public Waveform fromWaveformString(String waveform) {
        if (waveform == null) {
            return null;
        } else {
            try {
                return Waveform.valueOf(waveform);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @TypeConverter
    public String waveformToString(Waveform waveform) {
        return waveform == null ? null : waveform.toString();
    }
}
