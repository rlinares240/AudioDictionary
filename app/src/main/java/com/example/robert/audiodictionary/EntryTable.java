package com.example.robert.audiodictionary;

/**
 * Created by billk on 11/28/2017.
 */

public class EntryTable {

    private String name;
    private String word;
    private String deviceId;
    private String region;
    private String outputFile;
    private byte[] soundConverted;
    // has to be converted to a array of bytes before being stored as a blob

    public byte[] getSoundConverted() {
        return soundConverted;
    }

    public void setSoundConverted(byte[] soundConverted) {
        this.soundConverted = soundConverted;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }



    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
