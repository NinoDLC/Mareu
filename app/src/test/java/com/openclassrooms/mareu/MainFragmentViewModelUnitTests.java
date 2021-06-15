package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.main.MainFragmentViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainFragmentViewModelUnitTests {

    private MeetingsRepository meetingsRepository;
    private MasterDetailRepository masterDetailRepository;
    private MainFragmentViewModel viewModel;

    @Rule
    InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        meetingsRepository = new MeetingsRepository();
        masterDetailRepository = new MasterDetailRepository();
        viewModel = new MainFragmentViewModel(meetingsRepository, masterDetailRepository);
    }





    @Test
    public void filterMeetingExceptionOnNullMeetings() {
        // given
        LocalDateTime localDateTimeFilter = LocalDateTime.of(2021, 6, 15, 8, 10, 0);
        List<Meeting> list = new ArrayList<>();

        // when
        // then
        viewModel.fil
    }


}
