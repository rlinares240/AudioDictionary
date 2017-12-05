package com.example.robert.audiodictionary;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.UUID;

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

    private String deviceID;
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
        //deviceID = UUID.randomUUID().toString();
        mRecord = (Button) getView().findViewById(R.id.record_button);
        mPlay = (Button) getView().findViewById(R.id.play_button);
        mSubmit =(Button) getView().findViewById(R.id.submit_button);
        mStop = (Button) getView().findViewById(R.id.stop_button);
        mName = (EditText) getView().findViewById(R.id.submission_name);
        mWord = (TextView) getActivity().findViewById(R.id.word);
        mRegion = (EditText)getActivity().findViewById(R.id.region);
      //outputFile= getActivity().getExternalCacheDir().getAbsolutePath() + "/recording.3gp";


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

                //TODO need to check if deviceId already exists in DB, for the word entry. if yes then confirm override
                try{
                    File tempFile = File.createTempFile("VoiceRecorder",".3gp", getActivity().getExternalFilesDir(null));
                    mSubmit.setTag(tempFile);
                    mPlay.setTag(tempFile);
                    mAudioRecorder.setOutputFile(tempFile.getAbsolutePath());
                    System.out.println("TEMP FILE" + tempFile.getAbsolutePath());
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();
                }catch(IllegalStateException e){

                }catch(IOException i){

                }
                mRecord.setEnabled(false);
                mStop.setEnabled(true);
                Toast.makeText(getContext(),"Recording in Progress",Toast.LENGTH_SHORT).show();
            }
        });



        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAudioRecorder.stop();
                mAudioRecorder.release();

                mStop.setEnabled(false);
                mPlay.setEnabled(true);
                mSubmit.setEnabled(true);
                mRecord.setEnabled(true);
                Toast.makeText(getContext(),"Recording Complete",Toast.LENGTH_SHORT).show();
                // save to a local file then when user clicks submit it will display the dialog

            }
        });
//
//
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                File output = (File) view.getTag();
                outputFile = output.getAbsolutePath();
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
            public void onClick(final View v) {


                boolean entryExists = false;

               entryExists= mDatabase.checkIfExists("tbl2",mWord.getText().toString(),
                        mName.getText().toString());



                if(entryExists == true){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Entry Exists");
                    alert.setMessage("An entry for this already exits, do you want to overwrite it?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EntryTable contact = new EntryTable();
                            contact.setWord(mWord.getText().toString());
                            contact.setName(mName.getText().toString());
                            contact.setRegion(mRegion.getText().toString());
//
                            File temp = (File) v.getTag();
                            File newFile = new File(getActivity().getExternalFilesDir(null).getAbsoluteFile() +File.separator+ mName.getText().toString() + mWord.getText().toString()+".3gp");

                            temp.renameTo(newFile);
                            contact.setSoundLocation(newFile.getAbsolutePath());
                            int changed = mDatabase.updateEntry(contact);

                           System.out.println("ROWS UPDATED: " + changed);
                           dialogInterface.dismiss();
                            getActivity().getFragmentManager().popBackStack();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                        alert.show();
                }else {
                   final  EntryTable contact = new EntryTable();

                    contact.setWord(mWord.getText().toString());
                    contact.setName(mName.getText().toString());
                    contact.setRegion(mRegion.getText().toString());

                    File temp = (File)v.getTag();
                    File newFile = new File(getActivity().getExternalFilesDir(null).getAbsoluteFile()  +"/"+ mName.getText().toString() + mWord.getText().toString()+".3gp");
                    temp.renameTo(newFile);
                    contact.setSoundLocation(newFile.getAbsolutePath());




                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Confirm Submission");
                    alert.setMessage("Are you sure you want to submit?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           long res = mDatabase.insertEntry(contact);
//                            if (res == 1) {
//                                getActivity().getFragmentManager().popBackStack();
//                                ArrayList<EntryTable> entries = mDatabase.getAllRecords();
//                                for (EntryTable a : entries) {
//                                    Log.d("Name", a.getName());
//                                }
//                            }
                            dialogInterface.dismiss();
                            getActivity().getFragmentManager().popBackStack();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    alert.show();


                }

            }
        });


    }




//    public String getDeviceID(){
//
//        TelephonyManager manager = (TelephonyManager ) getContext().getSystemService(Context.TELEPHONY_SERVICE);
//        @SuppressLint("MissingPermission") String deviceId= manager.getDeviceId();
//        System.out.println(deviceId);
//        return deviceId;
//    }

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
