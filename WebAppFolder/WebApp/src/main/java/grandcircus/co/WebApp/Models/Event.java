package grandcircus.co.WebApp.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Event {
	private String id;
	private String start;
	private String end;
	private String eventName;
	private Double duration;
	private List<String> employees;
	private String employeesString;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Getter for time, without the date portion
	public String getStartTime() {
		LocalDateTime startString = LocalDateTime.parse(start);
		return startString.format(DateTimeFormatter.ofPattern("hh:mm a"));
	}

	// Getter for time, without the date portion
	public String getEndTime() {
		LocalDateTime endString = LocalDateTime.parse(end);
		return endString.format(DateTimeFormatter.ofPattern("hh:mm a"));
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
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
	
	

	public String getEmployeesString() {
		employeesString = employees.toString().substring(1, employees.toString().length()-1);
		return employeesString;
	}

	public void setEmployeesString(String employeesString) {
		this.employeesString = employeesString;
	}

	public Event(String start, String end, String eventName, List<String> employees, Double duration) {
		this.duration = duration;
		this.start = start;
		this.end = end;
		this.eventName = eventName;
		this.employees = employees;
	}

	public Event() {

	}

	@Override
	public String toString() {
		return "Event [start=" + start + ", end=" + end + ", eventName=" + eventName + ", duration=" + duration
				+ ", employees=" + employees + "]";
	}

}
