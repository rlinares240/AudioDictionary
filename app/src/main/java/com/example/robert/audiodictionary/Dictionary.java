package com.example.robert.audiodictionary;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * Created by Robert on 11/5/2017.
 */

public class Dictionary {


    //private final String path ="dictionary_small.csv";
    private final String path = "dictionary.csv";
    private HashMap<String,ArrayList<Tuple>> wordMap = new HashMap<>();
    private String pattern = "^\\\"([A-Za-z- '\\.]*)\\\",\\\"([A-Za-z\\. \\&]*)\\\",\\\"(.*)\\\"";

    public Dictionary(Context context) {
        AssetManager am = context.getAssets();

        Pattern r = Pattern.compile(pattern);


        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher m = r.matcher(line);
                String word, pos, definition;

                if(m.find()) {
                    word = m.group(1).toLowerCase();
                    pos = m.group(2);
                    definition = m.group(3);

                    Tuple t = new Tuple(pos, definition);
                    ArrayList currentDefinitions = wordMap.get(word);
                    if(currentDefinitions == null) {
                        ArrayList defList = new ArrayList();
                        defList.add(t);
                        wordMap.put(word, defList);
                    } else {
                        currentDefinitions.add(t);
                        wordMap.replace(word, currentDefinitions);
                    }

                } else
                    Log.i(TAG,"no matches " + line);
            }
        } catch (IOException e) {
            Log.i(TAG, "Error reading from file");
            e.printStackTrace();
        }
    }

    public ArrayList<Tuple> getValue(String key) {
        return wordMap.get(key);
    }
    public boolean containsKey(String key) {
        return wordMap.containsKey(key);
    }
    public ArrayList<String> getKeys() {
        return new ArrayList<String>(wordMap.keySet());
    }




}
