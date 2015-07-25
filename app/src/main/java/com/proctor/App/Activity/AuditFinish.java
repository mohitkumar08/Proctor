package com.proctor.App.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.proctor.App.Adapter.CategoryListAdapter;

import com.proctor.App.Adapter.ToolbarListAdapter;
import com.proctor.App.database.DatabaseHelper;
import com.proctor.App.helper.QuestionUploadCondition;
import com.proctor.App.model.Caategory;
import com.proctor.App.model.Dashboard;

import com.proctor.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AuditFinish extends ActionBarActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    Intent intent;
    public static int userDashboardId = 0;


    final static int TAKE_PICTURE = 1;
    ListView list;
    TextView totalScore;
    QuestionUploadCondition uploadQuestion;
    Context context;
    TextView comment_1, comment_2, comment_3;
    ImageView image_take_1, image_take_2;
    private static String IMAGE_PATH = " ";
    private static Uri srcImageUri = null;
    private static String IMAGE_NAME = null;
    private static Long clickId;
    LinearLayout listViewLayout;

    // Add Toolbar
    public Toolbar toolbar;
    public LinearLayout layout;
    ListView toolbar_list;

    public  static Boolean isClick = true;
    SQLiteDatabase sqlite_DB;
    DatabaseHelper dbHelper;
    //

    Button submit_data;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_finish);


        dbHelper = new DatabaseHelper(getBaseContext());
        sqlite_DB = dbHelper.getWritableDatabase();

        list = (ListView) findViewById(R.id.audit_finish_category);
        totalScore = (TextView) findViewById(R.id.score_text);
        listViewLayout = (LinearLayout) findViewById(R.id.listviewLayout);


        comment_1 = (EditText) findViewById(R.id.comment_1);
        comment_2 = (EditText) findViewById(R.id.comment_2);
        comment_3 = (EditText) findViewById(R.id.comment_3);

        image_take_1 = (ImageView) findViewById(R.id.image_btn_1);
        image_take_2 = (ImageView) findViewById(R.id.image_btn_2);
        submit_data = (Button) findViewById(R.id.button_submit);

        image_take_1.setOnClickListener(this);
        image_take_2.setOnClickListener(this);
        submit_data.setOnClickListener(this);

        context = this;

        intent = getIntent();
        userDashboardId = intent.getIntExtra("userDashboardId", 0);
        Log.e("dashboard id", String.valueOf(userDashboardId));
        checkAuditStatus();
        setFolderLocation();


        // Toolbar SEtup
        toolbar = (Toolbar) findViewById(R.id.finish_awesome_toolbar);
        Toolbar.LayoutParams layoutParams=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT, Gravity.LEFT| Gravity.CENTER_VERTICAL);
        layoutParams.leftMargin =20;
        layoutParams.rightMargin =20;
        toolbar.setTitle("Category ");
        layout = (LinearLayout) findViewById(R.id.finish_linearLayout_toolbar);
//        toolbar_list.setOnItemClickListener(this);
        //setToolbarList();
       /* toolbar.inflateMenu(R.menu.finish_audit);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.show_category:

                        if (isClick) {
                            isClick = false;
                            Animation bottomUp = new AlphaAnimation(0, 1);
                            bottomUp.setInterpolator(new DecelerateInterpolator()); //add this
                            bottomUp.setDuration(50);
                            layout.startAnimation(bottomUp);
                            layout.setVisibility(View.VISIBLE);

                        } else {
                            isClick = true;
                            //hideAnimation();
                            Animation bottomDown = new AlphaAnimation(1, 0);
                            bottomDown.setInterpolator(new AccelerateInterpolator()); //and this
                            bottomDown.setDuration(50);
                            layout.startAnimation(bottomDown);
                            layout.setVisibility(View.GONE);

                        }
                        break;


                }
                return false;
            }
        });
*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audit_finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.finish_audit) {
            Intent intent = new Intent(context, AuditSummary.class);
            context.startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_btn_1:
                getImageFromCamera();
                break;
            case R.id.image_btn_2:
                getImageFromCamera();
                break;
            case R.id.button_submit:
                buttonSubmit();
                break;

        }
    }

    private void checkAuditStatus() {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
            SQLiteDatabase sqlite_DB;
            sqlite_DB = dbHelper.getWritableDatabase();

            List<Caategory> mapList = new ArrayList<Caategory>();
            mapList = dbHelper.getCategoryQuestionStatus(sqlite_DB, userDashboardId);
Log.e("size of map list",String.valueOf(mapList.size()));
            CategoryListAdapter adapter = new CategoryListAdapter(getApplicationContext(), 0, mapList);
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
            LayoutParams params = listViewLayout.getLayoutParams();
            params.height = 55 * mapList.size();
            uploadQuestion = new QuestionUploadCondition();

            String score = String.valueOf(uploadQuestion.getScoreDashboard(userDashboardId));

            totalScore.setText(score);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void buttonSubmit() {
        Dashboard dashboard = uploadQuestion.getDashboard(userDashboardId);
        if (dashboard.getStatus() != 0) {
            Toast.makeText(context, "You Have Completed Audit", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "You are Not Completed Audit Your Audit Resume", Toast.LENGTH_LONG).show();
        }
    }


    public void getImageFromCamera() {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        File file = new File(IMAGE_PATH + imageName + "/", IMAGE_NAME + ".jpg");
        srcImageUri = Uri.fromFile(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, srcImageUri); // set the image file name
        // start the image capture Intent
        startActivityForResult(intent, TAKE_PICTURE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {

                    // saveImagePathTable(new File(IMAGE_PATH+String.valueOf(userDashboardId)+"/",IMAGE_NAME+".jpg"));
                }
                break;
        }
    }


    private void setFolderLocation() {
        if (new File("/storage/extSdCard").exists()) {
            IMAGE_PATH = "/storage/extSdCard/proctor/";

        } else {
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
            IMAGE_PATH = path + "/proctor/";
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent i = new Intent(this, AuditSummary.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //i.putExtra("EmpId",eeid);
            this.startActivity(i);
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void setToolbarList() {

        dbHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase sqlite_DB;
        int returnValue = 0;
        sqlite_DB = dbHelper.getWritableDatabase();
        List<Caategory> categoryDetail = dbHelper.getCategoryQuestionStatus(sqlite_DB, userDashboardId);

        ToolbarListAdapter adapter = new ToolbarListAdapter(getApplicationContext(), 0, categoryDetail);
        adapter.notifyDataSetChanged();
        toolbar_list.setAdapter(adapter);
        Log.e("out", "out");


    }


    //OnClick Finish Buttton
    public void syncOperation(View v)
    {
        new SweetAlertDialog(this).setContentText("What You Want")
                .setConfirmText("Sync Database").setCancelText("Save And Exit").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismiss();
                finishProcess();

            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismiss();
                Intent intent = new Intent(context, AuditSummary.class);
                context.startActivity(intent);

            }
        }).show();
    }
    public  void showAndHide(View v)
    {
        if (isClick) {
            isClick = false;
            Animation bottomUp = new AlphaAnimation(0, 1);
            bottomUp.setInterpolator(new DecelerateInterpolator()); //add this
            bottomUp.setDuration(50);
            layout.startAnimation(bottomUp);
            layout.setVisibility(View.VISIBLE);

        } else {
            isClick = true;
            //hideAnimation();
            Animation bottomDown = new AlphaAnimation(1, 0);
            bottomDown.setInterpolator(new AccelerateInterpolator()); //and this
            bottomDown.setDuration(50);
            layout.startAnimation(bottomDown);
            layout.setVisibility(View.GONE);

        }
    }
    private void finishProcess() {
        int temp = dbHelper.isCompleted(sqlite_DB, userDashboardId);

        if (temp <= 0) {
        if( isSyncPossible())
        {
            Intent intent = new Intent(context, AuditSummary.class);
            context.startActivity(intent);
        }
            else {
            Toast.makeText(getApplicationContext(),"please Write Minimum One Comment",Toast.LENGTH_LONG).show();
        }


        } else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setContentText("You are not attempt All Question.Is Continue.")
                    .setConfirmText("Continue Audit").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismiss();

                }
            }).show();


        }

    }


    public boolean isSyncPossible() {
        Boolean status;
        if (comment_1.getText().length()<0)
        {
            status = false;
        }
        else
        {
            status= true;
        }
        return status;
    }
}
