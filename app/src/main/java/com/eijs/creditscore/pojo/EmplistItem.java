package com.eijs.creditscore.pojo;

import com.google.gson.annotations.SerializedName;

public class EmplistItem{

	@SerializedName("divcd")
	private String divcd;

	@SerializedName("level5")
	private String level5;

	@SerializedName("level4")
	private String level4;

	@SerializedName("level6")
	private String level6;

	@SerializedName("netid")
	private String netid;

	@SerializedName("jndate")
	private String jndate;

	@SerializedName("ecode")
	private String ecode;

	@SerializedName("ename")
	private String ename;

	@SerializedName("grade")
	private String grade;

	@SerializedName("etype")
	private String etype;

	@SerializedName("level1")
	private String level1;

	@SerializedName("hname")
	private String hname;

	@SerializedName("level3")
	private String level3;

	@SerializedName("level2")
	private String level2;

	public void setDivcd(String divcd){
		this.divcd = divcd;
	}

	public String getDivcd(){
		return divcd;
	}

	public void setLevel5(String level5){
		this.level5 = level5;
	}

	public String getLevel5(){
		return level5;
	}

	public void setLevel4(String level4){
		this.level4 = level4;
	}

	public String getLevel4(){
		return level4;
	}

	public void setLevel6(String level6){
		this.level6 = level6;
	}

	public String getLevel6(){
		return level6;
	}

	public void setNetid(String netid){
		this.netid = netid;
	}

	public String getNetid(){
		return netid;
	}

	public void setJndate(String jndate){
		this.jndate = jndate;
	}

	public String getJndate(){
		return jndate;
	}

	public void setEcode(String ecode){
		this.ecode = ecode;
	}

	public String getEcode(){
		return ecode;
	}

	public void setEname(String ename){
		this.ename = ename;
	}

	public String getEname(){
		return ename;
	}

	public void setGrade(String grade){
		this.grade = grade;
	}

	public String getGrade(){
		return grade;
	}

	public void setEtype(String etype){
		this.etype = etype;
	}

	public String getEtype(){
		return etype;
	}

	public void setLevel1(String level1){
		this.level1 = level1;
	}

	public String getLevel1(){
		return level1;
	}

	public void setHname(String hname){
		this.hname = hname;
	}

	public String getHname(){
		return hname;
	}

	public void setLevel3(String level3){
		this.level3 = level3;
	}

	public String getLevel3(){
		return level3;
	}

	public void setLevel2(String level2){
		this.level2 = level2;
	}

	public String getLevel2(){
		return level2;
	}

	@Override
 	public String toString(){
		return 
			"EmplistItem{" + 
			"divcd = '" + divcd + '\'' + 
			",level5 = '" + level5 + '\'' + 
			",level4 = '" + level4 + '\'' + 
			",level6 = '" + level6 + '\'' + 
			",netid = '" + netid + '\'' + 
			",jndate = '" + jndate + '\'' + 
			",ecode = '" + ecode + '\'' + 
			",ename = '" + ename + '\'' + 
			",grade = '" + grade + '\'' + 
			",etype = '" + etype + '\'' + 
			",level1 = '" + level1 + '\'' + 
			",hname = '" + hname + '\'' + 
			",level3 = '" + level3 + '\'' + 
			",level2 = '" + level2 + '\'' + 
			"}";
		}
}