package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

public class SearchResult {
	
	// A list containing all of the trades fitting the search query
	private List<Trade> resultData;
		
	// Statistics relating to the results - could be useful
	private int numResults;
	private double elapsedTime;
	
	public SearchResult(List<Trade> results, double time) {
		resultData = results;
		numResults = results.size();
		elapsedTime = time;
	}
	
	public List<Trade> getResultData() {
		return resultData;
	}
	
	public int getNumResults() {
		return numResults;
	}
	
	public double getElapsedTime() {
		return elapsedTime;
	}
	
}