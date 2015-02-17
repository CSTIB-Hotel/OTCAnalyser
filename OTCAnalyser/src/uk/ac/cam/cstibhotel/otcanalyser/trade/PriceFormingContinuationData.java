package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum PriceFormingContinuationData {

	TERMINATION(0),
	TRADE(1),
	AMENDMENT(2),
	INCREASE(3),
	NOVATION(4),
	PARTIALTERMINATION(5),
	EXIT(6),
	EXERCISE(7),
	GLOBALCANCEL(8),
	AMEND(9);

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
			case 6:
				return EXIT;
			case 7:
				return EXERCISE;
			case 8:
				return GLOBALCANCEL;
			case 9:
				return AMEND;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public static PriceFormingContinuationData parsePFCD(String s) throws PFCDFormatException{
		s = s.toLowerCase();
		s = s.replace("-", "");
		s = s.replace(" ", "");
		switch(s){
			case "termination":
				return TERMINATION;
			case "trade":
				return TRADE;
			case "amendment":
				return AMENDMENT;
			case "increase":
				return INCREASE;
			case "novation":
				return NOVATION;
			case "novationtrade":
				return NOVATION;
			case "partialtermination":
				return PARTIALTERMINATION;
			case "exit":
				return EXIT;
			case "exercise":
				return EXERCISE;
			case "globalcancel":
				return GLOBALCANCEL;
			case "amend":
				return AMEND;
			case "":
				return null;
			default:
				throw new PFCDFormatException("for string " + s);
		}
	}

}
