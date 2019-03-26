package com.boxnotfound.sinewavegenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.Pitch;
import com.boxnotfound.sinewavegenerator.ui.AndroneViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final double defaultFrequency = 440.0;
    private Androne sineAndrone, squareAndrone, triangleAndrone, sawtoothAndrone;
    private RecyclerView androneView;
    private ArrayList<Androne> androneList;
    private AndroneViewAdapter androneViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Pitch.instantiatePitches(this);

        androneView = findViewById(R.id.rv_androne);

        androneList = new ArrayList<>();
        androneList.add(new Androne.Builder().setPitch("A4").setWaveform(Waveform.SINE).build());
        androneViewAdapter = new AndroneViewAdapter(MainActivity.this, androneList);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        androneView.setLayoutManager(manager);
        androneView.addItemDecoration(new DividerItemDecoration(androneView.getContext(), manager.getOrientation()));
        androneView.setAdapter(androneViewAdapter);

    }

    public void startSine(View view) {
        if (sineAndrone == null) {
            sineAndrone = new Androne.Builder()
                    .setPitch(defaultFrequency)
                    .setWaveform(Waveform.SINE)
                    .build();
        }
        sineAndrone.startAndrone();
    }

    public void stopSine(View view) {
        if (sineAndrone != null) {
            sineAndrone.stopAndrone();
        }
    }

    public void startSquare(View view) {
        if (squareAndrone == null) {
            squareAndrone = new Androne.Builder()
                    .setPitch(defaultFrequency)
                    .setWaveform(Waveform.SQUARE)
                    .build();
        }
        squareAndrone.startAndrone();
    }

    public void stopSquare(View view) {
        if (squareAndrone != null) {
            squareAndrone.stopAndrone();
        }
    }

    public void startTriangle(View view) {
        if (triangleAndrone == null) {
            triangleAndrone = new Androne.Builder()
                    .setPitch(defaultFrequency)
                    .setWaveform(Waveform.TRIANGLE)
                    .build();
        }
        triangleAndrone.startAndrone();
    }

    public void stopTriangle(View view) {
        if (triangleAndrone != null) {
            triangleAndrone.stopAndrone();
        }
    }

    public void startSawtooth(View view) {
        if (sawtoothAndrone == null) {
            sawtoothAndrone = new Androne.Builder()
                    .setPitch(defaultFrequency)
                    .setWaveform(Waveform.SAWTOOTH)
                    .build();
        }
        sawtoothAndrone.startAndrone();
    }

    public void stopSawtooth(View view) {
        if (sawtoothAndrone != null) {
            sawtoothAndrone.stopAndrone();
        }
    }

    public void createAndrone(View view) {
        Androne newAndrone = new Androne.Builder().setPitch("A4").setWaveform(Waveform.SINE).build();
        androneList.add(newAndrone);
        androneViewAdapter.notifyItemInserted(androneList.indexOf(newAndrone));
    }
}
