package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class CollateralizationFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public CollateralizationFormatException() {
	}

	public CollateralizationFormatException(String message) {
		super(message);
	}

	public CollateralizationFormatException(Throwable cause) {
		super(cause);
	}

	public CollateralizationFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public CollateralizationFormatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
