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
	public HttpEntity formatRequest() {
		//creating headers and setting them up for JSON
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		//setting the authentication for our API key
		headers.set("X-Api-Key", apiKey);
		
		HttpEntity request = new HttpEntity<>(headers);
		return request;
	}

	public DayEvent[] getTodayEvent() {
		String url = "https://www.daysoftheyear.com/api/v1/today";
		DayEventDataResponse response = rt.exchange(url, HttpMethod.GET, formatRequest(), 
				DayEventDataResponse.class).getBody();
		return response.getData();
	}
	
	public DayEvent[] getMonthEvents(String year, String month) {
		String url = "https://www.daysoftheyear.com/api/v1/date/" + year + "/" + month + "/";
		DayEventDataResponse response = rt.exchange(url, HttpMethod.GET, formatRequest(), 
				DayEventDataResponse.class).getBody();
		return response.getData();
	}
	
	public DayEvent[] getSpecificDateEvents(String year, String month, String day) {
		String url = "https://www.daysoftheyear.com/api/v1/date/" + year + "/" + month + "/" + day + "/";
		DayEventDataResponse response = rt.exchange(url, HttpMethod.GET, formatRequest(), 
				DayEventDataResponse.class).getBody();
		return response.getData();
	}

}
