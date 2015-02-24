package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.List;

public class PerCurrencyData {
	public List<PriceTimePair> data;
	public String currency;
	public Boolean perMonth;
	
	public PerCurrencyData(List<PriceTimePair> data, String currency, Boolean perMonth) {
		this.data = data;
		this.currency = currency;
		this.perMonth = perMonth;
	}
	
}