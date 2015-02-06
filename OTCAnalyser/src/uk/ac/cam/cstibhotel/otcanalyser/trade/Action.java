package uk.ac.cam.cstibhotel.otcanalyser.trade;

public enum Action {

	NEW(0),
	CANCEL(1),
	CORRECT(2);

	private short value;

	private Action(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}

}
