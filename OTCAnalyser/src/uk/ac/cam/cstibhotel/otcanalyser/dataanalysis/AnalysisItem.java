package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.Date;

public class AnalysisItem {
	
  private Date time;
  private String currency;
  private double price;
  
  public AnalysisItem(Date time, String currency, double price) {
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
  
  public double getPrice() {
  	return price;
  }
}
