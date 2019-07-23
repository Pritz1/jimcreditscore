package com.eijs.creditscore.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EntDocListRes{

	@SerializedName("DoEntDocList")
	private List<DoEntDocListItem> doEntDocList;

	public void setDoEntDocList(List<DoEntDocListItem> doEntDocList){
		this.doEntDocList = doEntDocList;
	}

	public List<DoEntDocListItem> getDoEntDocList(){
		return doEntDocList;
	}

	@Override
 	public String toString(){
		return 
			"EntDocListRes{" + 
			"doEntDocList = '" + doEntDocList + '\'' + 
			"}";
		}
}