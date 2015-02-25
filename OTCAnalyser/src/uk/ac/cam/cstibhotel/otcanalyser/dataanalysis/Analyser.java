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
  	List<AnalysisItem> maxes;
  	List<AnalysisItem> mins;
  	List<AnalysisItem> avgs;
  	
  	try {
  	  max = DBAnalysis.getMaxPrice(s, conn);
  	  min = DBAnalysis.getMinPrice(s, conn);
  	  stddev = DBAnalysis.getStdDevPrice(s, conn);
  	  
  	  List<String> currencies = DBAnalysis.getCurrencies(s, conn);
  	  
  	  for (String curr : currencies) {
  	  	s.setCurrency(curr);
    	  maxes = DBAnalysis.getMaxPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	  mins = DBAnalysis.getMinPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	  avgs = DBAnalysis.getAvgPricePerMonth(s, conn, DBAnalysis.EXECUTION_TIME);
    	  avg.add(new AnalysisItem(null, curr, DBAnalysis.getAvgPrice(s, conn), null));
    	  //pass lists to graph:
    	  DataViewer.addGraphPoints(maxes, mins, avgs, curr);
    	//  System.out.println(curr);
  	  }
  	  //pass analysis to GUI
  	  GUI.getInstance().addAnalyses(max, min, avg, stddev);
  	} catch (SQLException e){
  		e.printStackTrace();
  		//analysis could not be performed; do not pass anything
  	}
  }
}
