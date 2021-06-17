package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.ui.add.AddMeetingFragment;
import com.openclassrooms.mareu.ui.main.MainFragment;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrentIdRepositoryUnitTests {

    private CurrentIdRepository repo;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){repo = new CurrentIdRepository();}

    @Test
    public void checkNegativeValue() throws InterruptedException {
        //given
        int myId = -1;

        //when
        repo.setCurrentId(myId);

        //then
        assertTrue(LiveDataTestUtils.getOrAwaitValue(repo.getMasterFragment()) instanceof MainFragment);
    }

    @Test
    public void checkNullValue() throws InterruptedException {
        //given
        int myId = 0;

        //when
        repo.setCurrentId(myId);

        //then
        assertTrue(LiveDataTestUtils.getOrAwaitValue(repo.getDetailFragment()) instanceof AddMeetingFragment);
    }

    @Test
    public void checkPositiveValue() throws InterruptedException {
        //given
        int myId = 1;

        //when
        repo.setCurrentId(myId);

        //then
        assertEquals((Integer)myId, LiveDataTestUtils.getOrAwaitValue(repo.getCurrentDetailIdLiveData()));
        assertTrue(LiveDataTestUtils.getOrAwaitValue(repo.getDetailFragment()) instanceof ShowMeetingFragment);
    }
}
