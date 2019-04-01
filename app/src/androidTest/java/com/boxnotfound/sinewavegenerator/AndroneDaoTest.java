package com.boxnotfound.sinewavegenerator;

import android.arch.persistence.room.Room;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.Pitch;
import com.boxnotfound.sinewavegenerator.model.source.AppDatabase;
import com.boxnotfound.sinewavegenerator.model.source.androne.AndroneDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import androidx.test.core.app.ApplicationProvider;

public class AndroneDaoTest {

    private AppDatabase db;
    private AndroneDao dao;
    private Androne androne;

    @Before
    public void setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase.class).build();
        dao = db.androneDao();
        Pitch.instantiatePitchNames(ApplicationProvider.getApplicationContext());
        androne = new Androne(new Pitch(440.0), Waveform.SINE);
    }

    @After
    public void cleanup() {
        db.close();
        Pitch.releasePitchNames();
    }

    @Test
    public void checkDao() {
        assertThat(dao, notNullValue());
    }

    @Test
    public void insertAndroneAndGetId() {
        dao.insert(androne);

        Androne loaded = dao.getAndroneById(androne.getId());

        assertAndrone(loaded, androne.getId(), androne.getFrequency(), androne.getPitchName(), androne.getCents(), androne.getWaveform());
    }

    private void assertAndrone(Androne loadedAndrone, int id, double frequency, String pitchName, String cents, Waveform waveform) {
        assertThat(loadedAndrone, notNullValue());
        assertThat(loadedAndrone.getId(), is(id));
        assertThat(loadedAndrone.getFrequency(), is(frequency));
        assertThat(loadedAndrone.getPitchName(), is(pitchName));
        assertThat(loadedAndrone.getCents(), is(cents));
        assertThat(loadedAndrone.getWaveform(), is(waveform));
    }
}
