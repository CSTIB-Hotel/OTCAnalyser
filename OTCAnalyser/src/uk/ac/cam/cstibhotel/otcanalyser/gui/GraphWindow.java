package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.PriceTimePair;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphWindow extends CurrencyToolbarWindow implements ActionListener{
	
  private static final long serialVersionUID = 1L;
  private ContentPanel pnl;
  private List<TimeSeriesCollection> datasets = new ArrayList<>();
  private List<ChartPanel> chartPanels = new ArrayList<>();
  private ChartPanel currentChart = null;
  
  public GraphWindow() {
    pnl = new ContentPanel();
    add(pnl, BorderLayout.CENTER);
  }
  
  //add trendline points to dataset
  public synchronized void addTrendlinePoints(List<List<PriceTimePair>> ptp) {
  	for (int i = 0; i < ptp.size(); i++) {
  		LineGraphMaker.addToSeries(ptp.get(i), datasets.get(i), LineGraphMaker.TREND_LINE, false);
  	}
  }
  
  public synchronized void addChartPanel(String currency, boolean byMonth) {
  	TimeSeriesCollection dataset = LineGraphMaker.makeDataset();
    datasets.add(dataset);
    String currencyName = currency;
    if (currency.isEmpty()) {
    	currencyName = "Unknown Currency";
    }
    String timePeriod = "Day";
    if (byMonth) {
    	timePeriod = "Month";
    }
    JFreeChart chart = LineGraphMaker.makeChart("Prices by " + timePeriod, currencyName, dataset, byMonth);
    ChartPanel panel = new ChartPanel(chart);
    chartPanels.add(panel);
    ChartEditorManager.setChartEditorFactory(new NewDefaultChartEditorFactory());
    panel.setFillZoomRectangle(true);
    panel.setMouseWheelEnabled(true);
    currencies.add(currency);
  }
  
  //add trades to existing datasets
  public synchronized void addTradesToDatasets(List<PriceTimePair> maxes, List<PriceTimePair> mins,
      List<PriceTimePair> avgs,List<PriceTimePair> trendLine, String currency, boolean byMonth) {
    if (!currencies.contains(currency)) {
    	addChartPanel(currency, byMonth);
    }
    int i = currencies.indexOf(currency);
    TimeSeriesCollection dataset = datasets.get(i);
    LineGraphMaker.addToSeries(maxes, dataset, LineGraphMaker.MAX, byMonth);
    LineGraphMaker.addToSeries(mins, dataset, LineGraphMaker.MIN, byMonth);
    LineGraphMaker.addToSeries(avgs, dataset, LineGraphMaker.AVG, byMonth);
    LineGraphMaker.addToSeries(trendLine, dataset, LineGraphMaker.TREND_LINE, byMonth);
    if (currencies.size() > 1) {
    	makeToolBar();
    }
    if (pnl.getComponentCount() == 0) {
    	currentChart = chartPanels.get(0);
    	pnl.add(currentChart);
    }
  }
  
  public synchronized void clear() {
  	currencies = new ArrayList<>();
  	datasets = new ArrayList<>();
  	chartPanels = new ArrayList<>();
  	pnl.removeAll();
  	if (showToolbar) {
  	  remove(toolbar);
  	  showToolbar = false;
  	}
  }
  
  @Override
  public synchronized void actionPerformed(ActionEvent e){
    if (currencies.contains(e.getActionCommand())) {
    	pnl.removeAll();
    	currentChart = chartPanels.get(currencies.indexOf(e.getActionCommand()));
      pnl.add(currentChart);
      pnl.repaint();
    }
  }
  
}
