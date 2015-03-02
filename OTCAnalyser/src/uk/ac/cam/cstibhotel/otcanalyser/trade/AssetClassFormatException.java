package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class AssetClassFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public AssetClassFormatException() {
	}

	public AssetClassFormatException(String message) {
		super(message);
	}

	public AssetClassFormatException(Throwable cause) {
		super(cause);
	}

	public AssetClassFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssetClassFormatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
