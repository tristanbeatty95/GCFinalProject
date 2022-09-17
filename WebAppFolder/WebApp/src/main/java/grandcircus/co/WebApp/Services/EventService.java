package grandcircus.co.WebApp.Services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
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
	
	public HashMap<String, ArrayList<Event>> getEventsByTimeRange(String start, String end){
		String url = "http://localhost:8080/event/" + start + "/" + end;
		
		ParameterizedTypeReference<HashMap<String, ArrayList<Event>>> responseType = 
				new ParameterizedTypeReference<HashMap<String, ArrayList<Event>>>(){};
		
		RequestEntity<Void> request = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
		
		HashMap<String, ArrayList<Event>> response = restTemplate.exchange(request, responseType).getBody();
		return response;
	}

	public Event addNewEvent(Event event) {
		return restTemplate.postForObject("http://localhost:8080/event", event, Event.class);	
	}
	
	public void deleteEvent(String id) {
		String url = "http://localhost:8080/event/" + id;
		restTemplate.delete(url);
	}
	
	public Event[] getAllEvents() {
		String url = "http://localhost:8080/event/";
		Event[] response = restTemplate.getForObject(url, Event[].class);
		return response;
	}
	
	public void updateEvent(String id, Event event) {
		String url = "http://localhost:8080/event" + "/{id}";
		restTemplate.put(url, event, id);
	}

}
