package grandcircus.co.WebService.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import grandcircus.co.WebService.Exceptions.EventCannotBeCreatedException;
import grandcircus.co.WebService.Exceptions.EventNotFoundException;
import grandcircus.co.WebService.Models.Event;
import grandcircus.co.WebService.Repo.EventRepo;

@RestController
public class EventController {
	@Autowired
	private EventRepo event_repo;

	@GetMapping("/event")
	public List<Event> getAllEvents(@RequestParam(required = false) String employees,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end) throws ParseException {
		// If the list of employees is a param, return events with those employees
		if (employees != null) {
			return event_repo.findByEmployees(employees);
		
		// Return items within the time frames if they are present
		} else if (start != null && end != null) {
			
			List<Event> events = event_repo.findAll();
			List<Event> results = new ArrayList<>();
			
			for (int i = 0; i < events.size(); i++) {
				Event curr = events.get(i);
				
				//Works for multiple day events as well as single day events
				if((curr.getStart().compareTo(end) <= 0) && (curr.getEnd().compareTo(start) >= 0))
						results.add(curr);
//				if (((curr.getStart().compareTo(startDate) >= 0) && (curr.getEnd().compareTo(endDate) <= 0))
//						|| ((curr.getStart().compareTo(startDate) <= 0) && (curr.getStart().compareTo(endDate) >= 0)))
//					results.add(curr);
			}
			return results;
		} else {
			return event_repo.findAll();
		}
	}

	@GetMapping("/event/{id}")
	public Event getEventById(@PathVariable("id") String id) {
		return event_repo.findById(id).orElseThrow(() -> new EventNotFoundException());
	}
	/*
	 * @GetMapping("/event/{employees}") public List<Event>
	 * getEventByEmployee(@PathVariable("employees") String employees) { return
	 * event_repo.findByEmployees(employees); }
	 */

	@PostMapping("/event")
	@ResponseStatus(HttpStatus.CREATED)
	public Event createEvent(@RequestBody Event event) {
		event_repo.insert(event);
		return event;
	}

	@PutMapping("/event/{id}")
	public Event updateEvent(@RequestBody Event event, @PathVariable("id") String id) {
		event.setId(id);
		return event_repo.save(event);
	}

	@DeleteMapping("/event/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEvent(@PathVariable("id") String id) {
		event_repo.deleteById(id);
	}

	@ResponseBody
	@ExceptionHandler(EventNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String eventNotFoundHandler(EventNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(EventCannotBeCreatedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String eventCannotBeCreatedHandler(EventCannotBeCreatedException ex) {
		return ex.getMessage();
	}

	@PatchMapping("/event/{id}")
	public Event patchEvent(@PathVariable("id") String id, @RequestParam(required = false) String eventName,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			@RequestParam(required = false) List<String> employees) {
		Event updatedEvent = event_repo.findById(id).orElseThrow(() -> new EventNotFoundException());
		if (eventName != null) {
			updatedEvent.setEventName(eventName);
		}
		if (start != null) {
			updatedEvent.setStart(start);
		}
		if (end != null) {
			updatedEvent.setEnd(end);
		}
		if (employees != null) {
			updatedEvent.setEmployees(employees);
		}
		updatedEvent.setId(id);
		return event_repo.save(updatedEvent);
	}

}
