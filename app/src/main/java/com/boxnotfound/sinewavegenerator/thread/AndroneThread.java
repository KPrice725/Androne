package com.boxnotfound.sinewavegenerator.thread;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;

public class AndroneThread extends Thread {

    private boolean soundOn;
    private int sampleRate = 44100;
    private double frequency = 440.0;
    private WaveForm waveForm;

    @Override
    public void run() {
        super.run();
        soundOn = true;
        int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();

        AudioFormat audioFormat = new AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build();

        AudioTrack track = new AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(bufferSize)
                .build();


    }

    public void setFrequency(double f) {
        frequency = f;
    }

    public void setWaveForm(WaveForm wf) {
        waveForm = wf;
    }

    public void stopSound() {
        soundOn = false;

        try {
            this.join();
            this.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
