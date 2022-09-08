package grandcircus.co.WebApp.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import grandcircus.co.WebApp.Models.Event;

//We can add methods to the service as we require them when working in the controller
@Service
public class EventService {
	private RestTemplate restTemplate = new RestTemplate();

	public Event getEventById(String id) {
		// !!URL will change when project is hosted on AWS!!
		String url = "http://localhost:8080/event/" + id;
		Event thatEvent = restTemplate.getForObject(url, Event.class, id);
		return thatEvent;
	}
	
	public Event[] getEventsByTimeRange(String start, String end){
		String url = "http://localhost:8080/event?start=" + start + "&end=" + end;
		Event[] response = restTemplate.getForObject(url, Event[].class);
		return response;
	}

	public Event addNewEvent(Event event) {
		return restTemplate.postForObject("http://localhost:8080/event", event, Event.class);	
	}
	

}
