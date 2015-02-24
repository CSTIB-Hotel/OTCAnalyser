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
  	
  	//perform analysis:
  	AnalysisItem max;
  	AnalysisItem min;
  	List<AnalysisItem> avg = new ArrayList<>();
  	double stddev;
  	List<PriceTimePair> maxes;
  	List<PriceTimePair> mins;
  	List<PriceTimePair> avgs;
  	
  	try {
  		//do basic non-currency-based analysis
  	  max = DBAnalysis.getMaxPrice(s, conn);
  	  min = DBAnalysis.getMinPrice(s, conn);
  	  stddev = DBAnalysis.getStdDevPrice(s, conn);
  	  
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
  	  //pass analysis to GUI
  	  GUI.getInstance().addAnalyses(max, min, avg, stddev);
  	  
  	  //pass per currency data list to extended feeder
  	  ExtendedFeeder.update(perCurrencyDataList);
  	} catch (SQLException e){
  		e.printStackTrace();
  		//analysis could not be performed; do not pass anything
  	}
  }
}
