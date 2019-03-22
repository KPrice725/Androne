package com.boxnotfound.sinewavegenerator.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private static final String LOG_TAG = AndroneViewAdapter.class.getSimpleName();

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Androne androne = androneList.get(position);

        viewHolder.androneFrequency.setText(String.valueOf(androne.getFrequency()));
        viewHolder.androneFrequency.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String newFrequency = v.getText().toString();
                    if (newFrequency.length() > 0) {
                        androne.setFrequency(Double.valueOf(newFrequency));
                        notifyItemChanged(viewHolder.getAdapterPosition());
                        return true;
                    }
                }
                return false;
            }
        });

        viewHolder.androneCents.setText(androne.getCents());

        final Spinner waveformSpinner = viewHolder.androneWaveform;
        waveformSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewHolder.waveformSpinnerTouched = true;
                }
                return false;
            }
        });
        setSpinnerToValue(waveformSpinner, androne.getWaveForm().toString());
        waveformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (viewHolder.waveformSpinnerTouched) {
                    String value = waveformSpinner.getSelectedItem().toString();
                    androne.setWaveForm(WaveForm.valueOf(value));
                    viewHolder.waveformSpinnerTouched = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner pitchSpinner = viewHolder.andronePitch;
        setSpinnerToValue(pitchSpinner, androne.getPitch());
        pitchSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewHolder.pitchSpinnerTouched = true;
                }
                return false;
            }
        });
        pitchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (viewHolder.pitchSpinnerTouched) {
                    String value = pitchSpinner.getSelectedItem().toString();
                    androne.setPitch(value);
                    notifyItemChanged(viewHolder.getAdapterPosition());
                    viewHolder.pitchSpinnerTouched = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SeekBar volumeSeekbar = viewHolder.androneVolume;
        volumeSeekbar.setProgress(androne.getVolumeProgress());
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                androne.setVolumeProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        viewHolder.androneFrequencyIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.incrementFrequency();
                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.androneFrequencyDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.decrementFrequency();
                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.andronePitchIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.incrementPitch();
                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.andronePitchDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androne.decrementPitch();
                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });

        if (androne.isPlaying()) {
            viewHolder.andronePlay.setImageResource(R.drawable.ic_pause_black_24dp);
            viewHolder.andronePlay.setColorFilter(ContextCompat.getColor(context, R.color.colorPause));
        } else {
            viewHolder.andronePlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            viewHolder.andronePlay.setColorFilter(ContextCompat.getColor(context, R.color.colorPlay));
        }

        viewHolder.andronePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (androne.isPlaying()) {
                    androne.stopAndrone();
                    viewHolder.andronePlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    viewHolder.andronePlay.setColorFilter(ContextCompat.getColor(context, R.color.colorPlay), PorterDuff.Mode.SRC_ATOP);
                } else {
                    viewHolder.andronePlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    viewHolder.andronePlay.setColorFilter(ContextCompat.getColor(context, R.color.colorPause));
                    androne.startAndrone();
                }
                Log.d(LOG_TAG, "play button clicked: isPlaying now set to: " + androne.isPlaying());
                notifyItemChanged(viewHolder.getAdapterPosition());
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
        public ImageButton androneFrequencyIncrement, androneFrequencyDecrement, andronePitchIncrement, andronePitchDecrement;
        public ImageView andronePlay;

        public boolean pitchSpinnerTouched, waveformSpinnerTouched;

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

            //flag to ensure that uncontrolled calls to spinner onItemSelected listeners are not executed
            pitchSpinnerTouched = waveformSpinnerTouched = false;

        }
    }

//    private void updatePitchValues(Androne androne, ViewHolder viewHolder) {
//        setSpinnerToValue(viewHolder.andronePitch, androne.getPitch());
//        viewHolder.androneFrequency.setText(String.valueOf(androne.getFrequency()));
//        viewHolder.androneCents.setText(androne.getCents());
//        notifyItemChanged(viewHolder.getAdapterPosition());
//    }


}
