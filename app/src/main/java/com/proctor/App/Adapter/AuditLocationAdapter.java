package com.proctor.App.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.proctor.App.Activity.QuestionActivity;
import com.proctor.App.database.DatabaseHelper;
import com.proctor.App.model.Dashboard;
import com.proctor.App.model.Location;
import com.proctor.R;
import android.view.GestureDetector;

import java.util.List;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class AuditLocationAdapter extends RecyclerView.Adapter< AuditLocationAdapter.ContactViewHolder >
{
		//implements RecyclerView.OnItemTouchListener  {

	List< Location > locationData;
	Context context;
	public String title;
	public String userName;
	public String userId;

	private OnItemClickListener mListener;

	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

	GestureDetector mGestureDetector;


	public AuditLocationAdapter( Context context, List< Location > locationList, String title, String userName,String userId) {
		this.locationData = locationList;
		this.title = title;
		this.context = context;
		this.userName = userName;
		this.userId = userId;
		this.mListener = mListener;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	/*
	public AuditLocationAdapter( Context context, List< Location > locationList, String title, String userName,OnItemClickListener mListener ) {
		this.locationData = locationList;
		this.title = title;
		this.context = context;
		this.userName = userName;
		this.mListener = mListener;
	}
*/


	@Override
	public AuditLocationAdapter.ContactViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType ) {
		View itemView = LayoutInflater.
				from(viewGroup.getContext()).
				inflate(R.layout.location_card_view, viewGroup, false);

			return new ContactViewHolder(itemView);
	}
	/*
	@Override
	public AuditLocationAdapter.ContactViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType ) {
		View itemView = LayoutInflater.
				from(viewGroup.getContext()).
				inflate(R.layout.location_card_view, viewGroup, false);
		final ContactViewHolder pvh = new ContactViewHolder(itemView);
		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.onItemClick(v, pvh.getPosition());
			}
		});
		return pvh;
	//	return new ContactViewHolder(itemView);
	}
*/
	@Override
	public void onBindViewHolder( final AuditLocationAdapter.ContactViewHolder contactViewHolder, final int location ) {

		try {
			final Location obj = locationData.get(location);
			contactViewHolder.name.setText(obj.name);
			contactViewHolder.by.setText(obj.by);
			contactViewHolder.score.setText(String.valueOf(obj.score));
			contactViewHolder.date.setText(obj.date);
			if ( isResume(userId,userName,title, obj.name) ) {
				contactViewHolder.start.setImageResource(R.drawable.ic_pause_black_48dp);
			}
			contactViewHolder.start.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick( View v ) {
					Intent i = new Intent(context, QuestionActivity.class);
					Log.e("usernamae",userName+"   " +title+ "     " +contactViewHolder.name.getText().toString());
					i.putExtra("userid", userId);
					i.putExtra("username", userName);
					i.putExtra("type_name", title);
					i.putExtra("location_name", contactViewHolder.name.getText().toString());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				}
			});

			contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick( View v ) {

					Log.e("textview2223dh", String.valueOf(location));
					ViewGroup row = ( ViewGroup ) v.getParent();
				}
			});


		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		return locationData.size();
	}



	/*

	@Override
	public void onRequestDisallowInterceptTouchEvent( boolean disallowIntercept ) {

	}





	public AuditLocationAdapter(Context context, OnItemClickListener listener) {
		mListener = listener;
		mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
			@Override public boolean onSingleTapUp(MotionEvent e) {
				return true;
			}
		});
	}

	@Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
		View childView = view.findChildViewUnder(e.getX(), e.getY());
		if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
			mListener.onItemClick(childView, view.getChildPosition(childView));
			return true;
		}
		return false;
	}

	@Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }
*/
	public static class ContactViewHolder extends RecyclerView.ViewHolder {

		protected TextView name;
		protected TextView score;
		protected TextView date;
		protected TextView by;
		protected ImageView start;


		public ContactViewHolder( View v ) {
			super(v);
			name = ( TextView ) v.findViewById(R.id.loc_name);
			score = ( TextView ) v.findViewById(R.id.loc_score);
			date = ( TextView ) v.findViewById(R.id.loc_date);
			by = ( TextView ) v.findViewById(R.id.loc_by);
			start = ( ImageView ) v.findViewById(R.id.loc_start);
		}
}

	public Boolean isResume( String userid,String username, String type_name, String location_name ) {
		Boolean status = false;
		List< Dashboard > data = Dashboard.find(Dashboard.class, "(userid =? AND username=? AND type=? AND locationame=? AND status=?)",userid, username, type_name, location_name, "0");
		if ( data.size() > 0 ) {
			status = true;
		}
		return status;
	}


}
