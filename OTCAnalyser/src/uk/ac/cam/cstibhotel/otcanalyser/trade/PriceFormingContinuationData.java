package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum PriceFormingContinuationData {

	TERMINATION(0),
	TRADE(1),
	AMENDMENT(2),
	INCREASE(3),
	NOVATION(4);

	private short value;

	private PriceFormingContinuationData(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}
	
	public static PriceFormingContinuationData parsePFCD(String s) throws PFCDFormatException{
		switch(s){
			case "Termination":
				return TERMINATION;
			case "Trade":
				return TRADE;
			case "Amendment":
				return AMENDMENT;
			case "Increase":
				return INCREASE;
			case "Novation":
				return NOVATION;
			case "":
				return null;
			default:
				throw new PFCDFormatException("for string " + s);
		}
	}

}
