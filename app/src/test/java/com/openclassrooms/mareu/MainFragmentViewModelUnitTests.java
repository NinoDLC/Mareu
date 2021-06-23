package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.testUtils.TestsMeetingsList;
import com.openclassrooms.mareu.ui.main.MainFragmentViewModel;
import com.openclassrooms.mareu.ui.main.MainFragmentViewState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MainFragmentViewModelUnitTests {

    private MainFragmentViewModel viewModel;
    private Clock clock;
    private static final int MAGIC_MEETING_ID = 44;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private CurrentIdRepository mCurrentIdRepository;

    @Mock
    private MeetingsRepository meetingsRepository;

    @Before
    public void setUp() {
        clock = Clock.fixed(utils.ARBITRARY_DAY.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        given(meetingsRepository.getMeetings()).willReturn(new MutableLiveData<>(TestsMeetingsList.MEETING_LIST));
        viewModel = new MainFragmentViewModel(meetingsRepository, mCurrentIdRepository, clock);

        verify(meetingsRepository).getMeetings();
    }

    @Test
    public void nominalCase() throws InterruptedException {
        // when
        List<MainFragmentViewState> result = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);

        // ViewState list should have 20 items
        assertEquals(20, result.size());

        // and should be sorted
        // TODO export that as a "EXPECTED MainFragmentViewState list"
        assertEquals(1, result.get(0).getId());
        assertEquals(9, result.get(1).getId());
        assertEquals(12, result.get(2).getId());
        assertEquals(20, result.get(3).getId());
        assertEquals(5, result.get(4).getId());
        assertEquals(7, result.get(5).getId());
        assertEquals(8, result.get(6).getId());
        assertEquals(14, result.get(7).getId());
        assertEquals(4, result.get(8).getId());
        assertEquals(6, result.get(9).getId());
        assertEquals(17, result.get(10).getId());
        assertEquals(10, result.get(11).getId());
        assertEquals(11, result.get(12).getId());
        assertEquals(13, result.get(13).getId());
        assertEquals(18, result.get(14).getId());
        assertEquals(19, result.get(15).getId());
        assertEquals(2, result.get(16).getId());
        assertEquals(3, result.get(17).getId());
        assertEquals(15, result.get(18).getId());
        assertEquals(16, result.get(19).getId());
    }

    @Test
    public void withUnselectedRooms() throws InterruptedException {
        // when
        viewModel.setRoomFilter(MeetingRoom.ROOM_1.ordinal(), false);
        viewModel.setRoomFilter(MeetingRoom.ROOM_2.ordinal(), false);
        viewModel.setRoomFilter(MeetingRoom.ROOM_3.ordinal(), false);
        viewModel.setRoomFilter(MeetingRoom.ROOM_6.ordinal(), false);
        viewModel.setRoomFilter(MeetingRoom.ROOM_7.ordinal(), false);
        viewModel.setRoomFilter(MeetingRoom.ROOM_8.ordinal(), false);
        viewModel.setRoomFilter(MeetingRoom.ROOM_9.ordinal(), false);
        List<MainFragmentViewState> result = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        assertEquals(3, result.size());
        assertEquals(20, result.get(0).getId());
        assertEquals(19, result.get(1).getId());
        assertEquals(15, result.get(2).getId());
    }

    @Test
    public void withTimeFilterAndOneUnselectedRoom() throws InterruptedException {
        // when
        viewModel.setTimeFilter(10, 12);
        viewModel.setRoomFilter(MeetingRoom.ROOM_6.ordinal(), false);
        List<MainFragmentViewState> result = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        assertEquals(2, result.size());
        assertEquals(7, result.get(0).getId());
        assertEquals(14, result.get(1).getId());
    }

    @Test
    public void callSetCurrentIdWhenSetDetailId() {
        // when
        viewModel.setDetailId(MAGIC_MEETING_ID);

        // then
        verify(mCurrentIdRepository).setCurrentId(MAGIC_MEETING_ID);
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void callRemoveMeetingByIdOnDeleteButtonClicked() {
        // when
        viewModel.deleteButtonClicked(MAGIC_MEETING_ID);

        // then
        verify(meetingsRepository).removeMeetingById(MAGIC_MEETING_ID);
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
        boolean[] expected = {
                true, true, true,
                false,
                true, true, true, true, true, true};

        // when
        viewModel.setRoomFilter(3, false);
        boolean[] result = LiveDataTestUtils.getOrAwaitValue(viewModel.getRoomFilter());

        // then
        assertArrayEquals(expected, result);
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
        // given
        int hour = 8;
        int minutes = 35;

        // when
        viewModel.setTimeFilter(hour, minutes);

        // then
        assertEquals(hour, viewModel.getTimeFilterHour());
        assertEquals(minutes, viewModel.getTimeFilterMinute());
    }

    @Test
    public void returnNowIfNoTimFilterSet() {
        // when
        viewModel.resetTimeFilter();

        // then
        assertEquals(LocalDateTime.now(clock).getHour(), viewModel.getTimeFilterHour());
        assertEquals(LocalDateTime.now(clock).getMinute(), viewModel.getTimeFilterMinute());
    }

    // List<Meeting> list = new ArrayList<>();
    // LocalDateTime localDateTimeFilter = LocalDateTime.of(2021, 6, 15, 8, 10, 0);
}
