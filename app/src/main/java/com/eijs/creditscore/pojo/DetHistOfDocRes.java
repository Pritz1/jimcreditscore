package com.eijs.creditscore.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetHistOfDocRes{

	@SerializedName("FullHistory")
	private List<FullHistoryItem> fullHistory;

	public void setFullHistory(List<FullHistoryItem> fullHistory){
		this.fullHistory = fullHistory;
	}

	public List<FullHistoryItem> getFullHistory(){
		return fullHistory;
	}

	@Override
 	public String toString(){
		return 
			"DetHistOfDocRes{" + 
			"fullHistory = '" + fullHistory + '\'' + 
			"}";
		}
}