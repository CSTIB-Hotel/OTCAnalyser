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

}
