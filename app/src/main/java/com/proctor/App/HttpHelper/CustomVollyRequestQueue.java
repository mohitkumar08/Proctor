package com.proctor.App.HttpHelper;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import android.content.Context;

public class CustomVollyRequestQueue {
	 private static CustomVollyRequestQueue mInstance;
	    private static Context mCtx;
	    private RequestQueue mRequestQueue;
	 
	   
	    private CustomVollyRequestQueue(Context context) {
	        mCtx = context;
	        mRequestQueue = getRequestQueue();
	    }
	 
	    public static synchronized CustomVollyRequestQueue getInstance(Context context) {
	        if (mInstance == null) {
	            mInstance = new CustomVollyRequestQueue(context);
	        }
	        return mInstance;
	    }
	 
	    public RequestQueue getRequestQueue() {
	        if (mRequestQueue == null) {
	            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
	            Network network = new BasicNetwork(new HurlStack());
	            mRequestQueue = new RequestQueue(cache, network);
	            // Don't forget to start the volley request queue
	            mRequestQueue.start();
	        }
	        return mRequestQueue;
	    }
	}
