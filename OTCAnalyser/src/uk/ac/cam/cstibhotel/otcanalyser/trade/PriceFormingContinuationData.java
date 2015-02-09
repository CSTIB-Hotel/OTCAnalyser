package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum PriceFormingContinuationData {

	TERMINATION(0),
	TRADE(1),
	AMENDMENT(2);

	private short value;

	private PriceFormingContinuationData(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}
	
	public static PriceFormingContinuationData parsesPFCD(String s) throws PFCDFormatException{
		switch(s){
			case "Termination":
				return TERMINATION;
			case "Trade":
				return TRADE;
			case "Amendment":
				return AMENDMENT;
			case "":
				return null;
			default:
				throw new PFCDFormatException();
		}
	}

}
