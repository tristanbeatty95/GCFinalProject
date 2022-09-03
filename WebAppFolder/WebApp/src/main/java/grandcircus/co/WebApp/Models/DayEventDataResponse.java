package grandcircus.co.WebApp.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayEventDataResponse {

	@JsonProperty("data")
	private DayEvent[] dayEvents;

	public DayEvent[] getDayEvents() {
		return dayEvents;
	}

	public void setDayEvents(DayEvent[] dayEvents) {
		this.dayEvents = dayEvents;
	}
	
	
}
