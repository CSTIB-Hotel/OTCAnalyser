package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class PFCDFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public PFCDFormatException() {
	}

	public PFCDFormatException(String message) {
		super(message);
	}

	public PFCDFormatException(Throwable cause) {
		super(cause);
	}

	public PFCDFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public PFCDFormatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
