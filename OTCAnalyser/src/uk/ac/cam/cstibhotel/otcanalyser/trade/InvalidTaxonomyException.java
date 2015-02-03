package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class InvalidTaxonomyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidTaxonomyException(String taxonomy) {
		System.err.println("The taxonomy: " + taxonomy + " is invalid.");
	}

}
