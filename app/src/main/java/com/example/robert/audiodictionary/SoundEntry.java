package com.example.robert.audiodictionary;

import android.app.Fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWord = (TextView) getActivity().findViewById(R.id.word);
        view = inflater.inflate(R.layout.entryfrag, container, false);
        listView = (ListView) view.findViewById(R.id.entries);
        mDatabase = new DatabaseHelper(getActivity());
        cursor = mDatabase.checkWordEntries(mWord.getText().toString());
        for(EntryTable a : cursor) {
            Log.d("entry", a.getWord().toString());
        }
        mAdapter = new EntryAdapter(this.getActivity(),cursor);
        listView.setAdapter(mAdapter);
        Log.d("tbl", cursor.size() + "");


        return view;
    }








}
