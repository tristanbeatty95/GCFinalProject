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

}
