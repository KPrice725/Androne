package com.boxnotfound.sinewavegenerator;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;
import com.boxnotfound.sinewavegenerator.instance.Androne;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int sampleRate = 44100;
    private Androne sineAndrone, squareAndrone, triangleAndrone, sawtoothAndrone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSine(View view) {
        sineAndrone = new Androne();
        sineAndrone.startAndrone(WaveForm.SINE);
    }

    public void stopSine(View view) {
        if (sineAndrone != null) {
            sineAndrone.stopAndrone();
            sineAndrone = null;
        }
    }

    public void startSquare(View view) {
        squareAndrone = new Androne();
        squareAndrone.startAndrone(WaveForm.SQUARE);
    }

    public void stopSquare(View view) {
        if (squareAndrone != null) {
            squareAndrone.stopAndrone();
            squareAndrone = null;
        }
    }

    public void startTriangle(View view) {
        triangleAndrone = new Androne();
        triangleAndrone.startAndrone(WaveForm.TRIANGLE);
    }

    public void stopTriangle(View view) {
        if (triangleAndrone != null) {
            triangleAndrone.stopAndrone();
            triangleAndrone = null;
        }
    }

    public void startSawtooth(View view) {
        sawtoothAndrone = new Androne();
        sawtoothAndrone.startAndrone(WaveForm.SAWTOOTH);
    }

    public void stopSawtooth(View view) {
        if (sawtoothAndrone != null) {
            sawtoothAndrone.stopAndrone();
            sawtoothAndrone = null;
        }
    }
}
