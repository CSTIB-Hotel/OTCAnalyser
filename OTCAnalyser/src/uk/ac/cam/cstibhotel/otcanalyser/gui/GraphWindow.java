package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.data.xy.XYDataset;

import java.util.ArrayList;
import java.util.List;

public class GraphWindow extends CBLPanel{
	
  private static final long serialVersionUID = 1L;
  private ContentPanel pnl;
  private List<String> currencies = new ArrayList<>();
  private List<XYDataset> datasets = new ArrayList<>();
  private List<ChartPanel> chartPanels = new ArrayList<>();
  
  public GraphWindow() {
    pnl = new ContentPanel();
    add(pnl);
  }
  
  public void addChartPanel(String currency) {
  	XYDataset dataset = LineGraphMaker.makeDataset();
    datasets.add(dataset);
    JFreeChart chart = LineGraphMaker.makeMonthChart("Month", currency, dataset);
    ChartPanel panel = new ChartPanel(chart); 
    chartPanels.add(panel);
    ChartEditorManager.setChartEditorFactory(new NewDefaultChartEditorFactory());
    panel.setFillZoomRectangle(true);
    panel.setMouseWheelEnabled(true);
    pnl.add(panel);
  }
  
  //add trades to existing datasets
  public void addTradesToDatasets(List<AnalysisItem> maxes, List<AnalysisItem> mins,
      List<AnalysisItem> avgs, String currency) {
    if (!currencies.contains(currency)) {
    	addChartPanel(currency);
    }
    int i = currencies.lastIndexOf(currency);
    XYDataset dataset = datasets.get(i);
    LineGraphMaker.addToSeries(maxes, dataset, LineGraphMaker.MAX);
    LineGraphMaker.addToSeries(mins, dataset, LineGraphMaker.MIN);
    LineGraphMaker.addToSeries(avgs, dataset, LineGraphMaker.AVG);

  }
  
}
