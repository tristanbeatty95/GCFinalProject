package grandcircus.co.WebService.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import grandcircus.co.WebService.Exceptions.EventCannotBeCreatedException;
import grandcircus.co.WebService.Models.Event;
import grandcircus.co.WebService.Repo.EventRepo;

public class ScheduleController {
	@Autowired
	private EventRepo event_repo;

	@ResponseBody
	@ExceptionHandler(EventCannotBeCreatedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String eventCannotBeCreatedHandler(EventCannotBeCreatedException ex) {
		return ex.getMessage();
	}
	

	public List<String> scheduleFinder(List<String> employees, String start, String end, Double duration){
	List<Event> events = event_repo.findAll();	
	List<String> availableTime = new ArrayList<>();
	HashMap<String, String> slots = new HashMap<>();
	for (int i = 0; i < events.size(); i++) {
		Event curr = events.get(i); 
		if (curr.getStart().compareTo(start) < 0 && curr.getEnd().compareTo(start) < 0)  {
			slots.put(start, end);
		}
		if(curr.getStart().compareTo(end) > 0) {
			slots.put(start, end);
		}
		
	}
	return null;
	
	
	
	
	
	
	
	
	
//	LocalDateTime startTime = LocalDateTime.parse(start);
//	LocalDateTime endTime = LocalDateTime.parse(end);
//	HashMap<LocalDateTime, Boolean> slots = new HashMap<>();
//	//LocalDateTime time = LocalDateTime.of
//	while(startTime.isBefore(endTime)) {
//		if(!slots.get(startTime) || !slots.get(endTime)) {
//			return availableTime;
//		}
//		 startTime = startTime.plusHours(1);
//         endTime = endTime.minusHours(1);
//	}
//	while (startTime.isBefore(endTime)) {
//        slots.put(startTime, false);
//        slots.put(endTime, false);
//        startTime = startTime.plusHours(1);
//        endTime = endTime.minusHours(1);
//    }
//	return availableTime;
//	}

}
}

