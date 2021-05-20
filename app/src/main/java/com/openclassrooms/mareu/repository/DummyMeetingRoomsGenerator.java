package com.openclassrooms.mareu.repository;

import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class DummyMeetingRoomsGenerator {


    public static List<MeetingRoom> DUMMY_MEETING_ROOMS = Arrays.asList(
            new MeetingRoom(1, "Braque-de-Weimar", "1st floor, 1st door on the left", "Beamer", 3),
            new MeetingRoom(2, "Doberman", "1st floor, 2nd door on the left", "None", 4),
            new MeetingRoom(3, "Dogue-Allemand", "1st floor, 1st door on the right", "WhiteBoard", 5),
            new MeetingRoom(4, "Caniche", "1st floor, 2nd door on the right", "BlackBoard", 2),
            new MeetingRoom(5, "Jack-Russel", "2nd floor, 1st door on the left", "None", 4),
            new MeetingRoom(6, "Fox-Terrier", "2nd floor, 2nd door on the left", "Hifi-system", 5),
            new MeetingRoom(7, "Labrador", "2nd floor, 1st door on the right", "Beamer", 2),
            new MeetingRoom(8, "Basset", "2rd floor, 2nd door on the right", "Beamer", 4),
            new MeetingRoom(9, "Cocker", "3rd floor, 1st door on the left", "Beamer", 2),
            new MeetingRoom(10, "Epagnol", "3rd floor, 2nd door on the left", "Beamer", 6)
            );

}
