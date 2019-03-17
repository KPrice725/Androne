package com.boxnotfound.sinewavegenerator.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.boxnotfound.sinewavegenerator.R;
import com.boxnotfound.sinewavegenerator.instance.Androne;

import java.util.ArrayList;

public class AndroneViewAdapter extends RecyclerView.Adapter<AndroneViewAdapter.ViewHolder> {

    private ArrayList<Androne> androneList;

    public AndroneViewAdapter(ArrayList<Androne> androneList) {
        this.androneList = androneList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View androneView = inflater.inflate(R.layout.androne_rv_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(androneView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Androne androne = androneList.get(position);
    }

    @Override
    public int getItemCount() {
        return androneList.size();
    }

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
