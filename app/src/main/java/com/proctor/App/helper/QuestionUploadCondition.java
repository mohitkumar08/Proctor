package com.proctor.App.helper;

import android.util.Log;

import com.proctor.App.model.Dashboard;
import com.proctor.App.model.Inspection;
import com.proctor.App.model.Question;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by MOHIT KUMAR on 7/14/2015.
 */
public class QuestionUploadCondition {
    public QuestionUploadCondition() {
    }

    public List<Inspection> getListOfQuestion(String username, String type_name, String location_name) {

        List<Inspection> returnData = null;
        try {
            Log.e("valuess", username + " " + type_name + " " + location_name);
            List<Dashboard> data = Dashboard.find(Dashboard.class, "(username=? AND type=? AND locationame=?)", username, type_name, location_name);

            Log.e("data is ", "what");
            if (data.isEmpty()) {
                Log.e("yes empty ", "empty");
                //String timeStamp = new SimpleDateFormat("yyyy-MM-dd ").format(Calendar.getInstance().getTime());
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                List<Question> ques_data = Question.find(Question.class, "(audittype=?)", type_name.toLowerCase());

                Dashboard dashBoard_data = new Dashboard(username, type_name, location_name, timeStamp, timeStamp, 0,100);
                Long id = dashBoard_data.save();
                Log.e("id did", String.valueOf(ques_data.size()));

                for (int i = 0; i < ques_data.size(); i++) {

                    Inspection inspection = new Inspection(ques_data.get(i).getCategoryName(), ques_data.get(i).getQuestionText(), null, ques_data.get(i).getPhoto(), ques_data.get(i).getScore(), 2, Integer.valueOf(id.intValue()),1);
                    inspection.save();
                }

                returnData = Inspection.find(Inspection.class, "dashboardid=?", String.valueOf(id));
                //  getCategoryType(returnData);
                Log.e("size of return data", String.valueOf(returnData.size()));
            } else {
                String id = "0";
                for (Dashboard obj : data) {
                  id = String.valueOf(obj.getId());
                }
                Log.e("else mai aaya ", "else");
                returnData = Inspection.find(Inspection.class, "dashboardid=?", String.valueOf(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnData;

    }

    public void getCategoryType(List<Inspection> cat) {
        for (Inspection dashboard : cat) {
            Log.e("categoryyy ", dashboard.category);
        }
    }

    public String getDashboardId(String username, String type_name, String location_name) {
        String id = null;
        List<Dashboard> data = Dashboard.find(Dashboard.class, "(username=? AND type=? AND locationame=? AND status=?)", username, type_name, location_name, "0");
        for (Dashboard obj : data) {
            id = String.valueOf(obj.getId());
        }

        return id;
    }

    public  List<Inspection> getQuestionList(int id,String category)
    {
        List<Inspection> data= Inspection.find(Inspection.class, "dashboardid=? AND category=?", String.valueOf(id),category);
        return  data;
    }

    public  void setQuestionStatus(String id,Boolean check,String dashboardId,int score)
    {
        Inspection inspection = Inspection.findById(Inspection.class,Integer.parseInt(id));

        if(check) {
            inspection.attempt = 0;
            inspection.ischecked = 0;

        }
        else {
            inspection.attempt = 1;
            inspection.ischecked = 0;

        }
    inspection.save();
        setDashboardScore(dashboardId,score);

    }
    public  void setDashboardScore(String dashboardId,int score)
    {
        Dashboard dashboard = Dashboard.findById(Dashboard.class,Integer.parseInt(dashboardId));
        dashboard.totalscore = score;
        dashboard.save();

    }
    public  void setScoreDashboard(int id,int status)
    {
        Dashboard dashboard = Dashboard.findById(Dashboard.class,id);
        dashboard.status = status;
        dashboard.save();

    }

    public  int  getScoreDashboard(int id)
    {
        int scorereturn;
        Dashboard dashboard = Dashboard.findById(Dashboard.class,id);
        scorereturn= dashboard.totalscore;
        return scorereturn;
    }
    public  Dashboard  getDashboard(int id)
    {

        Dashboard dashboard = Dashboard.findById(Dashboard.class,id);
        return  dashboard;
    }
    public List<Inspection> getAllInspectionObject(int id)
    {

        List<Inspection> inspections = Inspection.find(Inspection.class,"dashboardid=? ",String.valueOf(id));
        return inspections;
//       Long lo =   Inspection.count(Inspection.class,"dashboardid=? AND category=?",category);



    }

}