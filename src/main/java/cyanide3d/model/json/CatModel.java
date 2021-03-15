package cyanide3d.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatModel{

	@JsonProperty("width")
	private int width;

	@JsonProperty("id")
	private String id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("breeds")
	private List<Object> breeds;

	@JsonProperty("height")
	private int height;

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setBreeds(List<Object> breeds){
		this.breeds = breeds;
	}

	public List<Object> getBreeds(){
		return breeds;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
	public String toString() {
		return getUrl();
	}
}