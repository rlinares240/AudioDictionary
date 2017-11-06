package com.example.robert.audiodictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class WordActivity extends Activity {

    TextView mWord;
    TextView mDefinition;
    //DictionaryEntry mEntry;
    String word;
    ArrayList<Tuple> defList;
    private final static String TAG = "WordActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        mWord = findViewById(R.id.word);
        mDefinition = findViewById(R.id.definition);

        Intent intent = getIntent();
        try {
           word = intent.getStringExtra("word");
           defList = (ArrayList<Tuple>)intent.getSerializableExtra("defList");
        }catch(Exception e) {
            Log.i(TAG,"Error getting extra");
        }

        //if(mEntry != null) {
            mWord.setText(word);
            String text = "";
            for(Tuple t: defList){
                text += t.getPOS() + " - " + t.getDefinition() + "\n";
            }

           mDefinition.setText(text );
       // }
    }
}
