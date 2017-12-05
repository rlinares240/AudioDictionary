package com.example.robert.audiodictionary;

import android.content.Context;
import android.media.MediaPlayer;
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
        TextView word;
        TextView name;
        //String soundLocation;
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

    public View getView(int position, View convertView, ViewGroup parent) {
         final ViewHolder holder;
        View nv = convertView;
        EntryTable entry = objects.get(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = (View) inflater.inflate(R.layout.entry_list, null);
            holder.word = (TextView) convertView.findViewById(R.id.entry_word);
            holder.name = (TextView) convertView.findViewById(R.id.author);
            holder.play = (Button) convertView.findViewById(R.id.play_button1);
            // holder.location = (TextView) nv.findViewById(R.id.region);
           // holder.soundLocation = entry.getSoundLocation();


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.word.setText(entry.getWord().toString());
        holder.name.setText(entry.getName().toString());
        System.out.println(entry.getRegion().toString());
        final String soundLocation=entry.getSoundLocation();
       // holder.soundLocation = entry.getSoundLocation();


       // System.out.println(soundLocation);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("INSIDE THE LISTENER");
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
}
