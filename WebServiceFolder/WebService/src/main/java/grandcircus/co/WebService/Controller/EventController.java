package grandcircus.co.WebService.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import grandcircus.co.WebService.Exceptions.EventNotFoundException;
import grandcircus.co.WebService.Models.Event;
import grandcircus.co.WebService.Repo.EventRepo;

@RestController
public class EventController {
	@Autowired
	private EventRepo event_repo;
	
	@GetMapping("/event")
	public List<Event> getAllEvents(){
		return event_repo.findAll();
	}
	@GetMapping("/event/{id}")
	public Event getEventById(@PathVariable("id") String id) {
		return event_repo.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}
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
	String characterNotFoundHandler(EventNotFoundException ex) {
		return ex.getMessage();
	}
	
	@PatchMapping("/event/{id}")
	public Event patchEvent(@PathVariable("id") String id, @RequestBody Event event, @RequestParam(required=false) String name, 
			@RequestParam(required=false) Date start,
			@RequestParam(required=false) Date end,
			@RequestParam(required=false) List<String> employees) {
		Event updatedEvent = event_repo.findById(id).orElseThrow(()-> new EventNotFoundException(id));
		if(name!=null) {
			updatedEvent.setEventName(name);
		}
		if(start!=null) {
			updatedEvent.setStart(start);
		}
		if(end!=null) {
			updatedEvent.setEnd(end);
		}
		if(employees!=null) {
			updatedEvent.setEmployees(employees);
		}
		updatedEvent.setId(id);
		return event_repo.save(updatedEvent);
	}

}
