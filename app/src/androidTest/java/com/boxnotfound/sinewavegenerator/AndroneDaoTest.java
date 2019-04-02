package com.boxnotfound.sinewavegenerator;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.persistence.room.Room;

import com.boxnotfound.sinewavegenerator.constants.Waveform;
import com.boxnotfound.sinewavegenerator.model.Androne;
import com.boxnotfound.sinewavegenerator.model.Pitch;
import com.boxnotfound.sinewavegenerator.model.source.AppDatabase;
import com.boxnotfound.sinewavegenerator.model.source.androne.AndroneDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import androidx.test.core.app.ApplicationProvider;

public class AndroneDaoTest {

    private MediatorLiveData<List<Androne>> mAndroneLive;
    private AppDatabase db;
    private AndroneDao dao;
    private Androne androne;
    private static final int ID = 1337;

    @Before
    public void setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                AppDatabase.class).build();
        dao = db.androneDao();
        Pitch.instantiatePitchNames(ApplicationProvider.getApplicationContext());
        androne = new Androne(new Pitch(440.0), Waveform.SINE);
        androne.setId(ID);
        mAndroneLive = new MediatorLiveData<>();
    }

    @After
    public void cleanup() {
        db.close();
        Pitch.releasePitchNames();
        mAndroneLive = null;
    }

    @Test
    public void checkDao() {
        assertThat(dao, notNullValue());
    }

    @Test
    public void insertAndroneAndGetId() {
        dao.insert(androne);

        Androne loaded = dao.getAndroneById(androne.getId());

        assertAndrone(loaded, androne.getId(),
                androne.getFrequency(), androne.getPitchName(),
                androne.getCents(), androne.getWaveform());
    }

    @Test
    public void insertAndroneReplacesOnConflict() {
        dao.insert(androne);

        Androne newAndrone = new Androne(new Pitch(400.0), Waveform.SAWTOOTH);
        newAndrone.setId(ID);

        dao.insert(newAndrone);

        Androne loadedAndrone = dao.getAndroneById(ID);

        assertAndrone(loadedAndrone, androne.getId(),
                newAndrone.getFrequency(), newAndrone.getPitchName(),
                newAndrone.getCents(), newAndrone.getWaveform());
    }

    @Test
    public void insertAndroneAndGetList() {
        dao.insert(androne);

        List<Androne> list = dao.getAllAndrones();

        assertThat(list, notNullValue());

        assertThat(list.size(), is(1));

        assertAndrone(list.get(0), androne.getId(),
                androne.getFrequency(), androne.getPitchName(),
                androne.getCents(), androne.getWaveform());
    }

    @Test
    public void updateAndroneAndGetById() {
        Androne updateTestAndrone = new Androne(new Pitch(440.0), Waveform.SINE);
        updateTestAndrone.setId(ID);
        dao.insert(updateTestAndrone);

        updateTestAndrone.setFrequency(880.0);
        updateTestAndrone.setWaveform(Waveform.SAWTOOTH);

        dao.updateAndrone(updateTestAndrone);

        Androne loadedAndrone = dao.getAndroneById(ID);

        assertAndrone(loadedAndrone, updateTestAndrone.getId(),
                updateTestAndrone.getFrequency(), updateTestAndrone.getPitchName(),
                updateTestAndrone.getCents(), updateTestAndrone.getWaveform());


    }

    private void assertAndrone(Androne loadedAndrone, int id,
                               double frequency, String pitchName,
                               String cents, Waveform waveform) {
        assertThat(loadedAndrone, notNullValue());
        assertThat(loadedAndrone.getId(), is(id));
        assertThat(loadedAndrone.getFrequency(), is(frequency));
        assertThat(loadedAndrone.getPitchName(), is(pitchName));
        assertThat(loadedAndrone.getCents(), is(cents));
        assertThat(loadedAndrone.getWaveform(), is(waveform));
    }
}
