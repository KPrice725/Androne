package com.boxnotfound.sinewavegenerator.instance;

import android.content.Context;

import com.boxnotfound.sinewavegenerator.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Pitch {

    private String pitch;
    private double frequency;
    private int cents;
    private static ArrayList<Pitch> pitchList;

    private Pitch(String pitch, double frequency) {
        this.pitch = pitch;
        this.frequency = frequency;
        cents = 0;
    }

    public Pitch(String pitch) {
        this.pitch = pitch;
        for (Pitch p : pitchList) {
            if (p.pitch.equals(pitch)) {
                frequency = p.frequency;
                break;
            }
        }
        if (frequency == 0.0) {
            throw new IllegalArgumentException("Invalid pitch argument: " + pitch);
        }
        cents = 0;
    }

    public Pitch(double frequency) {
        this.frequency = frequency;
        double pitchDifference = 0.0;
        int pitchListIndex = 0;

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

    // There are 100 cents of distance per semitone, 1200 cents per octave.
    // cents = 1200 * Log2(pitchFrequency / frequencyOfClosestPitch)
    private int calculateCents(double frequency, double closestPitchFrequency) {
        return (int) Math.round(1200 * (Math.log(frequency / closestPitchFrequency) / Math.log(2)));
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
            pitchList.add(new Pitch(pitchName, frequency));
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
}
