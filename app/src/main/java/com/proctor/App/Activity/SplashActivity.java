package com.proctor.App.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.proctor.App.database.DatabaseHelper;
import com.proctor.App.helper.Initialize;
import com.proctor.App.model.Dashboard;
import com.proctor.R;
import com.rey.material.widget.ProgressView;

import java.util.List;


//This is Launcher Activity . In There We Create Table And Check That User Is Already Login Or Not.
//If User Already Login Then we send user to DashBoard .
//If User Not Logged Then Go to LoginActivity

public class SplashActivity extends ActionBarActivity {

    protected Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String LOGGED_USER = "LoggedUser";
    DatabaseHelper helper;
    Initialize initial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;


        initial = new Initialize(context, new DatabaseHelper(getApplicationContext()));
        initial.startOperation();


        //Check That User Logged or Not
        try {
            Boolean value = is_Logged();
            if (value) {

            } else {

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                //Intent intent = new Intent(SplashActivity.this, HelpShiftIntegration.class);
                startActivity(intent);
                finish();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    //This method for Already Login
    private boolean is_Logged() {
        Boolean returnValue = false;
        try {
            prefs = context.getSharedPreferences(LOGGED_USER, MODE_PRIVATE);
            String userName, passWord;
            userName = prefs.getString("userName", null);
            passWord = prefs.getString("passWord", null);
            Log.e("user name", userName);
            Log.e("password", passWord);
            if (userName != null && passWord != null) {
                returnValue = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

}
