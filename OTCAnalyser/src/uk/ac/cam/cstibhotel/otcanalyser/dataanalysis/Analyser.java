package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.DataViewer;
import uk.ac.cam.cstibhotel.otcanalyser.gui.GUI;

public class Analyser {
	
	
	
  public static void analyse(Search s) {
  	Connection conn = Database.getDB().getConnection();
  	
  	//analysis variables
  	AnalysisItem max; //overall max
  	AnalysisItem min; //overall min
  	List<AnalysisItem> avg = new ArrayList<>(); //overall average by currency
  	double stddev; //overall std deviation
  	String mostTraded; //most traded underlying asset 1
  	String leastTraded; //least traded underlying asset 1
  	List<PriceTimePair> maxes; //max data points
  	List<PriceTimePair> mins; //min data points
  	List<PriceTimePair> avgs; //average data points
  	double changeInAvgCost;
  	
  	try {
  		//do basic non-currency-based analysis
  	  max = DBAnalysis.getMaxPrice(s, conn);
  	  min = DBAnalysis.getMinPrice(s, conn);
  	  stddev = DBAnalysis.getStdDevPrice(s, conn);
  	  String mostLeastTraded[] = DBAnalysis.getMostAndLeastTradedUnderlyingAsset(s, conn);
  	  mostTraded = mostLeastTraded[0];
  	  leastTraded = mostLeastTraded[1];
  	  
  	  //get currencies
  	  List<String> currencies = DBAnalysis.getCurrencies(s, conn);
  	  List<PerCurrencyData> perCurrencyDataList = new ArrayList<>();
  	  
  	  for (String curr : currencies) {
  	  	s.setCurrency(curr);
  	  	//basic analysis by currency
    	  avg.add(new AnalysisItem(null, curr, DBAnalysis.getAvgPrice(s, conn), null));
  	  	//should graph by month?
  	  	boolean byMonth = DBAnalysis.graphByMonth(s, conn, DBAnalysis.EXECUTION_TIME);
  	  	
  	  	//make data points
  	  	if (byMonth) {
    	    maxes = DBAnalysis.getMaxPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	    mins = DBAnalysis.getMinPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	    avgs = DBAnalysis.getAvgPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
  	  	} else {
  	  		maxes = DBAnalysis.getMaxPricePerDay(s, conn, DBAnalysis.EXECUTION_TIME);
    	    mins = DBAnalysis.getMinPricePerDay(s, conn, DBAnalysis.EXECUTION_TIME);
    	    avgs = DBAnalysis.getAvgPricePerDay(s, conn, DBAnalysis.EXECUTION_TIME);
  	  	}
    	  //pass lists to graph:
    	  DataViewer.addGraphPoints(maxes, mins, avgs, curr, byMonth);
    	  //System.out.println(curr + " " + maxes.size());
    	  
    	  //add to per currency data list
    	  perCurrencyDataList.add(new PerCurrencyData(avgs, curr, byMonth));
  	  }
  	  
  	  //change in average cost from averages per time period for first currency
  	  List<PriceTimePair> firstAvgs = perCurrencyDataList.get(0).data; //first currency pricetime data
  	  //get difference:
  	  changeInAvgCost = firstAvgs.get(firstAvgs.size() - 1).getPrice() - firstAvgs.get(0).getPrice();
  	  
  	  //pass analysis to GUI
  	  GUI.getInstance().addAnalyses(max, min, avg, stddev, mostTraded, leastTraded, DBAnalysis.getNumTrades(s, conn), changeInAvgCost);
  	  
  	  //pass per currency data list to extended feeder
  	  ExtendedFeeder.update(perCurrencyDataList);
  	} catch (SQLException e){
  		e.printStackTrace();
  		//analysis could not be performed; do not pass anything
  	}
  }
}
