package com.example.robert.audiodictionary;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class WordActivity extends Activity implements TextToSpeech.OnInitListener {

    DatabaseHelper mDatabase;
    EditText mName;
    TextToSpeech mSpeech;
    ImageView mAdd;
    TextView mWord;
    Button mPlayAudio;
    TextView mDefinition;
    String word;
    Button mSubmit;
    private int CHECK_TTS  = 0;
    ArrayList<Tuple> defList;
    private final static String TAG = "WordActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        mWord = findViewById(R.id.word);
        mDefinition = findViewById(R.id.definition);
        mAdd = findViewById(R.id.imageButton);
        mPlayAudio = findViewById(R.id.button);
        mSubmit = findViewById(R.id.submit_button);
        mName = findViewById(R.id.submission_name);
        mDatabase = new DatabaseHelper(this);


        final Intent intent = getIntent();
        try {
           word = intent.getStringExtra("word");
           defList = (ArrayList<Tuple>)intent.getSerializableExtra("defList");
        }catch(Exception e) {
            Log.i(TAG,"Error getting extra");
        }

        mWord.setText(word);
        String text = "";
        for(Tuple t: defList){
            text += t.getPOS() + " - " + t.getDefinition() + "\n";
        }

        mDefinition.setText(text);

        Intent ttsIntent = new Intent();
        ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(ttsIntent, CHECK_TTS);

        if(findViewById(R.id.action_container) != null) {
            SoundEntry fragment = new SoundEntry();
            changeFragment(fragment);
        }


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.action_container) != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.action_container, new SubmitForm());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        mPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word_entry = mWord.getText().toString();
                speakWords(word_entry);
            }
        });


    }
    @Override
    public void onDestroy() {
        mSpeech.shutdown();
        super.onDestroy();

    }
    @Override
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHECK_TTS){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                mSpeech = new TextToSpeech(this,this);
;            }
        }


    }

    public void onInit(int initStatus) {

        if (initStatus == TextToSpeech.SUCCESS) {
            if(mSpeech.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                mSpeech.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Speech failed", Toast.LENGTH_LONG).show();
        }
    }
    public void speakWords(String word){

        mSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void changeFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.action_container,fragment);
        fragmentTransaction.commit();



    }




}
