package com.boxnotfound.sinewavegenerator.model;

import android.content.Context;
import android.util.Log;

import com.boxnotfound.sinewavegenerator.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Pitch {

    private String pitch;
    private double frequency;
    private int cents;
    private int pitchListIndex;
    private static ArrayList<Pitch> pitchList;
    private static final String LOG_TAG = Pitch.class.getSimpleName();

    private static final double MAX_FREQUENCY = 20000.0;
    private static final double MIN_FREQUENCY = 40.0;


    private Pitch(String pitch, double frequency, int pitchListIndex) {
        this.pitch = pitch;
        this.frequency = frequency;
        this.pitchListIndex = pitchListIndex;
        cents = 0;
    }

    public Pitch(String pitch) throws IllegalArgumentException {
        Log.d(LOG_TAG, "Pitch(String pitch) constructor called!");
        this.pitch = pitch;
        for (int i = 0; i < pitchList.size(); i++) {
            Pitch p = pitchList.get(i);
            if (p.pitch.equals(pitch)) {
                frequency = p.frequency;
                pitchListIndex = i;
                break;
            }
        }
        if (frequency == 0.0) {
            throw new IllegalArgumentException("Invalid pitch argument: " + pitch);
        }
        cents = 0;
    }

    public Pitch(double frequency) throws IllegalArgumentException {
        Log.d(LOG_TAG, "Pitch(double frequency) constructor called!");
        if (frequency > MAX_FREQUENCY || frequency < MIN_FREQUENCY) {
            throw new IllegalArgumentException("Pitch Frequency must be between " + MIN_FREQUENCY + " and " + MAX_FREQUENCY + " hz!");
        }
        this.frequency = frequency;
        double pitchDifference = 0.0;

        /* Check the frequency of the middle value of the pitchList.
        If the frequency argument is less than or equal to the middle pitch frequency, iterate upward
        through the pitchList until the closest pitch match is found.  Calculate the difference in cents.
        If the frequency argument is greater than the middle pitch frequency, iterate downward instead.
        */
        if (frequency >= pitchList.get(pitchList.size() / 2).frequency) {
            for (int i = pitchList.size() / 2; i < pitchList.size(); i++) {
                Pitch p = pitchList.get(i);
                if (frequency == p.frequency) {
                    pitch = p.pitch;
                    pitchListIndex = i;
                    break;
                } else if (frequency < p.frequency) {
                    if (Math.abs(frequency - p.frequency) > pitchDifference) {
                        pitch = pitchList.get(i - 1).pitch;
                        pitchListIndex = i - 1;
                    } else {
                        pitch = p.pitch;
                        pitchListIndex = i;
                    }
                    break;
                } else {
                    pitchDifference = frequency - p.frequency;
                }
            }
            // frequency is higher than the highest note in the list, set this to that pitch.
            if (pitch == null) {
                pitchListIndex = pitchList.size() - 1;
                pitch = pitchList.get(pitchListIndex).pitch;
            }
        } else {
            for (int i = pitchList.size() / 2; i > 0; i--) {
                Pitch p = pitchList.get(i);
                if (frequency == p.frequency) {
                    pitch = p.pitch;
                    pitchListIndex = i;
                    break;
                } else if (frequency > p.frequency) {
                    if ((frequency - p.frequency) > Math.abs(pitchDifference)) {
                        pitch = pitchList.get(i + 1).pitch;
                        pitchListIndex = i + 1;
                    } else {
                        pitch = p.pitch;
                        pitchListIndex = i;
                    }
                    break;
                } else {
                    pitchDifference = frequency - p.frequency;
                }
            }
            // frequency is lower than the lowest note in the list, set this to that pitch
            if (pitch == null) {
                pitchListIndex = 0;
                pitch = pitchList.get(0).pitch;
            }
        }

        cents = calculateCents(frequency, pitchList.get(pitchListIndex).frequency);
    }

    public static Pitch atIndex(int index) {
        if (index < pitchList.size()) {
            return pitchList.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index exceeds Pitch List's range!");
        }
    }

    // There are 100 cents of distance per semitone, 1200 cents per octave.
    // cents = 1200 * Log2(pitchFrequency / frequencyOfClosestPitch)
    private int calculateCents(double frequency, double closestPitchFrequency) {
        double cents = Math.round(1200 * (Math.log(frequency / closestPitchFrequency) / Math.log(2)));
        return (int) cents;
    }

    //formula for calculating pitch frequency: pitch of A4 * 2 ^ ((number of semitones away from C4 - 9) / 12).
    //A4 is typically 440hz, but this value can differ depending on region
    // TODO: add functionality to allow user to customize A4 based on preference, adjust pitch series
    public static void instantiatePitches(Context context) {

        if (pitchList == null) {
            pitchList = new ArrayList<>();
        } else {
            pitchList.clear();
        }

        ArrayList<String> pitchNames = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.pitch_names)));
        int indexOfMiddleC = findMiddleC(pitchNames);
        for (int i = 0; i < pitchNames.size(); i++) {
            final String pitchName = pitchNames.get(i);
            final double frequency = 440.0 * Math.pow(2.0, ((i - indexOfMiddleC) - 9.0) / 12.0);
            pitchList.add(new Pitch(pitchName, frequency, i));
        }
    }

    private static int findMiddleC(ArrayList<String> pitchNames) {
        for (int i = 0; i < pitchNames.size(); i++) {
            if (pitchNames.get(i).equals("C4")) {
                return i;
            }
        }
        throw new IllegalArgumentException("Could not locate Middle C");
    }

    public double getFrequency() {
        return frequency;
    }

    public String getPitch() {
        return pitch;
    }

    public int getCents() {
        return cents;
    }

    public int getPitchListIndex() {
        return pitchListIndex;
    }

    public static int getPitchListSize() {
        return pitchList.size();
    }

    public static double getMaxFrequency() {
        return MAX_FREQUENCY;
    }

    public static double getMinFrequency() {
        return MIN_FREQUENCY;
    }
}
