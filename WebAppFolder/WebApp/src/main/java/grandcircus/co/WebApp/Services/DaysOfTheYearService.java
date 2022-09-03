package grandcircus.co.WebApp.Services;

import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import grandcircus.co.WebApp.Models.DayEventDataResponse;

@Service
public class DaysOfTheYearService {

	private RestTemplate rt = new RestTemplate();
	//This service needs to be tweaked to allow the headers to give us Authentication. It's getting
	//to the daysoftheyear website, but not getting through (yet)
	
	
	//Not sure if we need this, but left if here in case. Will delete if not needed.
	//Source: https://attacomsian.com/blog/spring-boot-resttemplate-get-request-parameters-headers#get-request-with-parameters-and-headers
	
//		ResponseEntity<String> response = rt.exchange(
//		        url,
//		        HttpMethod.GET,
//		        request,
//		        String.class
//		);

	
	public DayEventDataResponse dayEventDataResponse() {
		//creating headers and setting them up for JSON
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		//setting the authentication for our API key
		headers.setBasicAuth("X-Api-Key", "${apiKey}");
//		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		//setting up the response
		String url = "https://www.daysoftheyear.com/api/v1/today";
		DayEventDataResponse response = rt.getForObject(url, DayEventDataResponse.class);
		
		return response;
	}
}
