package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum PriceFormingContinuationData {

	TERMINATION(0),
	TRADE(1);

	private short value;

	private PriceFormingContinuationData(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}

}
