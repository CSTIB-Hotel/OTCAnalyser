package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.DataViewer;

public class Analyser {
  public static void analyse(Search s) {
  	Connection conn = Database.getDB().getConnection();
  	
  	//perform analysis:
  	AnalysisItem max;
  	AnalysisItem min;
  	AnalysisItem avg;
  	AnalysisItem stddev;
  	List<AnalysisItem> maxes;
  	List<AnalysisItem> mins;
  	List<AnalysisItem> avgs;
  	
  	try {
  	  System.out.println(s.getCurrency());
  	  List<String> currencies = DBAnalysis.getCurrencies(s, conn);
  	  
  	  for (String curr : currencies) {
  	  	s.setCurrency(curr);
    	  maxes = DBAnalysis.getMaxPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	  mins = DBAnalysis.getMinPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	  avgs = DBAnalysis.getAvgPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	  //pass lists to graph:
    	  DataViewer.addGraphPoints(maxes, mins, avgs, curr);
    	  System.out.println(curr);
  	  }
  	} catch (SQLException e){
  		e.printStackTrace();
  		//analysis could not be performed; do not pass anything
  	}
  }
}
