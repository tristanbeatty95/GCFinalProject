package grandcircus.co.WebApp.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventService {
	private RestTemplate restTemplate = new RestTemplate();
	
	public Event getThatEvent(String id) {
		String url = "";
		Event thatEvent = restTemplate.getForObject(url, Event.class, id);
		return thatEvent;
	}
	
	
}
