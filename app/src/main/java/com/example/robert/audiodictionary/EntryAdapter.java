package com.example.robert.audiodictionary;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by billk on 11/29/2017.
 */

public class EntryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<EntryTable> objects = new ArrayList<EntryTable>();

    public class ViewHolder {
        TextView accuracy;
        TextView name;
        TextView clarity;
        Button play;
    }

    public EntryAdapter(Context context, ArrayList<EntryTable> items) {
        inflater = LayoutInflater.from(context);
        this.objects = items;

    }

    public int getCount() {
        return objects.size();
    }

    public EntryTable getItem(int position) {
        return objects.get(position);
    }



    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
         final ViewHolder holder;
        View nv = convertView;
        EntryTable entry = objects.get(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = (View) inflater.inflate(R.layout.entry_list, null);
            holder.name = (TextView) convertView.findViewById(R.id.author);
            holder.accuracy = (TextView) convertView.findViewById(R.id.accuracy_tally);
            holder.clarity = (TextView) convertView.findViewById(R.id.clarity_tally);
            holder.play = (Button) convertView.findViewById(R.id.play_button1);
           // holder.soundLocation = entry.getSoundLocation();


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int temp = entry.getAccuracyBadNum() + entry.getAccuracyGoodNum();
        holder.accuracy.setText("" + entry.getAccuracyGoodNum() + "/" + temp );
        int temp2 = entry.getClarityBadNum() + entry.getClarityGoodNum();
        holder.clarity.setText("" + entry.getClarityGoodNum() + "/" + temp );
        holder.name.setText(entry.getName().toString());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Context context = parent.getContext();
                Fragment fragment = new ReviewSound();
                Bundle bundle = new Bundle();
                bundle.putString("name",holder.name.getText().toString());
                fragment.setArguments(bundle);
               FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.action_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
//        System.out.println(entry.getRegion().toString());
        final String soundLocation = entry.getSoundLocation();
       // holder.soundLocation = entry.getSoundLocation();


       // System.out.println(soundLocation);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("INSIDE THE LISTENER");
                MediaPlayer player = new MediaPlayer();
                try{
                   System.out.println(soundLocation);
                    player.setDataSource(soundLocation);

                    player.prepare();
                    player.start();
                    Toast.makeText(view.getContext(),"playing audio",Toast.LENGTH_SHORT).show();

                }catch(Exception e){

                }
            }
        });

        return convertView;
    }
    public void add(EntryTable listItem) {
        this.objects.add(listItem);
        notifyDataSetChanged();
    }
    public int getPercentage(int a, int b) {
        Log.d("numbers", a + "   " + b);
        int temp = 0;
        if(a/(a+b) == temp)  {
            return temp;
        }else {
            return a/(a+b);
        }

    }
}
