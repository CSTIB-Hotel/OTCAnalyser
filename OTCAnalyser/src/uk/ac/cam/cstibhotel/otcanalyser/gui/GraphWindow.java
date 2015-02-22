package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.data.time.TimeSeriesCollection;

import java.util.ArrayList;
import java.util.List;

public class GraphWindow extends CBLPanel{
	
  private static final long serialVersionUID = 1L;
  private ContentPanel pnl;
  private List<String> currencies = new ArrayList<>();
  private List<TimeSeriesCollection> datasets = new ArrayList<>();
  private List<ChartPanel> chartPanels = new ArrayList<>();
  
  public GraphWindow() {
    pnl = new ContentPanel();
    add(pnl);
  }
  
  public void addChartPanel(String currency) {
  	TimeSeriesCollection dataset = LineGraphMaker.makeDataset();
    datasets.add(dataset);
    String currencyName = currency;
    if (currency.isEmpty()) {
    	currencyName = "Unknown Currency";
    }
    JFreeChart chart = LineGraphMaker.makeMonthChart("Prices by Month", currencyName, dataset);
    ChartPanel panel = new ChartPanel(chart); 
    chartPanels.add(panel);
    ChartEditorManager.setChartEditorFactory(new NewDefaultChartEditorFactory());
    panel.setFillZoomRectangle(true);
    panel.setMouseWheelEnabled(true);
    currencies.add(currency);
    pnl.add(panel);
  }
  
  //add trades to existing datasets
  public void addTradesToDatasets(List<AnalysisItem> maxes, List<AnalysisItem> mins,
      List<AnalysisItem> avgs, String currency) {
    if (!currencies.contains(currency)) {
    	addChartPanel(currency);
    }
    int i = currencies.indexOf(currency);
    TimeSeriesCollection dataset = datasets.get(i);
    LineGraphMaker.addToSeries(maxes, dataset, LineGraphMaker.MAX);
    LineGraphMaker.addToSeries(mins, dataset, LineGraphMaker.MIN);
    LineGraphMaker.addToSeries(avgs, dataset, LineGraphMaker.AVG);
  }
  
  public void clear() {
  	currencies = new ArrayList<>();
  	datasets = new ArrayList<>();
  	chartPanels = new ArrayList<>();
  	pnl.removeAll();
  }
  
}
