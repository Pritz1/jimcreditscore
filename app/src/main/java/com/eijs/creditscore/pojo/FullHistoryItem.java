package com.eijs.creditscore.pojo;

import com.google.gson.annotations.SerializedName;

public class FullHistoryItem{

	@SerializedName("drcd")
	private String drcd;

	@SerializedName("mth")
	private String mth;

	@SerializedName("dailyscore")
	private String dailyscore;

	@SerializedName("entdate")
	private String entdate;

	@SerializedName("netid")
	private String netid;

	@SerializedName("yr")
	private String yr;

	@SerializedName("cntcd")
	private String cntcd;

	@SerializedName("mthscore")
	private String mthscore;

	@SerializedName("ecode")
	private String ecode;

	public void setDrcd(String drcd){
		this.drcd = drcd;
	}

	public String getDrcd(){
		return drcd;
	}

	public void setMth(String mth){
		this.mth = mth;
	}

	public String getMth(){
		return mth;
	}

	public void setDailyscore(String dailyscore){
		this.dailyscore = dailyscore;
	}

	public String getDailyscore(){
		return dailyscore;
	}

	public void setEntdate(String entdate){
		this.entdate = entdate;
	}

	public String getEntdate(){
		return entdate;
	}

	public void setNetid(String netid){
		this.netid = netid;
	}

	public String getNetid(){
		return netid;
	}

	public void setYr(String yr){
		this.yr = yr;
	}

	public String getYr(){
		return yr;
	}

	public void setCntcd(String cntcd){
		this.cntcd = cntcd;
	}

	public String getCntcd(){
		return cntcd;
	}

	public void setMthscore(String mthscore){
		this.mthscore = mthscore;
	}

	public String getMthscore(){
		return mthscore;
	}

	public void setEcode(String ecode){
		this.ecode = ecode;
	}

	public String getEcode(){
		return ecode;
	}

	@Override
 	public String toString(){
		return 
			"FullHistoryItem{" + 
			"drcd = '" + drcd + '\'' + 
			",mth = '" + mth + '\'' + 
			",dailyscore = '" + dailyscore + '\'' + 
			",entdate = '" + entdate + '\'' + 
			",netid = '" + netid + '\'' + 
			",yr = '" + yr + '\'' + 
			",cntcd = '" + cntcd + '\'' + 
			",mthscore = '" + mthscore + '\'' + 
			",ecode = '" + ecode + '\'' + 
			"}";
		}
}