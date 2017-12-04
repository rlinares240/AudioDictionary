package com.example.robert.audiodictionary;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 11/19/17.
 */

public class SubmitForm extends Fragment {
    DatabaseHelper mDatabase;
    private Button  mSubmit, mPlay;
    private MediaRecorder mAudioRecorder;
    private Button mRecord,mStop;
    private String outputFile;
    private EditText mName;
    private EditText mRegion;
    private TextView mWord;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        mDatabase = new DatabaseHelper(getActivity());

        ActivityCompat.requestPermissions(this.getActivity(),permissions,REQUEST_RECORD_AUDIO_PERMISSION);
        return inflater.inflate(R.layout.form, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        mRecord = (Button) getView().findViewById(R.id.record_button);
        mPlay = (Button) getView().findViewById(R.id.play_button);
        mSubmit =(Button) getView().findViewById(R.id.submit_button);
        mStop = (Button) getView().findViewById(R.id.stop_button);
        mName = (EditText) getView().findViewById(R.id.submission_name);
        mWord = (TextView) getActivity().findViewById(R.id.word);
        mRegion = (EditText)getActivity().findViewById(R.id.region);
      outputFile= getActivity().getExternalCacheDir().getAbsolutePath() + "/recording.3gp";

        mSubmit.setEnabled(false);
        mPlay.setEnabled(false);
        mStop.setEnabled(false);



//
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAudioRecorder= new MediaRecorder();
                mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                mAudioRecorder.setOutputFile(outputFile);
                //TODO need to check if deviceId already exists in DB, for the word entry. if yes then confirm override
                try{
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();
                }catch(IllegalStateException e){

                }catch(IOException i){

                }
                mRecord.setEnabled(false);
                mStop.setEnabled(true);
                Toast.makeText(getContext(),"Recording in Progress",Toast.LENGTH_LONG).show();
            }
        });


        //TODO FIX THIS
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAudioRecorder.stop();
                mAudioRecorder.release();

                mStop.setEnabled(false);
                mPlay.setEnabled(true);
                mSubmit.setEnabled(true);
                mRecord.setEnabled(true);
                Toast.makeText(getContext(),"Recording Complete",Toast.LENGTH_LONG).show();
                // save to a local file then when user clicks submit it will display the dialog

            }
        });
//
//
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                try{
                    player.setDataSource(outputFile);
                    player.prepare();
                    player.start();
                    Toast.makeText(getContext(),"Playing Audio",Toast.LENGTH_SHORT).show();
                }catch(Exception e){

                }



            }
        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //have to add conditional to check if fields are non empty
                EntryTable contact = new EntryTable();
                contact.setWord(mWord.getText().toString());
                contact.setName(mName.getText().toString());
                //System.out.println(getDeviceID());
                //contact.setDeviceId(getDeviceID());
                contact.setDeviceId("12345");
                contact.setRegion(mRegion.getText().toString());


                byte[] soundConverted = new byte[0];


                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(Uri.fromFile(new File(outputFile)));
                    soundConverted = new byte[inputStream.available()];
                    soundConverted = toByteArray(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                contact.setSoundConverted(soundConverted);

                long res = mDatabase.insertEntry(contact);
                if (res == 1) {
                    getActivity().getFragmentManager().popBackStack();
                    ArrayList<EntryTable> entries = mDatabase.getAllRecords();
                    for (EntryTable a : entries) {
                        Log.d("Name", a.getName());
                    }
                }

            }
        });


    }

    public byte[] toByteArray(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int read = 0;
        byte[] buffer = new byte[1024];
        while(read != -1){
            read = in.read(buffer);
            if(read!=-1)
                out.write(buffer,0,read);
        }
        out.close();
        return out.toByteArray();

    }


    public String getDeviceID(){

        TelephonyManager manager = (TelephonyManager ) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId= manager.getDeviceId();
        System.out.println(deviceId);
        return deviceId;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch(requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;

        }
        if(!permissionToRecordAccepted) getActivity().finish();
    }


}
