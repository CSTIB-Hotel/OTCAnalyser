package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction.PredictionResult;

public class PerCurrencyResult {
	PredictionResult data;
	String currency;
	
	public PerCurrencyResult(PredictionResult data, String currency) {
		this.data = data;
		this.currency = currency;
	}
}