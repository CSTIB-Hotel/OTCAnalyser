package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class EmptyTaxonomyException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyTaxonomyException() {
	}

	public EmptyTaxonomyException(String message) {
		super(message);
	}

	public EmptyTaxonomyException(Throwable cause) {
		super(cause);
	}

	public EmptyTaxonomyException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyTaxonomyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
