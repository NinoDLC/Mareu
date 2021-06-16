package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MasterDetailRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.main.MainFragmentViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MainFragmentViewModelUnitTests {

    private MainFragmentViewModel viewModel;
    private static final int EXPECTED_ID = 44;

    private static final LiveData<List<Meeting>> EXPECTED_MEETINGS_LIVEDATA = new LiveData<List<Meeting>>(Arrays.asList(
            new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]),
            new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1])
    )) {
    };

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MasterDetailRepository masterDetailRepository;

    @Mock
    private MeetingsRepository meetingsRepository;

    @Before
    public void setUp() {
        // todo: should not use (non Mutable) LiveData here, right?
        given(masterDetailRepository.getMasterFragment()).willReturn(new MutableLiveData<>());
        given(masterDetailRepository.getDetailFragment()).willReturn(new MutableLiveData<>());
        given(masterDetailRepository.getCurrentDetailIdLiveData()).willReturn(new MutableLiveData<>());
        given(meetingsRepository.getMeetings()).willReturn(EXPECTED_MEETINGS_LIVEDATA);
        viewModel = new MainFragmentViewModel(meetingsRepository, masterDetailRepository);
    }

    @Test
    public void nominalCase() {
        // when
        viewModel.getViewStateListLiveData();

        // then
        verify(meetingsRepository).getMeetings();
//        verify()
    }


    @Test
    public void clickOnItemOpensItemInDetailFragment() {
        // given
        // when
        viewModel.setDetailId(EXPECTED_ID);

        // then
        verify(masterDetailRepository).getDetailFragment();
        verifyNoMoreInteractions(masterDetailRepository);
    }

    @Test
    public void getMeetingRoomNames() {
        // given
        CharSequence[] expectedNames = {
                "Braque-de-Weimar", "Doberman", "Dogue-Allemand", "Caniche", "Jack-Russel",
                "Fox-Terrier", "Labrador", "Basset", "Cocker", "Epagnol"
        };

        // when
        CharSequence[] receivedNames = viewModel.getMeetingRoomNames();

        // then
        assertTrue(Arrays.deepEquals(expectedNames, receivedNames));
    }

    // List<Meeting> list = new ArrayList<>();
    // LocalDateTime localDateTimeFilter = LocalDateTime.of(2021, 6, 15, 8, 10, 0);


}
