package com.boxnotfound.sinewavegenerator.thread;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.util.Log;

import com.boxnotfound.sinewavegenerator.constants.WaveForm;

public class AndroneThread extends Thread {

    private static final String LOG_TAG = AndroneThread.class.getSimpleName();
    private boolean soundOn;
    private static int sampleRate = 44100;
    private double frequency;
    private WaveForm waveForm;

    public static class Builder {
        private double frequency;
        private WaveForm waveForm;

        public Builder setFrequency(double f) {
            frequency = f;
            return this;
        }

        public Builder setWaveForm(WaveForm wf) {
            waveForm = wf;
            return this;
        }

        public AndroneThread build() {
            if (frequency == 0.0 || waveForm == null) {
                throw new IllegalStateException("frequency and waveform are both required");
            }
            return new AndroneThread(frequency, waveForm);
        }

    }

    private AndroneThread(double f, WaveForm wf) {
        frequency = f;
        waveForm = wf;
    }

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
                        currentPhase += ((2 * Math.PI * freq) / sampleRate);
                        if (currentPhase > (2 * Math.PI)) {
                            currentPhase -= (2 * Math.PI);
                        }
                    }
                    // TODO: Test if resetting currentPhase to 0 affects anything negatively.
                    break;
                case SQUARE: {
                    Log.i(LOG_TAG, "Square Wave Sampling Started");
                    for (int i = 0; i < sample.length; i++) {
                        sample[i] = currentPhase < Math.PI ? (short) amplitude : (short) -amplitude;
                        currentPhase += (2 * Math.PI * freq / sampleRate);
                        if (currentPhase > (2 * Math.PI)) {
                            currentPhase -= (2 * Math.PI);
                        }
                    }

                } break;
                case TRIANGLE: {
                    Log.i(LOG_TAG, "Triangle Wave Sampling Started");
                    for (int i = 0; i < sample.length; i++) {
                        if (currentPhase < Math.PI) {
                            sample[i] = (short) (-amplitude + (2 * amplitude / Math.PI) * currentPhase);
                        } else {
                            sample[i] = (short) (3 * amplitude - (2 * amplitude / Math.PI) * currentPhase);
                        }
                        currentPhase += (2 * Math.PI * freq / sampleRate);
                        if (currentPhase > (2 * Math.PI)) {
                            currentPhase -= (2 * Math.PI);
                        }
                    }

                } break;
                case SAWTOOTH:
                    Log.i(LOG_TAG, "Sawtooth Wave Sampling Started");
                    for (int i = 0; i < sample.length; i++) {
                        sample[i] = (short) (amplitude - ((amplitude / Math.PI) * currentPhase));
                        currentPhase += (2 * Math.PI * freq / sampleRate);
                        if (currentPhase > (2 * Math.PI)) {
                            currentPhase -= (2 * Math.PI);
                        }
                    }
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
