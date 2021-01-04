package cyanide3d.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMemberDefault{

	@JsonProperty("width")
	private int width;

	@JsonProperty("url")
	private String url;

	@JsonProperty("height")
	private int height;

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}
}