package com.proctor.App.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proctor.App.Activity.QuestionActivity;
import com.proctor.App.model.Dashboard;
import com.proctor.R;

import java.util.List;

import co.dift.ui.SwipeToAction;

/**
 * Created by MOHIT KUMAR on 7/18/2015.
 */
public class AuditSummaryAdapter extends RecyclerView.Adapter<AuditSummaryAdapter.DashboardViewHolder> {

    List<Dashboard> dashBoardData;
    Context context;
   public int clickId;
    ListPostion listPostion ;

    public AuditSummaryAdapter(Context context, List<Dashboard> locationList) {
        this.dashBoardData = locationList;
        this.context = context;

    }

    @Override
    public AuditSummaryAdapter.DashboardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_summary_list_item, viewGroup, false);

        return new DashboardViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        try {
            return dashBoardData.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final AuditSummaryAdapter.DashboardViewHolder dashboardViewHolder, final int position) {

        final Dashboard obj = dashBoardData.get(position);
        dashboardViewHolder.name.setText(obj.username);
        dashboardViewHolder.location.setText(obj.locationame);
        dashboardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context, QuestionActivity.class);
                i.putExtra("username",obj.getUsername());
                i.putExtra("type_name",obj.getType());
                i.putExtra("location_name",obj.getLocationame());
                context.startActivity(i);

            }
        });
    }


    public interface ListPostion {
        public static String idClick = null;
        public void clickMyAccess();
    }


    public class DashboardViewHolder extends RecyclerView.ViewHolder{

        protected TextView name;
        protected TextView location;

        public DashboardViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.summary_username);
            location = (TextView) v.findViewById(R.id.summary_location);
        }
    }
}

