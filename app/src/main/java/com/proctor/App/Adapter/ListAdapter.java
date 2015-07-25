package com.proctor.App.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proctor.App.model.Inspection;
import com.proctor.R;

import java.util.List;

/**
 * Created by MOHIT KUMAR on 7/14/2015.
 */
public class ListAdapter extends ArrayAdapter<Inspection> {
    public LayoutInflater mInflater;
    List<Inspection> locationData;
    Context context;




    public ListAdapter(Context context, int resource, List<Inspection> objects) {
        super(context, resource, objects);
        this.context = context;
        locationData = objects;
        mInflater = LayoutInflater.from(context);


    }

    private class ViewHolder {

        public TextView name;
        public ImageView imageview;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.list_text);
            holder.imageview = (ImageView) convertView.findViewById(R.id.captureImage);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Inspection loc = locationData.get(position);
       
        if (loc.attempt == 2) {
            holder.name.setBackgroundColor(Color.parseColor("#eeeeee"));
        } else if (loc.attempt == 0) {
            holder.name.setBackgroundColor(Color.parseColor("#5FFF14"));
        } else {
            holder.name.setBackgroundColor(Color.parseColor("#F42116"));

        }


		if (loc.getPhotopath()!=null) {
			holder.imageview.setVisibility(View.VISIBLE);

		}
        holder.name.setText(loc.getQuestionname());

        return convertView;
    }



}

