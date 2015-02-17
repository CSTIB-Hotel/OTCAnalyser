package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum TradeType {

    SWAP(0),
    OPTION(1),
    BOTH(2);

    private short value;

    private TradeType(int value) {
    	this.value = (short) value;
    }

    public short getValue() {
    	return value;
    }
	
	public static TradeType lookup(int i){
		switch(i){
			case 0:
				return SWAP;
			case 1:
				return OPTION;
			case 2:
				return BOTH;
			default:
				throw new IllegalArgumentException();
		}
	}

}
