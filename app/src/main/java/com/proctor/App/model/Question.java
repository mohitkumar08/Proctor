package com.proctor.App.model;

import com.orm.SugarRecord;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class Question extends SugarRecord{
    protected  String audittype;
    protected  String categoryname;
    protected String questiontext;
    protected String photo;
    protected int score;
    protected int questionsequence;


    public  Question(){}

    public Question(String auditType,String categoryName,String questionText,String photo, int score,int questionSequence) {
        this.audittype = auditType;
        this.categoryname = categoryName;
        this.questiontext = questionText;
        this.photo = photo;
        this.score = score;
        this.questionsequence = questionSequence;
    }


    public void setAuditType(String auditType) {
        this.audittype = auditType;
    }

    public String getAuditType() {
        return this.audittype;
    }

    public void setQuestionText(String questionText) {
        this.questiontext = questionText;
    }

    public String getQuestionText() {
        return this.questiontext;
    }

    public void setCategoryName(String categoryName) {
        this.categoryname = categoryName;
    }

    public String getCategoryName() {
        return this.categoryname;
    }



    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }


    public void setQuestionSequence(int questionSequence) {
        this.questionsequence = questionSequence;
    }

    public int getQuestionSequence() {
        return this.questionsequence;
    }


}
