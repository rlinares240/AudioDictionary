package com.example.robert.audiodictionary;


import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;



public class ReviewSound extends Fragment{
    DatabaseHelper mDatabase;
    private Button mSubmit, mThumbsup1, mThumbsdown1, mThumbsup2, mThumbsdown2;
    private TextView mName, mRegion, mUserName, mUserRegion, mAccuracy, mClarity, mWord;
    private boolean mThumbsup1flag, mThumbsdown1flag, mThumbsup2flag, mThumbsdown2flag;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String outputFile;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onCreate(savedInstanceState);

        mDatabase = new DatabaseHelper(getActivity());
        return inflater.inflate(R.layout.reviewsound, container, false);



    }


    public void onStart() {
        super.onStart();

        Bundle bundle = this.getArguments();
        mSubmit =(Button) getView().findViewById(R.id.sb);
        mThumbsup1 =(Button) getView().findViewById(R.id.thumbsup_button1);
        mThumbsdown1 = (Button) getView().findViewById(R.id.thumbsdown_button1);
        mThumbsup2=(Button) getView().findViewById(R.id.thumbsup_button2);
        mThumbsdown2=(Button) getView().findViewById(R.id.thumbsdown_button2);
        mName = (TextView) getView().findViewById(R.id.NameLabel);
        mRegion = (TextView) getView().findViewById(R.id.RegionLabel);
        mUserName =(TextView) getActivity().findViewById(R.id.UserName);// need change here
        mUserRegion = (TextView) getActivity().findViewById(R.id.UserRegion); //need change here
        mAccuracy = (TextView) getView().findViewById(R.id.Accuracy_review);
        mClarity =  (TextView) getView().findViewById(R.id.Clarity_review);
        mWord = (TextView) getActivity().findViewById(R.id.word);

        mUserName.setText(bundle.getString("name"));
        mRegion.setText(bundle.getString("location"));
        mSubmit.setEnabled(true);
        mThumbsup1.setEnabled(true);
        mThumbsdown1.setEnabled(true);
        mThumbsup2.setEnabled(true);
        mThumbsdown2.setEnabled(true);

        mThumbsup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThumbsup1flag=true;
                mThumbsdown1.setEnabled(false);
            }
        });


        mThumbsdown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThumbsdown1flag=true;
                mThumbsup1.setEnabled(false);
            }
        });

        mThumbsup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThumbsup2flag=true;
                mThumbsdown2.setEnabled(false);
            }
        });

        mThumbsdown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThumbsdown2flag=true;
                mThumbsup2.setEnabled(false);
            }
        });




        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean entryExists = false;

                entryExists = mDatabase.checkIfExists( mWord.getText().toString(),
                        mUserName.getText().toString());


                if (entryExists == true) {
                    ArrayList<EntryTable> result = mDatabase.searchSoundEntries(mWord.getText().toString(), mUserName.getText().toString());
                    EntryTable contact = result.get(0);
                    if (mThumbsup1flag && mThumbsup2flag) {
                        contact.setAccuracyGoodNum(contact.getAccuracyGoodNum() + 1);
                        contact.setClarityGoodNum(contact.getClarityGoodNum() + 1);
                    } else if (mThumbsup1flag && mThumbsdown2flag) {
                        contact.setAccuracyGoodNum(contact.getAccuracyGoodNum() + 1);
                        contact.setClarityBadNum(contact.getClarityBadNum() + 1);
                    } else if (mThumbsdown1flag && mThumbsup2flag) {
                        contact.setAccuracyBadNum(contact.getAccuracyBadNum() + 1);
                        contact.setClarityGoodNum(contact.getClarityGoodNum() + 1);
                    } else if (mThumbsdown1flag && mThumbsdown2flag) {
                        contact.setAccuracyBadNum(contact.getAccuracyBadNum() + 1);
                        contact.setClarityBadNum(contact.getClarityBadNum() + 1);
                    } else {

                    }
                    int changed = mDatabase.updateEntry(contact);
                    System.out.println("ROWS UPDATED: " + changed);

                }

                getActivity().getFragmentManager().popBackStack();

            }




        });
    }

}


