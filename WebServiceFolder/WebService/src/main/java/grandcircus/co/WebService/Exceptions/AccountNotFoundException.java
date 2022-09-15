package grandcircus.co.WebService.Exceptions;

public class AccountNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AccountNotFoundException() {
		super("Could not find account");
	}
}
