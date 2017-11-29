package com.example.robert.audiodictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by billk on 11/29/2017.
 */

public class EntryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<EntryTable> objects;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
    }

    public EntryAdapter(Context context, ArrayList<EntryTable> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
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
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = (View) inflater.inflate(R.layout.entry_list, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.word);
            holder.textView2 = (TextView) convertView.findViewById(R.id.submission_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView1.setText(objects.get(position).getWord());
        holder.textView2.setText(objects.get(position).getName());
        return convertView;
    }
}
