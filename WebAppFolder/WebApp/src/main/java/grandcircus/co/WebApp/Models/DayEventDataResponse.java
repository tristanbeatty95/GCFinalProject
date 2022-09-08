package grandcircus.co.WebApp.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayEventDataResponse {

	@JsonProperty("data")
	private DayEvent[] data;

	public DayEvent[] getData() {
		return data;
	}

	public void setData(DayEvent[] data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return data.getClass().getName();
	}
	
}

