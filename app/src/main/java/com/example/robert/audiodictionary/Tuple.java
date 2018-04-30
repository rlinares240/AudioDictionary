package com.example.robert.audiodictionary;

import java.io.Serializable;



public class Tuple implements Serializable {
    private String partOfSpeech, definition;


    public Tuple(String pos, String d)
    {
        partOfSpeech = pos;
        definition = d;
    }

    public String getPOS() {
        return partOfSpeech;
    }
    public String getDefinition(){
        return definition;
    }
}
