package com.boxnotfound.sinewavegenerator;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.instance.Androne;
import com.boxnotfound.sinewavegenerator.instance.Pitch;
import com.boxnotfound.sinewavegenerator.ui.AndroneViewAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final double defaultFrequency = 440.0;
    private Androne sineAndrone, squareAndrone, triangleAndrone, sawtoothAndrone;
    private RecyclerView androneView;
    private AndroneViewAdapter androneViewAdapter;
    private RecyclerView.LayoutManager androneViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Pitch.instantiatePitches(this);
    }

    public void startSine(View view) {
        if (sineAndrone == null) {
            sineAndrone = new Androne.Builder()
                    .setPitch(defaultFrequency)
                    .setWaveForm(WaveForm.SINE)
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
                    .setWaveForm(WaveForm.SQUARE)
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
                    .setWaveForm(WaveForm.TRIANGLE)
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
                    .setWaveForm(WaveForm.SAWTOOTH)
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

    }
}
