package com.example.robert.audiodictionary;



        import android.annotation.SuppressLint;
        import android.app.Fragment;
        import android.content.Context;
        import android.media.MediaRecorder;
        import android.os.Bundle;
        import android.os.Environment;
        import android.telephony.TelephonyManager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.Toast;
        import android.media.MediaPlayer;
        import java.io.IOException;

/**
 * Created by Rodrigo on 11/19/17.
 */

public class SubmitForm extends Fragment {

    private Button  mSubmit, mPlay;
    private MediaRecorder mAudioRecorder;
    private Button mRecord,mStop;
    private String outputFile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecord = (Button) container.findViewById(R.id.record_button);
        mPlay = (Button) container.findViewById(R.id.play_button);
        mSubmit =(Button) container.findViewById(R.id.submit_button);
        mStop = (Button) container.findViewById(R.id.stop_button);
        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath() + "recording.3gp";
        mSubmit.setEnabled(false);
        mPlay.setEnabled(false);
        mStop.setEnabled(false);

        mAudioRecorder= new MediaRecorder();

        mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mAudioRecorder.setOutputFile(outputFile);



        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAudioRecorder.stop();
                mStop.setEnabled(false);
                mPlay.setEnabled(true);
                mSubmit.setEnabled(true);
                mRecord.setEnabled(true);
                Toast.makeText(getContext(),"Recording Complete",Toast.LENGTH_LONG).show();
                // save to a local file then when user clicks submit it will display the dialog
            }
        });


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                try{
                    player.setDataSource(outputFile);
                    player.prepare();
                    player.start();
                    Toast.makeText(getContext(),"Playing Audio",Toast.LENGTH_LONG).show();
                }catch(Exception e){

                }



            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //display dialog box to confirm user's submission, if entry already exists, then dialog box to confirm DB entry override


            }
        });


        return inflater.inflate(R.layout.form, container, false);


    }

    public String getDeviceID(){

        TelephonyManager manager = (TelephonyManager ) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId= manager.getDeviceId();
        return deviceId;
    }

}
