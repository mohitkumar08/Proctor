package com.proctor.App.HttpHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

public class CustomJsonObjectRequest extends JsonObjectRequest {
	private Map<String, String> headers = new HashMap<String, String>();
	Priority mPriority;

	public CustomJsonObjectRequest(int method, String url,
			JSONObject jsonRequest, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RetryPolicy getRetryPolicy() {
		// here you can write a custom retry policy
		return super.getRetryPolicy();

	}

	/**
	 * Custom class!
	 */
	public void setCookies(List<String> cookies) {
		StringBuilder sb = new StringBuilder();
		for (String cookie : cookies) {
			sb.append(cookie).append("; ");
		}
		headers.put("Cookie", sb.toString());
	}

//	@Override
//	public Map<String, String> getHeaders() throws AuthFailureError {
//		return headers;
//	}

	public void setPriority(Priority priority) {
		mPriority = priority;
	}

	@Override
	public Priority getPriority() {
		// If you didn't use the setPriority method,
		// the priority is automatically set to NORMAL
		return mPriority != null ? mPriority : Priority.NORMAL;
	}

	@Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Content-Type","text/plain");
        return headers;
    }
}
