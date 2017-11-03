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

public class WordActivity extends Activity {

    TextView mWord;
    TextView mDefinition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        mWord = findViewById(R.id.word);
        mDefinition = findViewById(R.id.definition);

        mWord.setText("the word");
        mDefinition.setText("the definition");

    }
}
