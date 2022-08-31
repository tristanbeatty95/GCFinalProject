package grandcircus.co.WebApp.Models;

import java.util.Date;
import java.util.List;

public class Event {
	private String id;
	private Date start;
	private Date end;
	private String eventName;
	private Double duration;
	private List<String> employees;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
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

	public Event(Date start, Date end, String eventName, List<String> employees, Double duration) {

		this.start = start;
		this.end = end;
		this.eventName = eventName;
		this.employees = employees;
	}

	public Event() {
		
	}

}

