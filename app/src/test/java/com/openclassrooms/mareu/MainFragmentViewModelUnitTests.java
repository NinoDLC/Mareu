package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.ui.main.MainFragmentViewModel;
import com.openclassrooms.mareu.ui.main.MainFragmentViewState;

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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MainFragmentViewModelUnitTests {

    private MainFragmentViewModel viewModel;
    private static final int EXPECTED_ID = 44;
    private static final int EXPECTED_HOUR = 8;
    private static final int EXPECTED_MINUTE = 41;
    // private static final int ROOMS_NUMBER = 10;

    private static final LiveData<List<Meeting>> EXPECTED_MEETINGS_LIVEDATA = new LiveData<List<Meeting>>(Arrays.asList(
            new Meeting(1, "marc@lamzone.fr", new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")), "Daily meetup", LocalDateTime.of(2021, 6, 14, 8, 30, 0), LocalDateTime.of(2021, 6, 14, 9, 35, 0), MeetingRoom.values()[3]),
            new Meeting(2, "tedy@buymore.fr", new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")), "Project xXx", LocalDateTime.of(2021, 6, 14, 16, 15, 0), LocalDateTime.of(2021, 6, 14, 16, 40, 0), MeetingRoom.values()[1])
    )) {
    };

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private CurrentIdRepository mCurrentIdRepository;

    @Mock
    private MeetingsRepository meetingsRepository;

    @Before
    public void setUp() {
        // todo: should not use (non Mutable) LiveData here, right?
        // given(masterDetailRepository.getMasterFragment()).willReturn(new MutableLiveData<>());
        // given(masterDetailRepository.getDetailFragment()).willReturn(new MutableLiveData<>());
        // given(masterDetailRepository.getCurrentDetailIdLiveData()).willReturn(new MutableLiveData<>());
        given(meetingsRepository.getMeetings()).willReturn(EXPECTED_MEETINGS_LIVEDATA);
        viewModel = new MainFragmentViewModel(meetingsRepository, mCurrentIdRepository);
    }

    @Test
    public void nominalCase() throws InterruptedException {
        // when
        Object result = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        assertTrue(result instanceof MainFragmentViewState);
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void callSetCurrentIdOnSetDetailId() {
        // when
        viewModel.setDetailId(EXPECTED_ID);

        // then
        verify(mCurrentIdRepository).setCurrentId(EXPECTED_ID);
        verify(meetingsRepository).getMeetings();
        //todo les verifyNoMoreInterraction sont de l'hygiène : on pourrait les mettre en @After ?
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void callRemoveMeetingByIdOnDeleteButtonClicked() {
        // when
        viewModel.deleteButtonClicked(EXPECTED_ID);

        // then
        verify(meetingsRepository).removeMeetingById(EXPECTED_ID);
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
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
        assertArrayEquals(expectedNames, receivedNames);
    }

    @Test
    public void nonNullResultOnGetRoomFilter() throws InterruptedException {
        // then
        assertNotNull(LiveDataTestUtils.getOrAwaitValue(viewModel.getRoomFilter()));
    }

    @Test
    public void setRoomFilter() throws InterruptedException {
        // given
        int position = 3;
        boolean[] expected = {
                true, true, true,
                false,
                true, true, true, true, true, true};
        // when
        viewModel.setRoomFilter(position, false);

        // then
        assertArrayEquals(expected, LiveDataTestUtils.getOrAwaitValue(viewModel.getRoomFilter()));
    }

    @Test
    public void setRoomFilterWithNonInitializedRoomFilter(){
        // todo: how to test it ?

    }

    @Test
    public void nonNullResultOnResetRoomFilter() throws InterruptedException {
        // when
        viewModel.resetRoomFilter();

        // then
        assertNotNull(LiveDataTestUtils.getOrAwaitValue(viewModel.getRoomFilter()));
    }

    @Test
    public void returnTimeFilterIfSet() {
        // when
        viewModel.setTimeFilter(EXPECTED_HOUR, EXPECTED_MINUTE);

        // then
        assertEquals(EXPECTED_HOUR, viewModel.getTimeFilterHour());
        assertEquals(EXPECTED_MINUTE, viewModel.getTimeFilterMinute());
    }

    @Test
    public void returnNowIfNoTimFilterSet() {
        // when
        viewModel.resetTimeFilter();

        // then
        assertEquals(LocalDateTime.now().getHour(), viewModel.getTimeFilterHour());
        assertEquals(LocalDateTime.now().getMinute(), viewModel.getTimeFilterMinute());
    }

    // List<Meeting> list = new ArrayList<>();
    // LocalDateTime localDateTimeFilter = LocalDateTime.of(2021, 6, 15, 8, 10, 0);


}
