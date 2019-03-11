package com.boxnotfound.sinewavegenerator;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final int duration = 3; // seconds
    private final int sampleRate = 44100;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final double freqOfTone = 440; // hz

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
        handler.post(new Runnable() {
            @Override
            public void run() {
                instantiatedTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length, AudioTrack.MODE_STATIC);
                instantiatedTrack.write(generatedSnd, 0, generatedSnd.length);
                instantiatedTrack.play();
            }
        });

    }

    public void startBuilderSound(View view) {
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (builderTrack == null) {
        /*
        AudioAttributes: A class to encapsulate a collection of attributes describing information about an audio stream.
        AudioAttributes supersede the notion of stream types (see for instance AudioManager.STREAM_MUSIC or AudioManager.STREAM_ALARM)
        for defining the behavior of audio playback. Attributes allow an application to specify more information than is conveyed in a
        stream type by allowing the application to define
        */
                    AudioAttributes.Builder attributesBuilder = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA) // why you are playing a sound
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC); // what you are playing

        /*
        AudioFormat: The AudioFormat class is used to access a number of audio format and channel configuration constants.
        They are for instance used in AudioTrack and AudioRecord, as valid values in individual parameters of constructors
        like AudioTrack.AudioTrack(int, int, int, int, int, int), where the fourth parameter is one of the
        AudioFormat.ENCODING_* constants. The AudioFormat constants are also used in MediaFormat to specify audio related
        values commonly used in media, such as for MediaFormat.KEY_CHANNEL_MASK. The AudioFormat.Builder class can be used
        to create instances of the AudioFormat format class. Refer to AudioFormat.Builder for documentation on the mechanics
        of the configuration and building of such instances. Here we describe the main concepts that the AudioFormat class
        allow you to convey in each instance, they are:
        sample rate, encoding, channel masks
        Closely associated with the AudioFormat is the notion of an audio frame, which is used throughout the documentation
        to represent the minimum size complete unit of audio data.
        */

                    AudioFormat.Builder formatBuilder = new AudioFormat.Builder()
                            .setSampleRate(sampleRate) //number of audio samples for each channel per second
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT) // describes the bit representation of audio data, which can be either linear PCM (Pulse Code Modulation) or compressed audio, such as AC3 or DTS.
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO); //describes the samples and their arrangement in the audio frame


                    AudioTrack.Builder trackBuilder = new AudioTrack.Builder()
                            .setAudioAttributes(attributesBuilder.build())
                            .setAudioFormat(formatBuilder.build())
                            .setBufferSizeInBytes(generatedSnd.length);

                    builderTrack = trackBuilder.build();
                }
                builderTrack.write(generatedSnd, 0, generatedSnd.length);

                builderTrack.play();
            }
        });

    }
}
