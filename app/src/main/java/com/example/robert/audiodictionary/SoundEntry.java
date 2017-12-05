package com.example.robert.audiodictionary;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by billk on 11/17/2017.
 */

public class SoundEntry extends Fragment {
    ListView listView;
    EntryAdapter mAdapter;
    DatabaseHelper mDatabase;
    View view;
    TextView mWord;
    ArrayList<EntryTable> cursor;
    //Button mPlayButton;
    String soundLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWord = (TextView) getActivity().findViewById(R.id.word);
        view = inflater.inflate(R.layout.entryfrag, container, false);
        listView = (ListView) view.findViewById(R.id.entries);
        //mPlayButton = (Button) view.findViewById(R.id.play_button1);
        mDatabase = new DatabaseHelper(getActivity());
        cursor = mDatabase.checkWordEntries(mWord.getText().toString());
        for(EntryTable a : cursor) {
            Log.d("entry", a.getWord().toString());
        }
        mAdapter = new EntryAdapter(this.getActivity(),cursor);
        listView.setAdapter(mAdapter);
        Log.d("SHOWS", "entry added");
        Log.d("tbl", cursor.size() + "");


        return view;
    }








}
