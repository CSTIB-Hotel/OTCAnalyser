package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum Collateralization {

    UC(0),
    PC(1),
    FC(2),
    OC(3),
    BLANK(4);

    private short value;

    private Collateralization(int value) {
	this.value = (short) value;
    }

    public short getValue() {
	return value;
    }
    
    public static Collateralization parse(String s) throws CollateralizationFormatException{
    	switch(s){
    		case "UC":
    			return UC;
    		case "PC":
    			return PC;
    		case "FC":
    			return FC;
    		case "OC":
    			return OC;
    		case "":
    			return BLANK;
    		default:
    			throw new CollateralizationFormatException();
    	}
    			
    }

}
