package com.example.robert.audiodictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    AutoCompleteTextView mEnterWord;
    private Dictionary mDictionary;
    private final String TAG = "MainActivity";
    private String[] recentSearches = new String[] {
            "Belgium", "France", "Italy", "Germany","Germania", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDictionary = new Dictionary(getApplicationContext());
        mEnterWord = findViewById(R.id.AutoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mDictionary.getKeys());
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.AutoCompleteTextView);
        textView.setAdapter(adapter);

        mEnterWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){

                    Intent intent = new Intent(MainActivity.this, WordActivity.class);

                    String word = mEnterWord.getText().toString().toLowerCase();

                    if(mDictionary.containsKey(word)){
                        intent.putExtra("word", word);
                        intent.putExtra("defList", mDictionary.getValue(word));
                        startActivity(intent);
                        return true;
                    } else {
                        Log.i(TAG, "Could not find word");
                        Toast.makeText(getApplicationContext(),"Couldn't find Word",Toast.LENGTH_LONG).show();
                        return false;
                    }
                }

                return false;
            }
        });
        mEnterWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnterWord.setText("");
            }
        });

    }





}
