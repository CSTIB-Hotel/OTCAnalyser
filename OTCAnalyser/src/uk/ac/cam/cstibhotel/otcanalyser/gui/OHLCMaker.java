package uk.ac.cam.cstibhotel.otcanalyser.gui;
	
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.OHLCDataset;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

//makes an OHLC chart based on execution timestamps and rounded notional amount 1 per day
public class OHLCMaker {
  
  public static JFreeChart makeChart(String name, OHLCDataset dataset) {
    JFreeChart chart = ChartFactory.createCandlestickChart(name, "Execution Date", "Price",
        dataset, false);
    
    //appearance tweaks
    XYPlot plot = (XYPlot) chart.getPlot();
    CandlestickRenderer csr = (CandlestickRenderer) plot.getRenderer();
    csr.setUseOutlinePaint(true);
    //set up paint to white and down paint to dark gray
    csr.setUpPaint(Color.WHITE); //for when close > open
    csr.setDownPaint(Color.DARK_GRAY); //for when open > close
    
    //date formatting
    DateAxis axis = (DateAxis) plot.getDomainAxis();
    if (axis.getMaximumDate().getMonth() > axis.getMinimumDate().getMonth()){
      axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
    } else {
      axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));
    }
    
    return chart;
  }
  
  public static OHLCSeriesCollection makeDataset(List<Trade> trade, String name, boolean sorted) {
    if (!sorted) { //not sorted by execution timestamps
      sortByExecutionTimestamp(trade);
    }
    OHLCSeriesCollection dataset = new OHLCSeriesCollection();
    dataset.addSeries(makeSeries(trade, name));
    return dataset;
  }
  
  //sorts trades by execution timestamp
  public static void sortByExecutionTimestamp(List<Trade> trade) {
    Collections.sort(trade, new ExecutionTimestampComparator());
  }
  
  //given a list of trades ordered by execution timestamps, makes OHLCSeries
  public static OHLCSeries makeSeries(List<Trade> trade, Comparable<String> key) {
    OHLCSeries ohlcs = new OHLCSeries(key);
    addToSeries(ohlcs, trade);
    return ohlcs;
  }
  
  // returns true if series successfully updates and otherwise returns false
  public static boolean updateSeries(OHLCSeriesCollection dataset, int series, List<Trade> trade) {
    OHLCSeries ohlcs = dataset.getSeries(series);
    try {
      addToSeries(ohlcs, trade);
    } catch(SeriesException e) {
    	// overlap with dates
      return false;
    }
    return true;
  }
  
  private static void addToSeries(OHLCSeries ohlcs, List<Trade> trade) {
    if (trade.isEmpty()) {
      return;
    }
    int k = 0;
    Day rtp = new Day(trade.get(k).getExecutionTimestamp()); //day time period
    String rna = trade.get(k).getRoundedNotionalAmount1();
    //cycle through trade list looking for first readable rounded notional amount 1
    while (!(validRNA(rna)) && k < trade.size() - 1) {
    	k++;
    	rtp = new Day(trade.get(k).getExecutionTimestamp());
    	rna = trade.get(k).getRoundedNotionalAmount1();
    }
    //if they are all unreadable
    if (k == trade.size() - 1) {
    	return;
    }
    double open = getDoubleRNA(rna);
    double high = open;
    double low = open;
    double close = open;
    for (int i = k; i < trade.size(); i++) {
      Trade currentTrade = trade.get(i);
      rna = currentTrade.getRoundedNotionalAmount1();
      if (validRNA(rna)) {
        double price = getDoubleRNA(rna);
        if(rtp.equals(new Day(currentTrade.getExecutionTimestamp()))) { //same time period
        	if (price > high) {
        		high = price;
        	}
        	if (price < low) {
        		low = price;
        	}
        	close = price;
        } else {
        	ohlcs.add(rtp, open, high, low, close);
        	rtp = new Day(currentTrade.getExecutionTimestamp());
        	open = price;
        	high = open;
        	low = open;
          close = open;
       }
      }
    }
    ohlcs.add(rtp, open, high, low, close); //add last item in
  }
  
  //returns true if rna is in valid Rounded Notional Amount format
  private static boolean validRNA(String rna) {
  	try {
  		getDoubleRNA(rna);
  		return true;
  	} catch (Exception e) {
  		return false;
  	}
  }
  
  //get double form of the Rounded Notional Amount, which sometimes contains the '+' character
  private static double getDoubleRNA(String rna) {
  	int index = rna.indexOf('+');
  	if (index > -1) {
  		rna = rna.substring(0, index);
  	}
  	return Double.parseDouble(rna);
  }
}