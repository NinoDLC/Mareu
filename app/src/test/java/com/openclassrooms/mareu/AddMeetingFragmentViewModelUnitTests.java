package com.openclassrooms.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.add.AddMeetingFragmentViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AddMeetingFragmentViewModelUnitTests {

    private AddMeetingFragmentViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MainApplication application;

    @Mock
    MeetingsRepository meetingsRepository;

/*
    @Before
    public void setUp() {
        viewModel = new AddMeetingFragmentViewModel(application, meetingsRepository);
    }

    @Test
    public void nominalCase(){
        viewModel.setRoom(4);
    }

*/

    @Test
    public void dumb() {
        assertTrue(true);
    }

}