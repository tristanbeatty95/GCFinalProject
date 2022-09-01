package grandcircus.co.WebService.Exceptions;

public class EventCannotBeCreatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EventCannotBeCreatedException() {
		super("Event cannot be created.");
	}

}