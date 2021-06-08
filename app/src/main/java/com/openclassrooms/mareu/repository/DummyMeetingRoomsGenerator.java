package com.openclassrooms.mareu.repository;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public abstract class DummyMeetingRoomsGenerator {

    private static final String[] DEVICES = {"Beamer", "Whiteboard", "Blackboard", "Hi-fi", "Augmented reality"};

    private static final Random RANDOM = new Random();

    private static String[] generateDevices() {
        int participantsNumber = RANDOM.nextInt(2);
        HashSet<String> devices = new HashSet<>();
        while (devices.size() < participantsNumber) {
            devices.add(DEVICES[RANDOM.nextInt(DEVICES.length)]);
        }
        return devices.toArray(new String[0]);
    }

    public static HashMap<Integer, MeetingRoom> getDummyMeetingRooms() {
        // TODO use TreeSet to garantee unicity and order
        HashMap<Integer, MeetingRoom> dummy_meeting_rooms = new HashMap<>();
        dummy_meeting_rooms.put(1, new MeetingRoom(1, "Braque-de-Weimar", "1st floor, 1st door on the left", generateDevices(), 3, R.color.dog_1));
        dummy_meeting_rooms.put(2, new MeetingRoom(2, "Doberman", "1st floor, 2nd door on the left", generateDevices(), 4, R.color.dog_2));
        dummy_meeting_rooms.put(3, new MeetingRoom(3, "Dogue-Allemand", "1st floor, 1st door on the right", generateDevices(), 5, R.color.dog_3));
        dummy_meeting_rooms.put(4, new MeetingRoom(4, "Caniche", "1st floor, 2nd door on the right", generateDevices(), 2, R.color.dog_4));
        dummy_meeting_rooms.put(5, new MeetingRoom(5, "Jack-Russel", "2nd floor, 1st door on the left", generateDevices(), 4, R.color.dog_5));
        dummy_meeting_rooms.put(6, new MeetingRoom(6, "Fox-Terrier", "2nd floor, 2nd door on the left", generateDevices(), 5, R.color.dog_6));
        dummy_meeting_rooms.put(7, new MeetingRoom(7, "Labrador", "2nd floor, 1st door on the right", generateDevices(), 2, R.color.dog_7));
        dummy_meeting_rooms.put(8, new MeetingRoom(8, "Basset", "2rd floor, 2nd door on the right", generateDevices(), 4, R.color.dog_8));
        dummy_meeting_rooms.put(9, new MeetingRoom(9, "Cocker", "3rd floor, 1st door on the left", generateDevices(), 2, R.color.dog_9));
        dummy_meeting_rooms.put(10, new MeetingRoom(10, "Epagnol", "3rd floor, 2nd door on the left", generateDevices(), 6, R.color.dog_10));

        return dummy_meeting_rooms;
    }
}
