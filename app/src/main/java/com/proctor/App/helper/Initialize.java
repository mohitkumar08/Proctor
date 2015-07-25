package com.proctor.App.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.proctor.App.database.DatabaseHelper;
import com.proctor.App.model.Dashboard;
import com.proctor.App.model.Inspection;
import com.proctor.App.model.Location;
import com.proctor.R;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class Initialize  {

    private Context context;
    protected  DatabaseHelper dbHelper;
    protected  SQLiteDatabase sqlite_DB;
    ProgressView progressView;


    public Initialize(Context context, DatabaseHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public void startOperation()
    {


    /*    LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.location_card_view, viewGroup, false);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.activity_splash,
                (ViewGroup) findViewById(R.id.progrssBar));
        ImageView image = (ImageView) layout.findViewById(R.id.layout_root1);
*/


        new StartApp().execute();
    }
    public class StartApp extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            Log.e("Pre Execuate","request");

        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e("start","request");
                sqlite_DB = dbHelper.getWritableDatabase();
                dbHelper.onCreate(sqlite_DB);
				dbHelper.close();
                Temp tmp = new Temp();
                tmp.setLocation();
                tmp.setQuestion();
                tmp.setType();

                /*
                ArrayList<Location> location = (ArrayList<Location>) Select.from(Location.class).where(Condition.prop("audit").eq("shop")).list();
             //   Log.e("datatataaa",String.valueOf(location.size()));


              Select data = Select.from(Dashboard.class).
                        where(Condition.prop("username").eq("mohit"), Condition.prop("type").eq("shop"), Condition.prop("locationame").eq("Iffco Chowk"));




                List<Location> datata = Location.find(Location.class,"(name=? AND score=?)","DLF","90");
                Log.e("datatataaa",String.valueOf(datata.size()));

                List<Dashboard> dalldata = Dashboard.listAll(Dashboard.class);
                for(int i=0;i<dalldata.size();i++)
                {
                    Log.e("username",String.valueOf(dalldata.get(i).getUsername()));
                    Log.e("plocatiom",String.valueOf(dalldata.get(i).getLocationame()));
                    Log.e("type",String.valueOf(dalldata.get(i).getType()));
                }


                List<Inspection> datat111a = Inspection.findWithQuery(Inspection.class, "SELECT * from INSPECTION");
                Log.e("datatataaa",String.valueOf(datat111a.size()));
                Set<Inspection> uniqueGas = new HashSet<Inspection>(datat111a);
                System.out.println("Unique gas count: " + uniqueGas.size());
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
            Log.e("update","request");
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                Log.e("finish","request");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
