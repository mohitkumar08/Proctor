package com.proctor.App.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.proctor.App.Activity.QuestionActivity;
import com.proctor.App.model.Location;
import com.proctor.R;

import java.util.List;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class AuditLocationAdapter extends RecyclerView.Adapter<AuditLocationAdapter.ContactViewHolder> {

    List<Location> locationData;
    Context context;
    public String title;
    public String userName;

    public AuditLocationAdapter(Context context, List<Location> locationList, String title,String userName) {
        this.locationData = locationList;
        this.title = title;
        this.context = context;
        this.userName = userName;
    }

    @Override
    public AuditLocationAdapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.location_card_view, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AuditLocationAdapter.ContactViewHolder contactViewHolder, final int location) {

        try {
            Location obj = locationData.get(location);
            contactViewHolder.name.setText(obj.name);
            contactViewHolder.by.setText(obj.by);
            contactViewHolder.score.setText(String.valueOf(obj.score));
            contactViewHolder.date.setText(obj.date);

            contactViewHolder.start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent i =new Intent(context, QuestionActivity.class);
                i.putExtra("username",userName);
                i.putExtra("type_name",title);
                i.putExtra("location_name",contactViewHolder.name.getText().toString());
                context.startActivity(i);
                }
            });

            contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("textview2223dh", String.valueOf(location));
                    ViewGroup row = (ViewGroup) v.getParent();
                }
            });

        }
        catch (Exception e)
        {e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return locationData.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView score;
        protected TextView date;
        protected TextView by;
        protected Button start;

        public ContactViewHolder(View v) {

            super(v);

            name = (TextView) v.findViewById(R.id.loc_name);
            score = (TextView) v.findViewById(R.id.loc_score);
            date = (TextView) v.findViewById(R.id.loc_date);
            by = (TextView) v.findViewById(R.id.loc_by);
            start = (Button) v.findViewById(R.id.loc_start);

        }
    }


}
