package grandcircus.co.WebApp.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayEvent {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("excerpt")
	private String excerpt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	
	public DayEvent() {
		
	}
	
	public DayEvent(String name, String url, String excerpt) {
		this.name = name;
		this.url = url;
		this.excerpt = excerpt;
	}

	@Override
	public String toString() {
		return name;
	}

}
