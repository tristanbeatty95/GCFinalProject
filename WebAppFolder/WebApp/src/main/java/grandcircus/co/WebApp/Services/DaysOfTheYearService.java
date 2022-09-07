package grandcircus.co.WebApp.Services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import grandcircus.co.WebApp.Models.DayEvent;
import grandcircus.co.WebApp.Models.DayEventDataResponse;

@Service
public class DaysOfTheYearService {

	private RestTemplate rt = new RestTemplate();
	
	@Value("${apiKey}")
	private String apiKey;
	
	public ResponseEntity<DayEvent> dayEventDataResponse() {
		
		String url = "https://www.daysoftheyear.com/api/v1/today?limit=1";
	
		//creating headers and setting them up for JSON
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		//setting the authentication for our API key
		headers.set("x-api-key", apiKey);
		
		HttpEntity<DayEvent> request = new HttpEntity<DayEvent>(headers);
		
		ResponseEntity<DayEvent> response = rt.exchange(
				url,
				HttpMethod.GET,
				request,
				DayEvent.class
				);
		if (response.getStatusCode() == HttpStatus.OK) {
		    System.out.println("Request Successful.");
		} else {
		    System.out.println("Request Failed");
		    System.out.println(response.getStatusCode());
		}		
		
		return response;
	}
}
