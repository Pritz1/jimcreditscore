package com.eijs.creditscore.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListOfEmpRes{

	@SerializedName("emplist")
	private List<EmplistItem> emplist;

	public void setEmplist(List<EmplistItem> emplist){
		this.emplist = emplist;
	}

	public List<EmplistItem> getEmplist(){
		return emplist;
	}

	@Override
 	public String toString(){
		return 
			"ListOfEmpRes{" + 
			"emplist = '" + emplist + '\'' + 
			"}";
		}
}