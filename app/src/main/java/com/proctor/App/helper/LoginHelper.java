package com.proctor.App.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.proctor.App.Activity.AuditActivity;
import com.proctor.App.Activity.LoginActivity;
import com.proctor.App.HttpHelper.CustomJsonObjectRequest;
import com.proctor.App.HttpHelper.CustomVollyRequestQueue;
import com.proctor.App.HttpHelper.VolleyCustomRequest;
import com.proctor.App.model.Audit;
import com.proctor.App.model.Location;
import com.proctor.App.model.Question;
import com.proctor.App.model.User;
import com.proctor.R;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */

// This Class is Helper Class For Login In there we have done Some Process Likes ->
// 1. Send Sync Request To Server using Volly
// 2.
public class LoginHelper implements Response.ErrorListener, Response.Listener< JSONObject > {
	Context context;

	RequestQueue queue;
	CustomJsonObjectRequest jsonRequest;
	public static final String TAG1 = "LOGIN";
	public static String status = null;
	public static String username = null;
	public static String password = null;
	SharedPreferences sharedpreferences;

	public LoginHelper() {
	}

	public LoginHelper( Context context ) {
		this.context = context;


	}

	public String isUserExist( User user ) {
		Log.e("is user exist", "there");
		username = user.getUsername();
		password = user.getPassword();
		Log.e("username", username);
		Log.e("password", password);

		queue = CustomVollyRequestQueue.getInstance(context).getRequestQueue();
	   	/*
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("user_id", "1"));
        params.add(new BasicNameValuePair("password", "test@123"));
        params.add(new BasicNameValuePair("datetime", "2015-05-30 09:22:21"));

// Create Request
        VolleyCustomRequest request = new VolleyCustomRequest(Request.Method.POST, "http://54.64.5.133/proctr/index.php/api/Login", params,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                            Log.e("respomse",response.toString());
                        setSharedPreferences(username,password);
                        Intent i =new Intent(context, AuditActivity.class);
                        context.startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        Log.e("errorof response",error.toString());
                        setSharedPreferences(username,password);
                        Intent i =new Intent(context, AuditActivity.class);
                        context.startActivity(i);
                    }
                }
        );
*/


		JSONObject data = new JSONObject();
		JSONObject sendData = new JSONObject();
		String url = "http://54.64.5.133/proctr/index.php/api/Login/";
		//String getData = "http://54.64.5.133/proctr/index.php/api/Get_proctr_new";
		try {
			data.put("user_id", user.getUsername());
			data.put("password", user.getPassword());
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			data.put("datetime", timeStamp);
			sendData.put("data", data);
			Log.e("send data", sendData.toString());

			jsonRequest = new CustomJsonObjectRequest(Request.Method.POST, url, sendData, this, this);
		} catch ( Exception e ) {
			e.printStackTrace();
		}


		jsonRequest.setTag(TAG1);
		jsonRequest.setPriority(Request.Priority.IMMEDIATE);
		queue.add(jsonRequest);
		return status;

	}

	@Override
	public void onErrorResponse( VolleyError volleyError ) {

		Log.e("error of volly", volleyError.toString());

		if ( volleyError.toString().equals("com.android.volley.TimeoutError") ) {
			this.status = "Timeout Error";
			Toast.makeText(context,"Timeout Error",Toast.LENGTH_LONG).show();

		}
		if ( volleyError.toString().equals("com.android.volley.NoConnectionError") ) {
			this.status = "NoConnectionError";
			Toast.makeText(context,"No Connection Error",Toast.LENGTH_LONG).show();
		}
		/*setSharedPreferences(username, password);
		Intent i =new Intent(context, AuditActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
		context.startActivity(i);
		*/

	}

	@Override
	public void onResponse( JSONObject response ) {

		Log.e("response", response.toString());
	//	JSONObject responseObject = null;
		try {

			String status =response.getString("status");
			if(status.equals("200"))
			{
				//responseObject = response.getJSONObject("master_data");
//				JSONObject responseObject = new JSONObject();
//				Log.e("dataaa",responseObject.toString());
				setTablesData(response);

				Intent i =new Intent(context, AuditActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(i);

			}
			else
			{
				status =response.getString("msg");
				Toast.makeText(context,status,Toast.LENGTH_LONG).show();
			}

		} catch ( JSONException e ) {
			e.printStackTrace();
		}




/*
        try {
            int status = Integer.parseInt(responseObject.getString("status"));
            if(status==200)
            {
                Intent i =new Intent(context, AuditActivity.class);
                context.startActivity(i);
            }
            else
            {
                Toast.makeText(context,"Error To Login",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
*/


	}


	public void setSharedPreferences( String username, String password ) {
		sharedpreferences = context.getSharedPreferences("LoggedUser", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putString("id", username);
		editor.putString("name", password);
		editor.commit();

	}

	public void setTablesData( JSONObject object ) {

		try {
			JSONArray data = object.getJSONArray("type");
			setSharedPreferences(object.getString("id"), object.getString("name"));
			for ( int i = 0; i < data.length(); i++ ) {
				JSONObject type_data = data.getJSONObject(i);
				String type_name = type_data.getString("name");

				Audit auditType = new Audit(type_name);
				auditType.save();


				int type_id = type_data.getInt("id");
				Audit audit = new Audit(type_name);
				audit.save();
				JSONArray location_type_data = type_data.getJSONArray("Location");
				for ( int j = 0; j < location_type_data.length(); j++ ) {
					JSONObject locationObject = location_type_data.getJSONObject(j);

					Location location = new Location(locationObject.getString("name"), locationObject.getInt("score"), locationObject.getString("by"), locationObject.getString("date"), type_name,locationObject.getInt("loc_id"));
					location.save();
				}



				JSONArray category_type_data = type_data.getJSONArray("Category");
				for ( int k = 0; k < category_type_data.length(); k++ ) {
					JSONObject category_object = category_type_data.getJSONObject(k);
					JSONArray questions = category_object.getJSONArray("Question");
					for ( int l = 0; l < questions.length(); l++ ) {
						JSONObject temp_question = questions.getJSONObject(l);

						Question question = new Question(type_name, category_object.getString("name"), temp_question.getString("name"), temp_question.getString("photo"), temp_question.getInt("score"), l,type_id,category_object.getInt("id"));
						question.save();
					}
				}
			}


		} catch ( JSONException e ) {
			e.printStackTrace();
		}

	}

	private void showData() {

		List< Location > loc = Location.listAll(Location.class);
		List< Question > ques = Question.listAll(Question.class);
		Log.e("size of list", String.valueOf(loc.size()) + "   " + String.valueOf(ques.size()
		));

		for ( Location location : loc ) {
			Log.e("Location", location.getName() + location.getAudit());
		}
		for ( Question question : ques ) {
			Log.e("Question", question.getAuditType() + " " + question.getCategoryName() + " " + question.getQuestionText());
		}

	}


}
