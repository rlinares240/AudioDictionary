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
        ViewHolder holder;
        View nv = convertView;
        EntryTable entry = objects.get(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = (View) inflater.inflate(R.layout.entry_list, null);
            holder.word = (TextView) convertView.findViewById(R.id.entry_word);
            holder.name = (TextView) convertView.findViewById(R.id.author);
            holder.play = (Button) convertView.findViewById(R.id.play_button);
            // holder.location = (TextView) nv.findViewById(R.id.region);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.word.setText(entry.getWord().toString());
        holder.name.setText(entry.getName().toString());

        return convertView;
    }
    public void add(EntryTable listItem) {
        this.objects.add(listItem);
        notifyDataSetChanged();
    }
}
