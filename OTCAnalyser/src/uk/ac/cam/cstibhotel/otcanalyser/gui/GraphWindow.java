package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class GraphWindow extends CBLPanel implements ActionListener{
	
  private static final long serialVersionUID = 1L;
  private ContentPanel pnl;
  private List<String> currencies = new ArrayList<>();
  private List<TimeSeriesCollection> datasets = new ArrayList<>();
  private List<ChartPanel> chartPanels = new ArrayList<>();
  private ChartPanel currentChart = null;
  private JToolBar toolbar;
  private boolean showToolbar = false;
  
  public GraphWindow() {
    pnl = new ContentPanel();
    add(pnl, BorderLayout.CENTER);
  }
  
  public void makeToolBar() {
  	if (showToolbar) {
  		remove(toolbar);
  	}
  	toolbar = new JToolBar();
    toolbar.setOpaque(true);
    toolbar.setRollover(true);
    toolbar.setFloatable(false);

    JButton button;
    for (String curr : currencies) {
    	button = new JButton(curr);
    	if (curr.isEmpty()) {
    		button = new JButton("Unknown Currency");
    	}
    	toolbar.add(button);
    	button.setActionCommand(curr);
    	button.addActionListener(this);
    }
    this.add(toolbar, BorderLayout.SOUTH);
    showToolbar = true;
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
    if (currencies.size() > 1) {
    	makeToolBar();
    }
    currentChart = chartPanels.get(0);
    pnl.add(currentChart);
  }
  
  public void clear() {
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
  public void actionPerformed(ActionEvent e){
    if (currencies.contains(e.getActionCommand())) {
    	pnl.remove(currentChart);
    	currentChart = chartPanels.get(currencies.indexOf(e.getActionCommand()));
      pnl.add(currentChart);
    }
  }
  
}
