package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum AssetClass {

	COMMODITY(0), //CO
	CREDIT(1),	//CR
	EQUITY(2), //EQ
	FOREX(3), //FX
	RATES(4); //IR

	private short value;

	private AssetClass(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}
	
	public static AssetClass lookup(int i){
		switch(i){
			case 0:
				return COMMODITY;
			case 1:
				return CREDIT;
			case 2:
				return EQUITY;
			case 3:
				return FOREX;
			case 4:
				return RATES;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public static AssetClass parseAssetC(String s) throws AssetClassFormatException{
		switch(s){
			case "CO":
				return COMMODITY;
			case "CR":
				return CREDIT;
			case "EQ":
				return EQUITY;
			case "FX":
				return FOREX;
			case "IR":
				return RATES;
			case "":
				return null;
			default:
				throw new AssetClassFormatException();
		}
	}

}
