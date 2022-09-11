package grandcircus.co.WebService.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import grandcircus.co.WebService.Exceptions.EventCannotBeCreatedException;
import grandcircus.co.WebService.Models.Event;
import grandcircus.co.WebService.Repo.EventRepo;

@RestController
public class ScheduleController {
	
	@Autowired
	private EventRepo event_repo;

	@ResponseBody
	@ExceptionHandler(EventCannotBeCreatedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String eventCannotBeCreatedHandler(EventCannotBeCreatedException ex) {
		return ex.getMessage();
	}
	

	@GetMapping("/schedule")
	public List<String> scheduleFinder(List<String> employees, String start, String end){
	List<Event> events = event_repo.findAll();	
	List<String> availableTime = new ArrayList<>();
	LocalDateTime startDate = LocalDateTime.parse(start);
	LocalDateTime endDate = LocalDateTime.parse(end);
	System.out.println("Got here!");
	
	for(Event e : events) {
		
		if (startDate.isAfter(LocalDateTime.parse(e.getStartTime()))  && startDate.isBefore(LocalDateTime.parse(e.getEndTime()))) {
			//suggest times
			availableTime.add("Yup");
			System.out.println("Added 1 ");
			
		} else if (endDate.isAfter(LocalDateTime.parse(e.getStartTime()))  && endDate.isBefore(LocalDateTime.parse(e.getEndTime()))) {
			//suggest times
			availableTime.add("Yup2");
			System.out.println("Added 1 2 ");
			
		} else {
			System.out.println("This time works fine and doesn't conflict with " + e.getEventName());
		}
	}

	
	return availableTime;
	
	
	
	
	
	
	
	
	
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

