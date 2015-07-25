package com.proctor.App.model;

import com.orm.SugarRecord;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class Audit extends SugarRecord {

    public String name;

    public Audit() {
    }

    public Audit(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

