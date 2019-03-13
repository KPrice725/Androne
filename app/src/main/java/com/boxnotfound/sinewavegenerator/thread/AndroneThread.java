package com.boxnotfound.sinewavegenerator.thread;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;

public class AndroneThread extends Thread {

    private static final String LOG_TAG = AndroneThread.class.getSimpleName();
    private boolean soundOn;
    private int sampleRate = 44100;
    private double frequency = 440.0;
    private WaveForm waveForm;

    @Override
    public void run() {
        super.run();
        soundOn = true;
        // int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        // TODO: Test latency and performance caused by setting bufferSize to match sampleRate
        int bufferSize = sampleRate;

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

        track.play();

        double currentPhase = 0.0;
        short sample[] = new short[bufferSize];
        int amplitude = 10000;

        label:
        while (soundOn) {
            double freq = frequency;
            switch (waveForm) {
                case SINE:
                    Log.i(LOG_TAG, "Sine Wave Sampling Started");
                    for (int i = 0; i < sample.length; i++) {
                        sample[i] = (short) (amplitude * Math.sin(currentPhase));
                        currentPhase += (2 * Math.PI * freq / sampleRate);
                    }
                    Log.i(LOG_TAG, "Sine Wave Sampling Finished.  Sin(currentPhase) = " + Math.sin(currentPhase));
                    // TODO: Test if resetting currentPhase to 0 affects anything negatively.
                    currentPhase = 0.0;
                    break;
                case SQUARE: {
                    Log.i(LOG_TAG, "Square Wave Sampling Started");
//                    for (int i = 0; i < sample.length; i++) {
//                        sample[i] = Math.sin(currentPhase) >= 0 ? (short) amplitude : (short) -amplitude;
//                        currentPhase += (2 * Math.PI * freq / sampleRate);
//                    }
                    int i = 0;
                    sample[i++] = (short) 0;
                    for (; i < sample.length / 2; i++) {
                        sample[i] = (short) amplitude;
                    }
                    for (; i < sample.length - 1; i++) {
                        sample[i] = (short) -amplitude;
                    }
                    sample[i] = (short) 0;
                } break;
                case TRIANGLE: {
                    Log.i(LOG_TAG, "Triangle Wave Sampling Started");
                    double slope = amplitude / (((1 / freq) * sampleRate) / 4);
                    int sampleIndex = 0;
                    int phaseIndex = 0;
                    for (int i = 0; i < sample.length / 4; i++) {
                        sample[sampleIndex++] = (short) (slope * phaseIndex++);
                    }
                    phaseIndex--;
                    for (int i = 0; i < sample.length / 2; i++) {
                        sample[sampleIndex++] = (short) (slope * phaseIndex--);
                    }
                    phaseIndex++;
                    for (int i = 0; i < sample.length / 4; i++) {
                        sample[sampleIndex++] = (short) (slope * phaseIndex++);
                    }
                    Log.d(LOG_TAG, "Current Phase Index: " + phaseIndex);
                } break;
                case SAWTOOTH:
                    Log.i(LOG_TAG, "Sawtooth Wave Sampling Started");

                    break;
                default:
                    Log.e(LOG_TAG, "Unknown WaveForm Selected");
                    soundOn = false;
                    break label;
            }
            track.write(sample, 0, sample.length);
            Log.d(LOG_TAG, "Phase after write: " + currentPhase);
        }
        track.stop();
        track.release();
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
