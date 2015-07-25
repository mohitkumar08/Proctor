package com.proctor.App.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.proctor.App.model.User;
import com.proctor.R;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */

// This Class is Helper Class For Login In there we have done Some Process Likes ->
// 1. Send Sync Request To Server using Volly
// 2.
public class LoginHelper implements Response.ErrorListener, Response.Listener<JSONObject> {
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

    public LoginHelper(Context context) {
        this.context = context;


    }

    public String isUserExist(User user) {
        Log.e("is user exist", "there");
        username = user.getUsername();
        password = user.getPassword();
        Log.e("username",username);
        Log.e("password",password);
        queue = CustomVollyRequestQueue.getInstance(context).getRequestQueue();
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

      /*

        JSONObject data = new JSONObject();
        JSONObject sendData = new JSONObject();
        String url = context.getResources().getString(R.string.login_url);
        String name="http://192.168.1.3/qm/index.php/api/";
        try {
            data.put("user_id", user.getUsername());
            data.put("password", user.getPassword());
            data.put("datetime", "2015-05-30 09:22:21");

         sendData.put("data",data);


            JSONObject userData = new JSONObject();
            userData.put("email",  user.getUsername());
            userData.put("password",  user.getUsername());


            Log.e("send data",sendData.toString());

            jsonRequest = new CustomJsonObjectRequest(Request.Method.POST, name,userData, this, this);

*/

//        jsonRequest.setTag(TAG1);

        //request.setPriority(Request.Priority.IMMEDIATE);

        queue.add(request);

        return status;

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        setSharedPreferences(username,password);
        Log.e("error of volly", volleyError.toString());

        if (volleyError.toString().equals("com.android.volley.TimeoutError")) {
            this.status = "Timeout Error";
        }
        if (volleyError.toString().equals("com.android.volley.NoConnectionError")) {
            this.status  = "NoConnectionError";
        }
        setSharedPreferences(username,password);
        Intent i =new Intent(context, AuditActivity.class);
        context.startActivity(i);

    }

    @Override
    public void onResponse(JSONObject response) {
        setSharedPreferences(username,password);
        Log.e("response", response.toString());
        JSONObject responseObject = response;
        Intent i =new Intent(context, AuditActivity.class);
        context.startActivity(i);

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

    public void setSharedPreferences(String username,String password)
    {
        sharedpreferences = context.getSharedPreferences("LoggedUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

    }
}
