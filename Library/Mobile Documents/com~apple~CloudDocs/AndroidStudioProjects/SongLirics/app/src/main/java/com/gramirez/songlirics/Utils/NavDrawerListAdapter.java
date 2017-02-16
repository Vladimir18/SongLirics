package com.gramirez.songlirics.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gramirez.songlirics.Model.MenuItem;
import com.gramirez.songlirics.R;

import java.util.ArrayList;

/**
 * Created by gilbertramirez on 2/14/17.
 */
public class NavDrawerListAdapter extends BaseAdapter {
    ArrayList<MenuItem> items;
    private LayoutInflater inflater;

    public NavDrawerListAdapter(ArrayList<MenuItem> items, LayoutInflater inflater) {
        this.items = items;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setSelected(int position) {

        for (int i = 0; i < items.size(); i++) {
            if (i == position) {
                items.get(i).setSelected(true);
                continue;
            }
            items.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.navdrawer_list_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(items.get(position).getNombre());

        if (items.get(position).isSelected()) {
            textView.setBackgroundResource(android.R.color.holo_red_light);
        } else {
            textView.setBackgroundResource(0);
        }

        return convertView;
    }
}
