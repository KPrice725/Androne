package com.boxnotfound.sinewavegenerator.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.boxnotfound.sinewavegenerator.R;
import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.instance.Androne;

import java.util.ArrayList;

public class AndroneViewAdapter extends RecyclerView.Adapter<AndroneViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Androne> androneList;

    public AndroneViewAdapter(Context context, ArrayList<Androne> androneList) {
        this.context = context;
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
        final Androne androne = androneList.get(position);

        viewHolder.androneFrequency.setText(String.valueOf(androne.getFrequency()));
        viewHolder.androneFrequency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                androne.setFrequency(Double.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.androneCents.setText(androne.getCents());

        final Spinner waveformSpinner = viewHolder.androneWaveform;
        setSpinnerToValue(waveformSpinner, androne.getWaveForm().toString());
        waveformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = waveformSpinner.getSelectedItem().toString();
                androne.setWaveForm(WaveForm.valueOf(value));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner pitchSpinner = viewHolder.andronePitch;
        setSpinnerToValue(pitchSpinner, androne.getPitch());
        pitchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = pitchSpinner.getSelectedItem().toString();
                androne.setPitch(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SeekBar volumeSeekbar = viewHolder.androneVolume;
        volumeSeekbar.setProgress(androne.getVolume());

        viewHolder.androneFrequencyIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.incrementFrequency();
            }
        });

        viewHolder.androneFrequencyDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.decrementFrequency();
            }
        });

        viewHolder.andronePitchIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.incrementPitch();
            }
        });

        viewHolder.andronePitchDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.decrementPitch();
            }
        });

    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
    }

    @Override
    public int getItemCount() {
        return androneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText androneFrequency;
        public TextView androneCents;
        public Spinner androneWaveform, andronePitch;
        public SeekBar androneVolume;
        public ImageButton androneFrequencyIncrement, androneFrequencyDecrement, andronePitchIncrement, andronePitchDecrement, andronePlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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
