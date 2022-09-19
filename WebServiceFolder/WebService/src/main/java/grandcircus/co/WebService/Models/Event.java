package grandcircus.co.WebService.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("events")
public class Event {
	@Id
	private String id;
	private String start;
	private String end;
	private String eventName;
	private Double duration;
	private List<String> employees;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	//Getter for time, without the date portion
	public String getStartTime() {
		return this.getStart().substring(11);
	}
	
	//Getter for time, without the date portion
	public String getEndTime() {
		return this.getEnd().substring(11);
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

	public Event(String start, String end, String eventName, List<String> employees, Double duration) {
		this.start = start;
		this.end = end;
		this.eventName = eventName;
		this.employees = employees;
		this.duration = duration;
	}
//Don't use super class method use this one. When using event.tostring it will print whatever is in this method
	@Override
	public String toString() {
		return "Event [start=" + start + ", end=" + end + ", eventName=" + eventName + ", duration=" + duration
				+ ", employees=" + employees + "]";
	}

	public Event() {

	}

}
