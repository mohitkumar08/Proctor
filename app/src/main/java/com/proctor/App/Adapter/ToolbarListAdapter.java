package com.proctor.App.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proctor.App.model.Caategory;
import com.proctor.App.model.Inspection;
import com.proctor.R;

import java.util.List;

/**
 * Created by MOHIT KUMAR on 7/17/2015.
 */
public class ToolbarListAdapter  extends ArrayAdapter<Caategory> {
    public LayoutInflater mInflater;
    List<Caategory> locationData;
    Context context;
    public ToolbarListAdapter(Context context, int resource,  List<Caategory>  objects) {
        super(context,resource ,  objects);
        this.context = context;
        locationData = objects;
        mInflater = LayoutInflater.from(context);

    }
    public void swapItems(List<Caategory> items) {
        this.locationData = items;
        notifyDataSetChanged();
    }

    private class ViewHolder {

        public TextView categoryName,total,attempt,complete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.toolbar_list_items, parent, false);
            holder = new ViewHolder();

            holder.categoryName = (TextView)convertView.findViewById(R.id.toolbar_cat_name);
            holder.attempt = (TextView)convertView.findViewById(R.id.toolbar_cat_total);
            holder.total = (TextView)convertView.findViewById(R.id.toolbar_cat_attempt);
            holder.complete = (TextView)convertView.findViewById(R.id.cat_complete);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder)convertView.getTag();
        }

        Caategory loc = locationData.get(position);
        holder.categoryName.setText(position+1+". "+loc.getCategory());
        holder.total.setText(String.valueOf(loc.getCountQuestion()));
        holder.attempt.setText(String.valueOf(loc.getAttempt()));

        holder.complete.setText(String.valueOf(loc.getScore())+"%");

        return convertView;
    }

}

