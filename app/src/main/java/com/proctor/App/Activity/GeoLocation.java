package com.proctor.App.Activity;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.proctor.R;

import java.text.DateFormat;
import java.util.Date;

public class GeoLocation extends ActionBarActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	LocationRequest mLocationRequest;
	GoogleApiClient mGoogleApiClient;
	Context context;
	Location mCurrentLocation;
	String mLastUpdateTime;
	Location mLastLocation;

	private static final String TAG = "LocationActivity";
	private static final long INTERVAL = 1000 * 10;
	private static final long FASTEST_INTERVAL = 1000 * 5;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		buildGoogleApiClient();


	}


	@Override
	public void onStart() {
		super.onStart();
		Toast.makeText(getApplicationContext(), "Start ", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart fired ..............");
		mGoogleApiClient.connect();
	}




	// Google Location //

	protected synchronized void buildGoogleApiClient() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
	}


	@Override
	public void onConnected(Bundle bundle) {
		Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
		// startLocationUpdates();
		 mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		if (mCurrentLocation != null) {
			Toast.makeText(context,String.valueOf(mCurrentLocation.getLatitude())+" "+String.valueOf(mCurrentLocation.getLongitude()),Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "get no gps", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onConnectionSuspended(int i) {

	}


	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "Firing onLocationChanged..............................................");
		mCurrentLocation = location;
		mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
	}



	@Override
	public void onConnectionFailed( ConnectionResult connectionResult ) {

	}

}
