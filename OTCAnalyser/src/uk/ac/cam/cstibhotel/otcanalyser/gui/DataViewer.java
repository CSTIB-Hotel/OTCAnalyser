package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.PriceTimePair;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class DataViewer extends JTabbedPane {
	
  private static final long serialVersionUID = 1L;
  private DataWindow data;
  private GraphWindow graph;
  private AnalysisWindow analysis;
  public static DataViewer dataViewer = new DataViewer();
  
  private DataViewer() {
    
    data = new DataWindow();
    graph = new GraphWindow();
    analysis = new AnalysisWindow();
    
    addTab("Graph", graph);
    addTab("Data", data);
    addTab("Extended Analysis", analysis);
    
  }
  
  //add trades to the DataViewer table
  public static void addTrades(List<Trade> trades) {
    for (Trade t : trades) {
      dataViewer.data.getTable().addRow(t);
    }
  }
  
  public static void addGraphPoints(List<PriceTimePair> maxes, List<PriceTimePair> mins,
      List<PriceTimePair> avgs, String currency, boolean byMonth) {
  	dataViewer.graph.addTradesToDatasets(maxes, mins, avgs, currency, byMonth);
  }
  
  //adds trendline points to graphs
  public static void addTrendlinePoints(List<List<PriceTimePair>> ptp) {
  	dataViewer.graph.addTrendlinePoints(ptp);
  }
  
  //clear trades - call before adding new trades
  public static void clearTrades() {
  	dataViewer.data.clear();
  	dataViewer.graph.clear();
  	dataViewer.analysis.clear();
  	dataViewer.repaint();
  }
  
  //add analysis
  public static void addAnalysis (AnalysisItem max, AnalysisItem min, List<AnalysisItem> avgs, double stddev) {
	  if (max != null) {
  	  String maxAnalysis = max.getPrice() + " " + max.getCurrency() + " at " + max.getTime();
	    addAnalysis (maxAnalysis, "Largest trade");
	  }
	  if (min != null) {
	    String minAnalysis = min.getPrice() + " " + min.getCurrency () + " at " + min.getTime();
	    addAnalysis (minAnalysis, "Smallest trade");
	  }
	  if (!avgs.isEmpty()) {
	    String avgAnalysis = "";
	    for (AnalysisItem a : avgs) {
	  	  String curr = a.getCurrency();
	  	  if (curr.isEmpty()) {
	  		  curr = "Unknown Currency";
	  	  }
	  	  curr += "\n";
	  	  avgAnalysis += a.getPrice() + " " + curr;
	    }
	    addAnalysis (avgAnalysis, "Average trade amounts by currency");
	  }
  }
  
  //add titled analysis
  public static void addAnalysis (String analysis, String title) {
	  dataViewer.analysis.addAnalysis(analysis, title);
  }
  
  //TODO: delete it from final product
//  public static void main(String[] args) {
//    JFrame frame = new JFrame("Tabs");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.add(dataViewer);
//    frame.pack();
//    frame.setVisible(true);
//    
//    //some made-up data
//    List<Trade> trades = new ArrayList<>();
//    Trade t;
//    for (int i = 0; i < 20; i++) {
//      Date d = new Date(Calendar.getInstance().getTime().getTime()
//          - (long)(Math.random() * 500000000) * i);
//      for (int j = 0; j < 10; j++) {
//        t = new Trade();
//        t.setAction(Action.NEW);
//        t.setBlockTrades(false);
//        t.setRoundedNotionalAmount1(200 * 500);
//        t.setExecutionTimestamp(d);
//        trades.add(t);
//      }
//    }
//    trades.get(11).setRoundedNotionalAmount1(0);
//    addTrades(trades);
//    clearTrades();
//    addTrades(trades);
//    
//    String a = "Analysis of data.";
//    
//    for (int i = 1; i < 5; i++) {
//    	addAnalysis(a, "Analysis Title " + i);
//    }
//    
//   }

}
