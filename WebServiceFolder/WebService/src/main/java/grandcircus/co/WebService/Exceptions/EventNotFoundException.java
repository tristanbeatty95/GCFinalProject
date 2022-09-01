package grandcircus.co.WebService.Exceptions;

public class EventNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EventNotFoundException() {
		super("Could not find Event");
	}

}
