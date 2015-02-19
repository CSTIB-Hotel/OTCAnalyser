package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.Date;

public class AnalysisItem {
	
	private Date time;
  private String currency;
  private long price;
  
  public AnalysisItem(Date time, String currency, long price) {
  	this.time = time;
  	this.currency = currency;
  	this.price = price;
  }
  
  public Date getTime() {
  	return time;
  }
  
  public String getCurrency() {
  	return currency;
  }
  
  public long getPrice() {
  	return price;
  }
}
