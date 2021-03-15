package cyanide3d.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemsItem{

	@JsonProperty("snippet")
	private Snippet snippet;

	@JsonProperty("kind")
	private String kind;

	@JsonProperty("etag")
	private String etag;

	@JsonProperty("id")
	private Id id;

	public void setSnippet(Snippet snippet){
		this.snippet = snippet;
	}

	public Snippet getSnippet(){
		return snippet;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setEtag(String etag){
		this.etag = etag;
	}

	public String getEtag(){
		return etag;
	}

	public void setId(Id id){
		this.id = id;
	}

	public Id getId(){
		return id;
	}
}