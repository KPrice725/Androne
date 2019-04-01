package com.boxnotfound.sinewavegenerator.model;

import android.content.Context;
import android.util.Log;

import com.boxnotfound.sinewavegenerator.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Pitch {

    private static final String MIDDLE_C = "C4";
    private static double pitchStandardInHz = 440.0;

    private int id;

    private String pitchName;

    private double frequency;

    private int cents;

    private int pitchListIndex;

    private static ArrayList<String> pitchNames;

    private static final String LOG_TAG = Pitch.class.getSimpleName();

    private static final double MAX_FREQUENCY = 20000.0;

    private static final double MIN_FREQUENCY = 40.0;

    public Pitch(final String pitchName) throws IllegalArgumentException {
        Log.d(LOG_TAG, "Pitch(String pitchName) constructor called!");
        this.pitchName = pitchName;
        frequency = getFrequencyFromPitchName(pitchName);
        if (frequency == 0.0) {
            throw new IllegalArgumentException("Invalid pitchName argument: " + pitchName);
        }
        // Since the pitch was instantiated with a definitive pitch name, cents will always be 0
        cents = 0;
        pitchListIndex = pitchNames.indexOf(pitchName);
    }

    public Pitch(final double frequency) throws IllegalArgumentException {
        Log.d(LOG_TAG, "Pitch(double frequency) constructor called!");
        if (frequency > MAX_FREQUENCY || frequency < MIN_FREQUENCY) {
            throw new IllegalArgumentException("Pitch Frequency must be between " + MIN_FREQUENCY + " and " + MAX_FREQUENCY + " hz!");
        }

        this.frequency = frequency;
        pitchName = getPitchNameFromFrequency(frequency);
        final double closestPitchFrequency = getFrequencyFromPitchName(pitchName);
        // Since the pitch was instantiated with a frequency,
        // cents will tell user how far away frequency is to closest "in tune" pitch
        cents = calculateCents(frequency, closestPitchFrequency);
        pitchListIndex = pitchNames.indexOf(pitchName);
    }

    //formula for calculating pitch frequency: pitch of A4 * 2 ^ ((number of semitones away from C4 - 9) / 12).
    //A4 is typically 440hz, but this value can differ depending on region
    // TODO: add functionality to allow user to customize A4 based on preference, adjust pitch series
    // TODO: Integrate this with new Room PitchRepository
    private double getFrequencyFromPitchName(final String pitchName) {
        int indexOfPitchName = pitchNames.indexOf(pitchName);
        if (indexOfPitchName == -1) {
            throw new IllegalArgumentException("Error: Invalid Pitch Name: " + pitchName);
        }
        int indexOfMiddleC = pitchNames.indexOf(MIDDLE_C);
        if (indexOfMiddleC == -1) {
            throw new IllegalArgumentException("Error: Could Not Locate Middle C");
        }

        final double frequency = pitchStandardInHz * Math.pow(2.0, ((indexOfPitchName - indexOfMiddleC) - 9.0) / 12.0);

        return frequency;
    }

    private String getPitchNameFromFrequency(final double frequency) {
        double pitchDifference = 0.0;
        final int indexOfMiddleC = pitchNames.indexOf(MIDDLE_C);
        final double frequencyOfMiddleC = getFrequencyFromPitchName(MIDDLE_C);
        /* Check the frequency of middle c in the pitchNames array.
        If the frequency argument is greater than or equal to middle c's frequency, iterate upward
        through the pitchNames until the closest pitch match is found.
        If the frequency argument is less than the middle pitch frequency, iterate downward instead.
        */

        if (frequency >= frequencyOfMiddleC) {
            for (int i = indexOfMiddleC; i < pitchNames.size(); i++) {
                final String currentPitchName = pitchNames.get(i);
                final double currentPitchFrequency = getFrequencyFromPitchName(currentPitchName);
                if (frequency == currentPitchFrequency) {
                    pitchName = currentPitchName;
                    break;
                } else if (frequency < currentPitchFrequency) {
                    if (Math.abs(frequency - currentPitchFrequency) > pitchDifference) {
                        pitchName = pitchNames.get(i - 1);
                    } else {
                        pitchName = currentPitchName;
                    }
                    break;
                } else {
                    pitchDifference = frequency - currentPitchFrequency;
                }
            }
            // frequency is higher than the highest note in the list, set this to that pitchName.
            if (pitchName == null) {
                pitchName = pitchNames.get(pitchNames.size() - 1);
            }
        } else {
            for (int i = indexOfMiddleC; i > 0; i--) {
                final String currentPitchName = pitchNames.get(i);
                final double currentPitchFrequency = getFrequencyFromPitchName(currentPitchName);
                if (frequency == currentPitchFrequency) {
                    pitchName = currentPitchName;
                    break;
                } else if (frequency > currentPitchFrequency) {
                    if ((frequency - currentPitchFrequency) > Math.abs(pitchDifference)) {
                        pitchName = pitchNames.get(i + 1);
                    } else {
                        pitchName = currentPitchName;
                    }
                    break;
                } else {
                    pitchDifference = frequency - currentPitchFrequency;
                }
            }
            // frequency is lower than the lowest note in the list, set this to that pitchName
            if (pitchName == null) {
                pitchName = pitchNames.get(0);
            }
        }
        return pitchName;
    }

    public static Pitch atIndex(int index) {
        if (index < pitchNames.size()) {
            return new Pitch(pitchNames.get(index));
        } else {
            throw new IndexOutOfBoundsException("Index exceeds Pitch List's range!");
        }
    }

    // There are 100 cents of distance per semitone, 1200 cents per octave.
    // cents = 1200 * Log2(pitchFrequency / frequencyOfClosestPitch)
    private int calculateCents(final double frequency, final double closestPitchFrequency) {
        final double cents = Math.round(1200 * (Math.log(frequency / closestPitchFrequency) / Math.log(2)));
        return (int) cents;
    }

    public static void instantiatePitchNames(Context context) {

        if (pitchNames == null) {
            pitchNames = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.pitch_names)));
        }
    }

    public static void releasePitchNames() {
        if (pitchNames != null) {
            pitchNames.clear();
            pitchNames = null;
        }
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
        this.pitchName = getPitchNameFromFrequency(frequency);
        this.cents = calculateCents(frequency, getFrequencyFromPitchName(pitchName));
        this.pitchListIndex = pitchNames.indexOf(pitchName);
    }

    public String getPitchName() {
        return pitchName;
    }

    public void setPitchName(String pitchName) {
        this.pitchName = pitchName;
        this.frequency = getFrequencyFromPitchName(pitchName);
        this.cents = 0;
        this.pitchListIndex = pitchNames.indexOf(pitchName);
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public int getPitchListIndex() {
        return pitchListIndex;
    }

    public void setPitchListIndex(int pitchListIndex) {
        this.pitchListIndex = pitchListIndex;
    }

    public static int getPitchListSize() {
        return pitchNames.size();
    }

    public static double getMaxFrequency() {
        return MAX_FREQUENCY;
    }

    public static double getMinFrequency() {
        return MIN_FREQUENCY;
    }
}
