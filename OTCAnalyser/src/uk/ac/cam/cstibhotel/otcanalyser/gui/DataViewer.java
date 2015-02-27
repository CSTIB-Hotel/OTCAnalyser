package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.PriceTimePair;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

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
    
    JScrollPane graphPane = new JScrollPane(graph);
    graphPane.setOpaque(false);
    graphPane.getViewport().setOpaque(false);
    
    JScrollPane analysisPane = new JScrollPane(analysis);
    analysisPane.setOpaque(false);
    analysisPane.getViewport().setOpaque(false);
    
    addTab("Graph", graphPane);
    addTab("Data", data);
    addTab("Extended Analysis", new JScrollPane(analysisPane));
    
    this.setMinimumSize(new Dimension(750, 500));
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
  public static void addAnalysis (List<AnalysisItem> maxes, List<AnalysisItem> mins, List<AnalysisItem> avgs) {
  	if (!avgs.isEmpty() && maxes.size() == avgs.size() && avgs.size() == mins.size()) {
	    for (int i = 0; i < maxes.size(); i++) {
		    String analysis = "";
	  	  String curr = maxes.get(i).getCurrency();
	  	  if (curr.isEmpty()) {
	  		  curr = "Unknown Currency";
	  	  }
	  	  DecimalFormat format = new DecimalFormat("#.00");
	  	  curr += "\n";
	  	  analysis += "Largest Trade Price: " + format.format(maxes.get(i).getPrice()) + " " + curr;
	  	  analysis += "Smallest Trade Price: " + format.format(mins.get(i).getPrice()) + " " + curr;
	  	  analysis += "Average Trade Price: " + format.format(avgs.get(i).getPrice()) + " " + curr;
	  	  addAnalysis(analysis, "Basic Information", curr);
	    }
	  }
  }
  
  //add titled analysis
  public static void addAnalysis (String analysis, String title, String currency) {
	  dataViewer.analysis.addAnalysis(analysis, title, currency);
  }

}
