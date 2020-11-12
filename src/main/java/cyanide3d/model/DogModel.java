package cyanide3d.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DogModel{

	@JsonProperty("message")
	private String message;

	@JsonProperty("status")
	private String status;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}