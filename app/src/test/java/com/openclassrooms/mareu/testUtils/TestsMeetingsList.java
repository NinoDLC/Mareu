package com.openclassrooms.mareu.testUtils;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class TestsMeetingsList {

    public static final List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(
                    1,
                    "marc@lamzone.fr",
                    new HashSet<>(Collections.singletonList("claire@nerdzherdz.org")),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 8, 30, 0),
                    LocalDateTime.of(2021, 6, 14, 9, 35, 0),
                    MeetingRoom.ROOM_3
            ),
            new Meeting(
                    2,
                    "tedy@buymore.fr",
                    new HashSet<>(Arrays.asList("claire@nerdzherdz.com", "jack@lamzone.fr", "jack@lamzone.net")),
                    "Project xXx",
                    LocalDateTime.of(2021, 6, 14, 16, 15, 0),
                    LocalDateTime.of(2021, 6, 14, 16, 40, 0),
                    MeetingRoom.ROOM_1
            ),
            new Meeting(
                    3,
                    "jack@lamzone.org",
                    new HashSet<>(Arrays.asList("hans@buymore.fr", "fred@nerdzherdz.com")),
                    "Project xXx",
                    LocalDateTime.of(2021, 6, 14, 17, 15, 0),
                    LocalDateTime.of(2021, 6, 14, 18, 5, 0),
                    MeetingRoom.ROOM_6
            ),
            new Meeting(
                    4,
                    "fred@lamzone.net",
                    new HashSet<>(Arrays.asList("morgan@buymore.com", "fred@buymore.fr")),
                    "Project xXx",
                    LocalDateTime.of(2021, 6, 14, 11, 10, 0),
                    LocalDateTime.of(2021, 6, 14, 11, 10, 0),
                    MeetingRoom.ROOM_1
            ),
            new Meeting(
                    5,
                    "jack@buymore.net",
                    new HashSet<>(Arrays.asList("tedy@nerdzherdz.com", "marc@buymore.com")),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 9, 40, 0),
                    LocalDateTime.of(2021, 6, 14, 9, 40, 0),
                    MeetingRoom.ROOM_3
            ),
            new Meeting(
                    6,
                    "morgan@lamzone.fr",
                    new HashSet<>(Collections.singletonList("franck@buymore.net")),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 11, 50, 0),
                    LocalDateTime.of(2021, 6, 14, 12, 10, 0),
                    MeetingRoom.ROOM_8
            ),
            new Meeting(
                    7,
                    "fred@nerdzherdz.org",
                    new HashSet<>(Collections.singletonList("joe@buymore.fr")),
                    "Agile sprint",
                    LocalDateTime.of(2021, 6, 14, 10, 0, 0),
                    LocalDateTime.of(2021, 6, 14, 11, 25, 0),
                    MeetingRoom.ROOM_2
            ),
            new Meeting(
                    8,
                    "morgan@nerdzherdz.com",
                    new HashSet<>(Arrays.asList("fred@nerdzherdz.fr", "chuck@nerdzherdz.fr")),
                    "Global warming",
                    LocalDateTime.of(2021, 6, 14, 10, 10, 0),
                    LocalDateTime.of(2021, 6, 14, 10, 25, 0),
                    MeetingRoom.ROOM_6
            ),
            new Meeting(
                    9,
                    "marc@nerdzherdz.org",
                    new HashSet<>(),
                    "Global warming",
                    LocalDateTime.of(2021, 6, 14, 8, 40, 0),
                    LocalDateTime.of(2021, 6, 14, 8, 45, 0),
                    MeetingRoom.ROOM_7
            ),
            new Meeting(
                    10,
                    "jack@lamzone.net",
                    new HashSet<>(Collections.singletonList("henry@lamzone.com")),
                    "Agile sprint",
                    LocalDateTime.of(2021, 6, 14, 13, 15, 0),
                    LocalDateTime.of(2021, 6, 14, 13, 30, 0),
                    MeetingRoom.ROOM_2
            ),
            new Meeting(
                    11,
                    "chuck@lamzone.fr",
                    new HashSet<>(Collections.singletonList("joe@lamzone.org")),
                    "Agile sprint",
                    LocalDateTime.of(2021, 6, 14, 13, 25, 0),
                    LocalDateTime.of(2021, 6, 14, 14, 40, 0),
                    MeetingRoom.ROOM_1
            ),
            new Meeting(
                    12,
                    "fred@lamzone.com",
                    new HashSet<>(Arrays.asList("marc@buymore.net", "henry@buymore.com")),
                    "Code red",
                    LocalDateTime.of(2021, 6, 14, 8, 50, 0),
                    LocalDateTime.of(2021, 6, 14, 9, 45, 0),
                    MeetingRoom.ROOM_8
            ),
            new Meeting(
                    13,
                    "tedy@lamzone.com",
                    new HashSet<>(),
                    "Code red",
                    LocalDateTime.of(2021, 6, 14, 14, 40, 0),
                    LocalDateTime.of(2021, 6, 14, 15, 25, 0),
                    MeetingRoom.ROOM_7
            ),
            new Meeting(
                    14,
                    "claire@nerdzherdz.fr",
                    new HashSet<>(Collections.singletonList("jasmine@nerdzherdz.fr")),
                    "Coffee break",
                    LocalDateTime.of(2021, 6, 14, 10, 10, 0),
                    LocalDateTime.of(2021, 6, 14, 10, 45, 0),
                    MeetingRoom.ROOM_9
            ),
            new Meeting(
                    15,
                    "marc@buymore.com",
                    new HashSet<>(Collections.singletonList("hans@buymore.org")),
                    "Coffee break",
                    LocalDateTime.of(2021, 6, 14, 17, 45, 0),
                    LocalDateTime.of(2021, 6, 14, 17, 55, 0),
                    MeetingRoom.ROOM_5
            ),
            new Meeting(
                    16,
                    "jasmine@buymore.org",
                    new HashSet<>(),
                    "Coffee break",
                    LocalDateTime.of(2021, 6, 14, 17, 50, 0),
                    LocalDateTime.of(2021, 6, 14, 18, 30, 0),
                    MeetingRoom.ROOM_9
            ),
            new Meeting(
                    17,
                    "henry@lamzone.net",
                    new HashSet<>(Collections.singletonList("fred@lamzone.net")),
                    "Code red",
                    LocalDateTime.of(2021, 6, 14, 12, 20, 0),
                    LocalDateTime.of(2021, 6, 14, 12, 40, 0),
                    MeetingRoom.ROOM_9
            ),
            new Meeting(
                    18,
                    "joe@nerdzherdz.fr",
                    new HashSet<>(),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 16, 5, 0),
                    LocalDateTime.of(2021, 6, 14, 16, 45, 0),
                    MeetingRoom.ROOM_8
            ),
            new Meeting(
                    19,
                    "fred@lamzone.fr",
                    new HashSet<>(),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 16, 10, 0),
                    LocalDateTime.of(2021, 6, 14, 17, 15, 0),
                    MeetingRoom.ROOM_0
            ),
            new Meeting(
                    20,
                    "marc@lamzone.fr",
                    new HashSet<>(Arrays.asList("hans@nerdzherdz.com", "marc@lamzone.com", "henry@lamzone.com")),
                    "Daily meetup",
                    LocalDateTime.of(2021, 6, 14, 9, 20, 0),
                    LocalDateTime.of(2021, 6, 14, 9, 20, 0),
                    MeetingRoom.ROOM_5
            )
    );

}
