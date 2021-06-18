package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrentIdRepositoryUnitTests {

    private CurrentIdRepository repo;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repo = new CurrentIdRepository();
    }

    @Test
    public void nominalCase() throws InterruptedException {
        //given
        int myId = 1;

        //when
        repo.setCurrentId(myId);

        //then
        assertEquals((Integer) myId, LiveDataTestUtils.getOrAwaitValue(repo.getCurrentIdLiveData()));
    }
}
