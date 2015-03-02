package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class BooleanFieldFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public BooleanFieldFormatException() {
	}

	public BooleanFieldFormatException(String message) {
		super(message);
	}

	public BooleanFieldFormatException(Throwable cause) {
		super(cause);
	}

	public BooleanFieldFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BooleanFieldFormatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
