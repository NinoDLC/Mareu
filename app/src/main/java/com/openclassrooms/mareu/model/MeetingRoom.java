package com.openclassrooms.mareu.model;

public class MeetingRoom {

    private final int mId;
    private final String mName;
    private final String mLocation;
    private final String[] mDevices;
    private final int mCapacity;
    private final int mImageSrc;

    public MeetingRoom(int id, String name, String location, String[] devices, int capacity, int imageSrc) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mDevices = devices;
        this.mCapacity = capacity;
        this.mImageSrc = imageSrc;
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

    public int getImageSrc() {
        return mImageSrc;
    }
}
