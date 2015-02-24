package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.Date;

public class PriceTimePair {
	
	private Date time;
  private double price;
  
  public PriceTimePair(Date time, double price) {
  	this.time = time;
  	this.price = price;
  }

  public Date getTime() {
  	return time;
  }
  
  public double getPrice() {
  	return price;
  }
}
