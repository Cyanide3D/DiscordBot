package cyanide3d.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Id{

	@JsonProperty("kind")
	private String kind;

	@JsonProperty("videoId")
	private String videoId;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setVideoId(String videoId){
		this.videoId = videoId;
	}

	public String getVideoId(){
		return videoId;
	}
}