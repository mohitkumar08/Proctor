package com.proctor.App.model;

import com.orm.SugarRecord;

/**
 * Created by MOHIT KUMAR on 7/14/2015.
 */
public class Dashboard extends SugarRecord {

    public String username;
    public String type;
    public String locationame;
    public String createdate;
    public String modifiedate;
    public int status;
    public  int totalscore;

    public Dashboard(){}
    public Dashboard(String username, String type, String locationame, String createdate, String modifiedate, int status,int totalscore) {
        this.username = username;
        this.type = type;
        this.locationame = locationame;
        this.createdate = createdate;
        this.modifiedate = modifiedate;
        this.status = status;
        this.totalscore = totalscore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getLocationame() {
        return locationame;
    }

    public void setLocationame(String locationame) {
        this.locationame = locationame;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getModifiedate() {
        return modifiedate;
    }

    public void setModifiedate(String modifiedate) {
        this.modifiedate = modifiedate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

}
