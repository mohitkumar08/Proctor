package com.proctor.App.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.helpshift.Helpshift;
import com.proctor.R;

public class HelpShiftIntegration extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_shift_integration);
        Helpshift.install(getApplication(),
                "2d3f51674b365d9d2bce1d70c04d15b5", // API Key
                "buildemployee.helpshift.com", // Domain Name
                "buildemployee_platform_20150721040826555-3ae634fba280a8d");


        Button showHelpBtn = (Button) findViewById(R.id.showHelpBtn);

        showHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helpshift.showConversation(HelpShiftIntegration.this);
                // Where MyActivity.this is the Activity you're calling Helpshift from

            }
        });


        Button reportIssueButton = (Button) findViewById(R.id.reportIssueButton);
        reportIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helpshift.showConversation(HelpShiftIntegration.this);
            }
        });


        Button showfaq = (Button) findViewById(R.id.showfaq);
        showfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helpshift.showFAQs(HelpShiftIntegration.this);
            }
        });


        Button faqSectionButton = (Button) findViewById(R.id.faqSectionButton);
        faqSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helpshift.showFAQSection(HelpShiftIntegration.this, "8");
            }
        });

        Button singleFaqButton = (Button) findViewById(R.id.singleFaqButton);
        singleFaqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helpshift.showSingleFAQ(HelpShiftIntegration.this, "50");
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_shift_integration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
