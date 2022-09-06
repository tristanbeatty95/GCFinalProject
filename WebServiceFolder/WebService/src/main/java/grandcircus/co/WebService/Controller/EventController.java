package grandcircus.co.WebService.Controller;

import java.time.LocalDateTime;
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
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
		if (employees != null) {
			return event_repo.findByEmployees(employees);
			// code below needs to be checked because failed to convert value of type
		} else if (startTime != null && endTime != null) {
			List<Event> events = event_repo.findAll();
			List<Event> results = new ArrayList<>();
			// SimpleDateFormat formatter = new
			// SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSXXX\r\n", Locale.ENGLISH);
			LocalDateTime start = LocalDateTime.parse(startTime);
			
			System.out.println(start);
			//LocalDateTime end = LocalDateTime.parse(endTime);
			for (int i = 0; i < events.size(); i++) {
				Event curr = events.get(i);
	
				if (curr.getStart().isAfter(start)) {
					results.add(curr);
					return results;
				}
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
	public Event patchEvent(@PathVariable("id") String id, @RequestParam(required = false) String name,
			@RequestParam(required = false) LocalDateTime start, @RequestParam(required = false) LocalDateTime end,
			@RequestParam(required = false) List<String> employees) {
		Event updatedEvent = event_repo.findById(id).orElseThrow(() -> new EventNotFoundException());
		if (name != null) {
			updatedEvent.setEventName(name);
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

