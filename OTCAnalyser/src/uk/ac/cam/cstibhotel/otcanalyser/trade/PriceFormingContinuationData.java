package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum PriceFormingContinuationData {

	TERMINATION(0),
	TRADE(1),
	AMENDMENT(2),
	INCREASE(3),
	NOVATION(4),
	PARTIALTERMINATION(5);

	private short value;

	private PriceFormingContinuationData(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}
	
	public static PriceFormingContinuationData lookup(int i){
		switch(i){
			case 0:
				return TERMINATION;
			case 1:
				return TRADE;
			case 2:
				return AMENDMENT;
			case 3:
				return INCREASE;
			case 4: 
				return NOVATION;
			case 5:
				return PARTIALTERMINATION;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public static PriceFormingContinuationData parsePFCD(String s) throws PFCDFormatException{
		switch(s){
			case "Termination":
				return TERMINATION;
			case "Trade":
				return TRADE;
			case "TRADE":
				return TRADE;
			case "Amendment":
				return AMENDMENT;
			case "AMENDMENT":
				return AMENDMENT;
			case "Increase":
				return INCREASE;
			case "Novation":
				return NOVATION;
			case "PartialTermination":
				return PARTIALTERMINATION;
			case "Partialtermination":
				return PARTIALTERMINATION;
			case "":
				return null;
			default:
				throw new PFCDFormatException("for string " + s);
		}
	}

}
