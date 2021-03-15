package cyanide3d.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cyanide3d.model.json.High;
import cyanide3d.model.json.JsonMemberDefault;
import cyanide3d.model.json.Medium;

public class Thumbnails{

	@JsonProperty("default")
	private JsonMemberDefault jsonMemberDefault;

	@JsonProperty("high")
	private High high;

	@JsonProperty("medium")
	private Medium medium;

	public void setJsonMemberDefault(JsonMemberDefault jsonMemberDefault){
		this.jsonMemberDefault = jsonMemberDefault;
	}

	public JsonMemberDefault getJsonMemberDefault(){
		return jsonMemberDefault;
	}

	public void setHigh(High high){
		this.high = high;
	}

	public High getHigh(){
		return high;
	}

	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}
}