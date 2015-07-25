package com.proctor.App.Activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.proctor.App.hardwareaccess.CameraAccess;
import com.proctor.App.helper.CommonHelper;
import com.proctor.App.helper.LoginHelper;
import com.proctor.App.model.User;
import com.proctor.R;
import com.rey.material.widget.ProgressView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


//This Is Login Activity Which is check user is Valid or Not
public class LoginActivity extends Activity {

    @Bind(R.id.email)
    public EditText emailId;

    @Bind(R.id.password)
    public EditText password;


    public Context context;

    private Pattern pattern;
    private Matcher matcher;
    public String status = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        //This is for annotation binding using Butter Knife dependency.
        ButterKnife.bind(this);

        //Compile the pattern of email matching
        String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+(.[a-zA-Z0-9]+)*(.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);

        //This code for email validation[Which is perfect]
        /*
        emailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                matcher = pattern.matcher(String.valueOf(s));
                if (matcher.matches()) {
                    emailId.setError(null);
                } else {
                    emailId.setError("Enter Valid Email Id");
                }
            }
        });
*/

        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        Log.e("path", path);

    }

    //This Method call when user wants  Sign in The App
    @OnClick(R.id.signIn)
    public void signIn(View view) {
        // TODO submit data to server...
        Log.e("if", String.valueOf(emailId.length()) + "and" + String.valueOf(password.length()) + "and" + password.getError());
        if ((emailId.length() <= 0) & password.length() <= 0) {
            Log.e("if", "if");
            emailId.setError("please enter email id");
            password.setError("please enter password");
        }
        if (emailId.length() < 1) {
            emailId.setError("please enter email id");
        }
        if (password.length() < 6) {
            password.setError("please enter password");
        }
        if ((emailId.getError() != null)) {
            emailId.setError("please enter email ");
        }
        if ((password.getError() != null)) {
            password.setError("please enter password");
        } else {
            CommonHelper commonHelper = new CommonHelper(context);

            if (!(commonHelper.isConnectingToInternet(context))) {
                Toast.makeText(context, "No Internet Connection.. Please Connect The Internet or Try Again Later", Toast.LENGTH_LONG).show();
            }
            if (!(commonHelper.isGpsPresent(context))) {
                Toast.makeText(context, "No GPS Enabled ..Please Connect The GPS or Try Again Later", Toast.LENGTH_LONG).show();
            } else {

                try {

                    Log.e("error of volly", emailId.getText().toString() + " " + password.getText().toString());
                    LoginHelper helper = new LoginHelper(context);
                    User user = new User(emailId.getText().toString(), password.getText().toString());

                    helper.isUserExist(user);
                    // Thread.sleep(2000);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }


}
