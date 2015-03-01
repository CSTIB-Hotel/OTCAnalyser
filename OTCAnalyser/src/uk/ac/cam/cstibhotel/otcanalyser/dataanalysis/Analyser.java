package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction.PredictionResult;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction.TrendPredictor;
import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.DataViewer;
import uk.ac.cam.cstibhotel.otcanalyser.gui.GUI;

public class Analyser {
	
	
	
  public static void analyse(Search s, int numResults) {
  	Connection conn = Database.getDB().getConnection();
  	
  	//analysis variables
  	AnalysisItem maxNoCurrency; //overall max not by currency
  	AnalysisItem minNoCurrency; //overall min not by currency
  	double avgNoCurrency; //overall average not by currency
  	List<AnalysisItem> maxWithCurrency = new ArrayList<>(); //overall max by currency
  	List<AnalysisItem> minWithCurrency = new ArrayList<>(); //overall min by currency
  	List<AnalysisItem> avgWithCurrency = new ArrayList<>(); //overall average by currency
  	double stddev; //overall std deviation
  	String mostTraded; //most traded underlying asset 1
  	String leastTraded; //least traded underlying asset 1
  	List<PriceTimePair> maxes; //max data points
  	List<PriceTimePair> mins; //min data points
  	List<PriceTimePair> avgs; //average data points
  	List<PriceTimePair> dataSet;
  	double changeInAvgCost;
  	String currency = ""; //currency for change in average cost
  	
  	try {
  		//do basic non-currency-based analysis
  		maxNoCurrency = DBAnalysis.getMaxPrice(s, conn);
  		minNoCurrency = DBAnalysis.getMinPrice(s, conn);
  		avgNoCurrency = DBAnalysis.getAvgPrice(s, conn);
  	  stddev = DBAnalysis.getStdDevPrice(s, conn);
  	  String mostLeastTraded[] = DBAnalysis.getMostAndLeastTradedUnderlyingAsset(s, conn);
  	  mostTraded = mostLeastTraded[0];
  	  leastTraded = mostLeastTraded[1];
  	  
  	  //get currencies
  	  List<String> currencies = DBAnalysis.getCurrencies(s, conn);
  	  List<PerCurrencyData> perCurrencyDataList = new ArrayList<>();
  	  
  	  for (String curr : currencies) {
  	  	if (curr != null && !curr.isEmpty()) {
	  	  	s.setCurrency(curr);
	  	  	//basic analysis by currency
	  	  	maxWithCurrency.add(DBAnalysis.getMaxPrice(s, conn));
	  	  	minWithCurrency.add(DBAnalysis.getMinPrice(s, conn));
	    	avgWithCurrency.add(new AnalysisItem(null, curr, DBAnalysis.getAvgPrice(s, conn), null));
	    	
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
	  	  	//make data points for trendline
	  	  	dataSet = DBAnalysis.getDataPoints(s, conn, DBAnalysis.EXECUTION_TIME);
	  	  	
	  	  	//gettinf the first and last elements for "time coordinate"
	  	  	  PriceTimePair first = maxes.get(0);
	  	  	  PriceTimePair last = maxes.get(maxes.size()-1);
	  	  	  
	  	  	  //x coordinate values in long
	  	  	  long firstX = first.getTime().getTime();
	  	  	  long lastX = last.getTime().getTime();
	  	  	  
	  	  	  /*
	  	  	   * 
	  	  	   * Creating trend lines
	  	  	   * 
	  	  	   */
	  	  	  TrendPredictor trendLineP = new TrendPredictor(new PerCurrencyData(dataSet, curr, byMonth));
	  	  	  PredictionResult trendResult = trendLineP.createPredictionResult();
	  	  	  PerCurrencyData avgPerCurrency = new PerCurrencyData(avgs, curr, byMonth);
//	  	  	  TrendPredictor avgTrendPredictor = new TrendPredictor(avgPerCurrency);
//	  	  	  TrendPredictor minTrendPredictor = new TrendPredictor(new PerCurrencyData(mins, curr, byMonth));
//	  	  	  TrendPredictor maxTrendPredictor = new TrendPredictor(new PerCurrencyData(maxes, curr, byMonth));
//	  	  	  PredictionResult avgPR = avgTrendPredictor.createPredictionResult();
//	  	  	  PredictionResult minPR = minTrendPredictor.createPredictionResult();
//	  	  	  PredictionResult maxPR = maxTrendPredictor.createPredictionResult();
	  	  	  
	  	  	  //System.out.println(avgPR.pmcc+" "+avgPR.regA+" "+avgPR.regB);
	  	  	  
	  	  	  //calculate trendline
	  	  	  List<PriceTimePair> trendLine = new ArrayList<PriceTimePair>();
	  	  	  double avgFirstY = trendResult.regA + firstX*trendResult.regB;
	  	  	  double avgLastY = trendResult.regA + lastX*trendResult.regB;
	  	  	  
	  	  	  //System.out.println(avgFirstY);
	  	  	  //System.out.println(avgLastY);
	  	  	  
	  	  	  trendLine.add(new PriceTimePair(new Date(firstX), avgFirstY));
	  	  	  trendLine.add(new PriceTimePair(new Date(lastX), avgLastY));
	  	  	  
//	  	  	  List<PriceTimePair> maxTrendLine = new ArrayList<PriceTimePair>();
//	  	  	  List<PriceTimePair> minTrendLine = new ArrayList<PriceTimePair>();
	  	  	  
//	  	  	  List<List<PriceTimePair>> trendLines = new ArrayList<List<PriceTimePair>>();
	  	  	  
//	  	  	  trendLines.add(avgTrendLine);
	  	  	  
	  	  	  DataViewer.addGraphPoints(maxes, mins, avgs, trendLine, curr, byMonth);
	    	  
	    	  //add to per currency data list
	    	  perCurrencyDataList.add(avgPerCurrency);
  	  	}
  	  }
  	  
  	  if (!perCurrencyDataList.isEmpty()) {
  	    //change in average cost from averages per time period for first currency
  	    List<PriceTimePair> firstAvgs = perCurrencyDataList.get(0).data; //first currency pricetime data
  	    //get difference:
  	    changeInAvgCost = firstAvgs.get(firstAvgs.size() - 1).getPrice() - firstAvgs.get(0).getPrice();
  	    currency = perCurrencyDataList.get(0).currency;
  	  } else {
  	  	changeInAvgCost = 0;
  	  }
  	  
  	  //pass analysis to GUI
  	  GUI.getInstance().addAnalyses(
  	      maxNoCurrency,
  	      minNoCurrency,
  	      avgNoCurrency,
  	      changeInAvgCost,
  	      currency,
  	      mostTraded,
  	      leastTraded,
  	      numResults,
  	      maxWithCurrency,
  	      minWithCurrency,
  	      avgWithCurrency);
  	  
  	  //pass per currency data list to extended feeder
  	  ExtendedFeeder.update(perCurrencyDataList);
  	} catch (SQLException e){
  		e.printStackTrace();
  		//analysis could not be performed; do not pass anything
  	}
  }
}
