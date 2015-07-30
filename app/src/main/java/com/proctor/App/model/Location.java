package com.proctor.App.model;

import com.orm.SugarRecord;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class Location extends SugarRecord {

	public String name;
	public int score;
	public String by;
	public String date;
	public String audit;
	public int locid;

	public Location() {
	}

	public Location( String name, int score, String by, String date, String audit, int locid ) {
		this.name = name;
		this.score = score;
		this.by = by;
		this.date = date;
		this.audit = audit;
		this.locid = locid;

	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore( int type ) {
		this.score = score;
	}

	public String getBy() {
		return by;
	}

	public void setBy( String by ) {
		this.by = by;
	}

	public String getDate() {
		return date;
	}

	public void setDate( String date ) {
		this.date = date;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit( String audit ) {
		this.audit = audit;
	}

	public int getLocid() {
		return this.locid;
	}

	public void setLocid( int locid ) {
		this.locid = locid;
	}

}

