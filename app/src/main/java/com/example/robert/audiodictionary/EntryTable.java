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
    private String soundLocation;
    // has to be converted to a array of bytes before being stored as a blob

    public String getSoundLocation() {
        return soundLocation;
    }

    public void setSoundLocation(String soundLocation) {
        this.soundLocation = soundLocation;
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
