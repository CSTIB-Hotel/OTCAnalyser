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
	
	public static Action lookup(int i){
		switch(i){
			case 0:
				return NEW;
			case 1:
				return CANCEL;
			case 2:
				return CORRECT;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public static Action parseAct(String s) throws ActionFormatException{
		switch(s){
			case "NEW":
				return NEW;
			case "CANCEL":
				return CANCEL;
			case "CORRECT":
				return CORRECT;
			default:
				throw new ActionFormatException("Malfunctioned Action entry!");
		}
		
	}

}
