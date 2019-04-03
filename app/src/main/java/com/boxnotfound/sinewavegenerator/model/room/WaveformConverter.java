package com.boxnotfound.sinewavegenerator.model.room;

import com.boxnotfound.sinewavegenerator.constants.Waveform;

import androidx.room.TypeConverter;

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
