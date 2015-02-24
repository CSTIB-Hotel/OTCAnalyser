package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.List;

public class PerCurrencyResult {
	List<PredictionResult> data;
	String currency;
	
	public PerCurrencyData(List<PredictionResult> data, String currency) {
		this.data = data;
		this.currency = currency;
	}
}