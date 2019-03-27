package com.boxnotfound.sinewavegenerator.model.room;

import android.arch.persistence.room.TypeConverter;

import com.boxnotfound.sinewavegenerator.model.Pitch;

public class PitchConverter {
    @TypeConverter
    public Pitch fromFrequency(Double frequency) {
        return frequency == null ? null : new Pitch(frequency);
    }

    @TypeConverter
    public Double pitchToFrequency(Pitch pitch) {
        return pitch == null ? null : pitch.getFrequency();
    }
}
