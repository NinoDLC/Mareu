package com.openclassrooms.mareu.repository;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public abstract class DummyMeetingRoomsGenerator {

    private static final String[] mDevices = {"Beamer", "Whiteboard", "Blackboard", "Hi-fi", "augmented reality"};

    private static final Random mRand = new Random();

    private static final int imageSrc = R.drawable.ic_launcher_foreground;

    private static final List<MeetingRoom> DUMMY_MEETING_ROOMS = Arrays.asList(
            new MeetingRoom(1, "Braque-de-Weimar", "1st floor, 1st door on the left", generateDevices(), 3, imageSrc),
            new MeetingRoom(2, "Doberman", "1st floor, 2nd door on the left", generateDevices(), 4, imageSrc),
            new MeetingRoom(3, "Dogue-Allemand", "1st floor, 1st door on the right", generateDevices(), 5, imageSrc),
            new MeetingRoom(4, "Caniche", "1st floor, 2nd door on the right", generateDevices(), 2, imageSrc),
            new MeetingRoom(5, "Jack-Russel", "2nd floor, 1st door on the left", generateDevices(), 4, imageSrc),
            new MeetingRoom(6, "Fox-Terrier", "2nd floor, 2nd door on the left", generateDevices(), 5, imageSrc),
            new MeetingRoom(7, "Labrador", "2nd floor, 1st door on the right", generateDevices(), 2, imageSrc),
            new MeetingRoom(8, "Basset", "2rd floor, 2nd door on the right", generateDevices(), 4, imageSrc),
            new MeetingRoom(9, "Cocker", "3rd floor, 1st door on the left", generateDevices(), 2, imageSrc),
            new MeetingRoom(10, "Epagnol", "3rd floor, 2nd door on the left", generateDevices(), 6, imageSrc)
    );

    private static String[] generateDevices() {
        int participantsNumber = mRand.nextInt(2);
        HashSet<String> devices = new HashSet<>();
        while (devices.size() < participantsNumber) {
            devices.add(mDevices[mRand.nextInt(mDevices.length)]);
        }
        return devices.toArray(new String[0]);
    }

    public static List<MeetingRoom> getDummyMeetingRooms() {
        return DUMMY_MEETING_ROOMS;
    }
}
