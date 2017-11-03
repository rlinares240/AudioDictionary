package com.example.robert.audiodictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends Activity {

    AutoCompleteTextView mEnterWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnterWord = findViewById(R.id.AutoCompleteTextView);
        //mEnterWord.setCompletionHint("hint");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.AutoCompleteTextView);
        textView.setAdapter(adapter);

        mEnterWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //Log.i(TAG,v.getText().toString());


                    //String word = mEnterWord.getText().toString();

                    Intent intent = new Intent(MainActivity.this, WordActivity.class);

                    startActivity(intent);
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
        /*
        mEnterWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    mEnterWord.setText("true");
                else
                    mEnterWord.setText("false");

            }
        });
        */

    }
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany","Germania", "Spain"
    };




}
