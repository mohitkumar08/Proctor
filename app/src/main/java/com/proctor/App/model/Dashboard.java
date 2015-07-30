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
	public int totalscore;
	public int imei;
	public int latitude;
	public int longitude;
	public int locationid;
	public int userid;
	public int audittypeid;

	public Dashboard() {
	}

	public Dashboard( String username, String type, String locationame, String createdate, String modifiedate, int status, int totalscore, int imei, int latitude, int longitude, int locationid, int userid, int audittypeid ) {

		this.username = username;
		this.type = type;
		this.locationame = locationame;
		this.createdate = createdate;
		this.modifiedate = modifiedate;
		this.status = status;
		this.totalscore = totalscore;
		this.imei = imei;
		this.latitude = latitude;
		this.longitude = longitude;
		this.locationid = locationid;
		this.userid = userid;
		this.audittypeid = audittypeid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername( String username ) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}


	public String getLocationame() {
		return locationame;
	}

	public void setLocationame( String locationame ) {
		this.locationame = locationame;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate( String createdate ) {
		this.createdate = createdate;
	}

	public String getModifiedate() {
		return modifiedate;
	}

	public void setModifiedate( String modifiedate ) {
		this.modifiedate = modifiedate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus( int status ) {
		this.status = status;
	}


	public int getTotalscore() {
		return totalscore;
	}

	public void setTotalscore( int totalscore ) {
		this.totalscore = totalscore;
	}

	public void setImei( int imei ) {
		this.imei = imei;
	}

	public int getImei() {
		return this.imei;
	}

	public void setLatitude( int latitude ) {
		this.latitude = latitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLongitude( int longitude ) {
		this.longitude = longitude;
	}

	public int getLongitude() {
		return longitude;
	}


	public void setUserid( int userid ) {
		this.userid = userid;
	}

	public int getUserid() {
		return userid;
	}


	public void setLocationid( int locationid ) {
		this.locationid = locationid;
	}

	public int getLocationid() {
		return locationid;
	}


	public void setAudittypeid( int audittypeid ) {
		this.audittypeid = audittypeid;
	}

	public int getAudittypeid() {
		return audittypeid;
	}

}

