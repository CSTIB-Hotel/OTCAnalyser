package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.Date;

public class AnalysisItem extends PriceTimePair{
	
  private String currency;
  private String underlyingAsset;
  
  public AnalysisItem(Date time, String currency, double price, String underlyingAsset) {
  	super(time, price);
  	this.currency = currency;
  	this.underlyingAsset = underlyingAsset;
  }
  
  public String getCurrency() {
  	return currency;
  }
  
  public String getUnderlyingAsset() {
  	return underlyingAsset;
  }
}
