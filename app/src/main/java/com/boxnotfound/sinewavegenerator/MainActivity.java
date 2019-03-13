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

import com.boxnotfound.sinewavegenerator.instance.Androne;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final int duration = 3; // seconds
    private final int sampleRate = 44100;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final double freqOfTone = 440; // hz
    private Androne androne;

    private final byte generatedSnd[] = new byte[2 * numSamples];

    Handler handler = new Handler();

    AudioTrack instantiatedTrack, builderTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                generateTone();
            }
        });
        thread.start();

    }

    public void generateTone() {
        //generate sine wave
        for (int i = 0; i < numSamples; i++) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
        }

        //convert samples to 16 bit pcm sound array
        int idx = 0;
        for (final double dVal : sample) {
            final short val = (short) ((dVal * 32767));
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }


    public void startInstantiatedSound(View view) {
        androne = new Androne();
        androne.startAndrone();
    }

    public void startBuilderSound(View view) {
        if (androne != null) {
            androne.stopAndrone();
            androne = null;
        }
    }
}
