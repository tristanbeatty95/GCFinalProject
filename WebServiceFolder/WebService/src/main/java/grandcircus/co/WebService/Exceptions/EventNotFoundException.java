package grandcircus.co.WebService.Exceptions;

public class EventNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EventNotFoundException(String id) {
		super("Could not find Event with ID " + id);
	}

}
