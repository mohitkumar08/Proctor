package com.proctor.App.model;

import com.orm.SugarRecord;
import com.google.gson.annotations.SerializedName;
/**
 * Created by MOHIT KUMAR on 7/14/2015.
 */
public class Inspection extends SugarRecord {

    public String category;
    public String questionname;
    public String photopath;
    public String isphoto;
    public int score;
    public int attempt;
    public int dashboardid;
    public int ischecked;

    public  Inspection()
    {}
    public Inspection(String category, String questionname, String photopath, String isphoto, int score, int attempt, int dashboard,int isChecked) {
        this.category = category;
        this.questionname = questionname;
        this.photopath = photopath;
        this.isphoto = isphoto;
        this.score = score;
        this.attempt = attempt;
        this.dashboardid = dashboard;
        this.ischecked = isChecked;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public String getIsphoto() {
        return isphoto;
    }

    public void setIsphoto(String isphoto) {
        this.isphoto = isphoto;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int getDashboard() {
        return dashboardid;
    }

    public void setDashboardid(int dashboardid) {
        this.dashboardid = dashboardid;
    }

    public int getIschecked() {
        return ischecked;
    }

    public void setIschecked(int ischecked) {
        this.ischecked = ischecked;
    }

}
