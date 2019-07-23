package com.eijs.creditscore.pojo;

import com.google.gson.annotations.SerializedName;

public class IsUpdateReqRes{

	@SerializedName("link")
	private String link;

	@SerializedName("version")
	private String version;

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}

	@Override
 	public String toString(){
		return 
			"IsUpdateReqRes{" + 
			"link = '" + link + '\'' + 
			",version = '" + version + '\'' + 
			"}";
		}
}