package com.openclassrooms.mareu.model;

import androidx.annotation.ColorInt;

public class MeetingRoom {

    private final int mId;
    private final String mName;
    private final String mLocation;
    private final String[] mDevices;
    private final int mCapacity;

    @ColorInt
    private final int mTextColor;

    public MeetingRoom(int id, String name, String location, String[] devices, int capacity, int textColor) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mDevices = devices;
        this.mCapacity = capacity;
        this.mTextColor = textColor;
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

    public int getTextColor() {
        return mTextColor;
    }
}
