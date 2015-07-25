package com.proctor.App.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.proctor.App.Adapter.AuditLocationAdapter;
import com.proctor.App.Adapter.AuditSummaryAdapter;
import com.proctor.App.model.Dashboard;
import com.proctor.App.model.Location;
import com.proctor.R;


import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;

public class AuditSummary extends ActionBarActivity implements AuditSummaryAdapter.ListPostion {

    RecyclerView recyclerView;
    AuditSummaryAdapter adapter;
    SwipeToAction swipeToAction;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_summary);
        context = this;
        setSummaryListView();
    }

    private void setSummaryListView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_summary);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        List<Dashboard> list = Dashboard.listAll(Dashboard.class);
        Log.e("datadadas", String.valueOf(list.size()));

        adapter = new AuditSummaryAdapter(context, list);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audit_summary, menu);
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

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.secondary));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();

    }

    private int removeBook(Dashboard dashboard) {
       /* int pos = books.indexOf(book);
        books.remove(book);
        adapter.notifyItemRemoved(pos);
        */
        //return pos;
        return 1;
    }

    private void addBook(int pos, Dashboard book) {
      /*  books.add(pos, book);
        adapter.notifyItemInserted(pos);
        */
    }


    @Override
    public void clickMyAccess() {

    }
}
