package com.boxnotfound.sinewavegenerator.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.boxnotfound.sinewavegenerator.R;

public class AndroneViewAdapter extends RecyclerView.Adapter<AndroneViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText androneName, androneFrequency;
        public TextView androneCents;
        public Spinner androneWaveform, andronePitch;
        public SeekBar androneVolume;
        public ImageButton androneFrequencyIncrement, androneFrequencyDecrement, andronePitchIncrement, andronePitchDecrement, andronePlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            androneName = itemView.findViewById(R.id.androne_name_edittext);
            androneFrequency = itemView.findViewById(R.id.frequency_edittext);
            androneCents = itemView.findViewById(R.id.cents_textview);
            androneWaveform = itemView.findViewById(R.id.waveform_spinner);
            andronePitch = itemView.findViewById(R.id.pitch_spinner);
            androneVolume = itemView.findViewById(R.id.volume_seekbar);
            androneFrequencyIncrement = itemView.findViewById(R.id.frequency_hz_increment);
            androneFrequencyDecrement = itemView.findViewById(R.id.frequency_hz_decrement);
            andronePitchIncrement = itemView.findViewById(R.id.pitch_increment);
            andronePitchDecrement = itemView.findViewById(R.id.pitch_decrement);
            andronePlay = itemView.findViewById(R.id.play_toggle_button);
        }
    }
}
