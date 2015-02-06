package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum AssetClass {

	Commodity(0),
	Credit(1),
	Equity(2),
	ForeignExchange(3),
	Rates(4);

	private short value;

	private AssetClass(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}

}
