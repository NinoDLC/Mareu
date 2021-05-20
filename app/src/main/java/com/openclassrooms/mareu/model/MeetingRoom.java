package com.openclassrooms.mareu.model;

public class MeetingRoom {

    private final int mId;
    private final String mName;
    private final String mLocation;
    private final String[] mDevices;
    private final int mCapacity;

    public MeetingRoom(int id, String name, String location, String[] devices, int capacity) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mDevices = devices;
        this.mCapacity = capacity;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String[] getDevices() {
        return mDevices;
    }

    public int getCapacity() {
        return mCapacity;
    }

}
