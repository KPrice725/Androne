package com.boxnotfound.sinewavegenerator.model.room;


import com.boxnotfound.sinewavegenerator.model.Pitch;

import androidx.room.TypeConverter;

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
