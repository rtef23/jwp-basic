package next.controller.user;

public class AuthenticationFailException extends RuntimeException{
	public AuthenticationFailException() {
		super();
	}

	public AuthenticationFailException(String message) {
		super(message);
	}

	public AuthenticationFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationFailException(Throwable cause) {
		super(cause);
	}

	protected AuthenticationFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
