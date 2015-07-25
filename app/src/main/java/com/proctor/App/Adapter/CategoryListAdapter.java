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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MOHIT KUMAR on 7/16/2015.
 */
public class CategoryListAdapter  extends ArrayAdapter<Caategory> {
    public LayoutInflater mInflater;
   List <Caategory> locationData;
    Context context;

    public CategoryListAdapter(Context context, int resource,   List<Caategory>   objects) {
        super(context,resource ,objects);
        this.context = context;
        locationData = objects;
        mInflater = LayoutInflater.from(context);

    }

    private class ViewHolder {

        public TextView categoryname,category_complete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_category, parent, false);
            holder = new ViewHolder();

            holder.categoryname = (TextView)convertView.findViewById(R.id.txt_cat_name);
            convertView.setTag(holder);
            holder.category_complete = (TextView)convertView.findViewById(R.id.txt_category_score);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder)convertView.getTag();
        }
        Caategory category = locationData.get(position);

//        if(category.attempt==0)
//        {
//            holder.categoryname.setBackgroundColor(Color.parseColor("#F42116"));
//           holder.category_complete.setBackgroundColor(Color.parseColor("#F42116"));
//        }
//        else
//        {
//            holder.categoryname.setBackgroundColor(Color.parseColor("#5FFF14"));
//           holder.category_complete.setBackgroundColor(Color.parseColor("#5FFF14"));
//        }
        holder.categoryname.setText(category.category);
        float complete_per;
        try {
         complete_per= (category.attempt * 100) / category.countQuestion;
        }
        catch (ArithmeticException e)
        {
            complete_per =0;
            e.printStackTrace();
        }
        holder.category_complete.setText(String.valueOf(category.getScore())+"%");

        return convertView;
    }

}

