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

    public static final MainFragmentViewState[] VIEW_STATE_LIST = {
            new MainFragmentViewState(1, "08h30 - Daily meetup", "marc@lamzone.fr", "+1", "Caniche", 2131034208),
            new MainFragmentViewState(9, "08h40 - Global warming", "marc@nerdzherdz.org", "+0", "Basset", 2131034212),
            new MainFragmentViewState(12, "08h50 - Code red", "fred@lamzone.com", "+2", "Cocker", 2131034213),
            new MainFragmentViewState(20, "09h20 - Daily meetup", "marc@lamzone.fr", "+3", "Fox-Terrier", 2131034210),
            new MainFragmentViewState(5, "09h40 - Daily meetup", "jack@buymore.net", "+2", "Caniche", 2131034208),
            new MainFragmentViewState(7, "10h00 - Agile sprint", "fred@nerdzherdz.org", "+1", "Dogue-Allemand", 2131034207),
            new MainFragmentViewState(8, "10h10 - Global warming", "morgan@nerdzherdz.com", "+2", "Labrador", 2131034211),
            new MainFragmentViewState(14, "10h10 - Coffee break", "claire@nerdzherdz.fr", "+1", "Epagnol", 2131034205),
            new MainFragmentViewState(4, "11h10 - Project xXx", "fred@lamzone.net", "+2", "Doberman", 2131034206),
            new MainFragmentViewState(6, "11h50 - Daily meetup", "morgan@lamzone.fr", "+1", "Cocker", 2131034213),
            new MainFragmentViewState(17, "12h20 - Code red", "henry@lamzone.net", "+1", "Epagnol", 2131034205),
            new MainFragmentViewState(10, "13h15 - Agile sprint", "jack@lamzone.net", "+1", "Dogue-Allemand", 2131034207),
            new MainFragmentViewState(11, "13h25 - Agile sprint", "chuck@lamzone.fr", "+1", "Doberman", 2131034206),
            new MainFragmentViewState(13, "14h40 - Code red", "tedy@lamzone.com", "+0", "Basset", 2131034212),
            new MainFragmentViewState(18, "16h05 - Daily meetup", "joe@nerdzherdz.fr", "+0", "Cocker", 2131034213),
            new MainFragmentViewState(19, "16h10 - Daily meetup", "fred@lamzone.fr", "+0", "Braque-de-Weimar", 2131034204),
            new MainFragmentViewState(2, "16h15 - Project xXx", "tedy@buymore.fr", "+3", "Doberman", 2131034206),
            new MainFragmentViewState(3, "17h15 - Project xXx", "jack@lamzone.org", "+2", "Labrador", 2131034211),
            new MainFragmentViewState(15, "17h45 - Coffee break", "marc@buymore.com", "+1", "Fox-Terrier", 2131034210),
            new MainFragmentViewState(16, "17h50 - Coffee break", "jasmine@buymore.org", "+0", "Epagnol", 2131034205),
    };

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

        // and should be sorted
        assertArrayEquals(VIEW_STATE_LIST, result.toArray());
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
}
