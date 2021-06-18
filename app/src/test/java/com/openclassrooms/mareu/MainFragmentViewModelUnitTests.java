package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MainFragmentViewModelUnitTests {

    private MainFragmentViewModel viewModel;
    private static final int EXPECTED_HOUR = 8;
    private static final int EXPECTED_MINUTE = 41;

    private static final int SOONER_MEETING_ID = 10;
    private static final int LATER_MEETING_ID = 3;

    private static final int MEETING_1_ROOM_ORDINAL = 7;
    private static final int MEETING_2_ROOM_ORDINAL = 3;

    private static final MutableLiveData<List<Meeting>> EXPECTED_MEETINGS_LIVEDATA = new MutableLiveData<>();

    private static final List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(
                    LATER_MEETING_ID,
                    "tedy@buymore.fr",
                    new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")),
                    "Project xXx",
                    LocalDateTime.of(2021, 6, 14, 16, 15, 0),
                    LocalDateTime.of(2021, 6, 14, 16, 40, 0),
                    MeetingRoom.values()[MEETING_1_ROOM_ORDINAL]
            ),
            new Meeting(
                    SOONER_MEETING_ID,
                    "marc@lamzone.fr",
                    new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 8, 30, 0),
                    LocalDateTime.of(2021, 6, 14, 9, 35, 0),
                    MeetingRoom.values()[MEETING_2_ROOM_ORDINAL]
            )

    // todo vérifier les résultats du filtrage...

        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "jack@lamzone.org", new HashSet<>(Arrays.asList("hans@buymore.fr", "fred@nerdzherdz.com")), "Project xXx", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 15, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 5, 0), MeetingRoom.values()[6]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "fred@lamzone.net", new HashSet<>(Arrays.asList("morgan@buymore.com", "fred@buymore.fr")), "Project xXx", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 10, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "jack@buymore.net", new HashSet<>(Arrays.asList("tedy@nerdzherdz.com", "marc@buymore.com")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 40, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 40, 0), MeetingRoom.values()[3]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "morgan@lamzone.fr", new HashSet<>(Collections.singletonList("franck@buymore.net")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 50, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 10, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "fred@nerdzherdz.org", new HashSet<>(Collections.singletonList("joe@buymore.fr")), "Agile sprint", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 0, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 11, 25, 0), MeetingRoom.values()[2]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "morgan@nerdzherdz.com", new HashSet<>(Arrays.asList("fred@nerdzherdz.fr", "chuck@nerdzherdz.fr")), "Global warming", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 25, 0), MeetingRoom.values()[6]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "marc@nerdzherdz.org", new HashSet<>(), "Global warming", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 40, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 45, 0), MeetingRoom.values()[7]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "jack@lamzone.net", new HashSet<>(Collections.singletonList("henry@lamzone.com")), "Agile sprint", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 13, 15, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 13, 30, 0), MeetingRoom.values()[2]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "chuck@lamzone.fr", new HashSet<>(Collections.singletonList("joe@lamzone.org")), "Agile sprint", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 13, 25, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 40, 0), MeetingRoom.values()[1]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "fred@lamzone.com", new HashSet<>(Arrays.asList("marc@buymore.net", "henry@buymore.com")), "Code red", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 50, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 45, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "tedy@lamzone.com", new HashSet<>(), "Code red", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 40, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 15, 25, 0), MeetingRoom.values()[7]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "claire@nerdzherdz.fr", new HashSet<>(Collections.singletonList("jasmine@nerdzherdz.fr")), "Coffee break", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 45, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "marc@buymore.com", new HashSet<>(Collections.singletonList("hans@buymore.org")), "Coffee break", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 45, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 55, 0), MeetingRoom.values()[5]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "jasmine@buymore.org", new HashSet<>(), "Coffee break", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 50, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 30, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "henry@lamzone.net", new HashSet<>(Collections.singletonList("fred@lamzone.net")), "Code red", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 20, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 40, 0), MeetingRoom.values()[9]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "joe@nerdzherdz.fr", new HashSet<>(), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 5, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 45, 0), MeetingRoom.values()[8]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "fred@lamzone.fr", new HashSet<>(), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 10, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 17, 15, 0), MeetingRoom.values()[0]));
        repo.createMeeting(new Meeting(repo.getNextMeetingId(), "marc@lamzone.fr", new HashSet<>(Arrays.asList("hans@nerdzherdz.com", "marc@lamzone.com", "henry@lamzone.com")), "Daily meetup", LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 20, 0), LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 20, 0), MeetingRoom.values()[5]));



    );

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private CurrentIdRepository mCurrentIdRepository;

    @Mock
    private MeetingsRepository meetingsRepository;

    @Before
    public void setUp() {
        given(meetingsRepository.getMeetings()).willReturn(EXPECTED_MEETINGS_LIVEDATA);
        EXPECTED_MEETINGS_LIVEDATA.setValue(MEETING_LIST);
        viewModel = new MainFragmentViewModel(meetingsRepository, mCurrentIdRepository);
    }

    @Test
    public void nominalCase() throws InterruptedException {
        // when
        LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verify(meetingsRepository).getMeetings();
        //todo Nino : les verifyNoMoreInterraction sont de l'hygiène :
        // on pourrait les mettre en @After ? Mais ça obligerait des verify() avant...?
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void withTimeFilterSet() throws InterruptedException {
        // when
        viewModel.setTimeFilter(EXPECTED_HOUR, EXPECTED_MINUTE);
        LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void withARoomUnselected() throws InterruptedException {
        // when
        viewModel.setRoomFilter(MEETING_2_ROOM_ORDINAL, false);
        LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void withTimeFilterSetAndARoomUnselected() throws InterruptedException {
        // when
        viewModel.setTimeFilter(EXPECTED_HOUR, EXPECTED_MINUTE);
        viewModel.setRoomFilter(MEETING_2_ROOM_ORDINAL, false);
        LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }


    @Test
    public void withTimeFilterSetLateAndARoomUnselected() throws InterruptedException {
        // when
        viewModel.setTimeFilter(19, EXPECTED_MINUTE);
        viewModel.setRoomFilter(MEETING_2_ROOM_ORDINAL, false);
        LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void withTimeFilterSetEarlyAndARoomUnselected() throws InterruptedException {
        // when
        viewModel.setTimeFilter(6, EXPECTED_MINUTE);
        viewModel.setRoomFilter(MEETING_2_ROOM_ORDINAL, false);
        LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void callSetCurrentIdWhenSetDetailId() {
        // when
        viewModel.setDetailId(SOONER_MEETING_ID);

        // then
        verify(mCurrentIdRepository).setCurrentId(SOONER_MEETING_ID);
        verify(meetingsRepository).getMeetings();
        verifyNoMoreInteractions(meetingsRepository);
        verifyNoMoreInteractions(mCurrentIdRepository);
    }

    @Test
    public void callRemoveMeetingByIdOnDeleteButtonClicked() {
        // when
        viewModel.deleteButtonClicked(SOONER_MEETING_ID);

        // then
        verify(meetingsRepository).removeMeetingById(SOONER_MEETING_ID);
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
        viewModel.setRoomFilter(MEETING_2_ROOM_ORDINAL, false);
        boolean[] result = LiveDataTestUtils.getOrAwaitValue(viewModel.getRoomFilter());

        // then
        assertArrayEquals(expected, result);
    }

    @Test
    public void setRoomFilterWithNonInitializedRoomFilter() {
        // todo: Nino how to test it ?
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

    @Test
    public void sortedList() throws InterruptedException {
        // when
        List<MainFragmentViewState> result = LiveDataTestUtils.getOrAwaitValue(viewModel.getViewStateListLiveData());

        // then
        assertEquals(SOONER_MEETING_ID, result.get(0).getId());
        assertEquals(LATER_MEETING_ID, result.get(1).getId());
    }
}
