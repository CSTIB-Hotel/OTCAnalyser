package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import java.util.ArrayList;
import java.util.List;

public class GraphWindow extends CBLPanel{
  private static final long serialVersionUID = 1L;
  private OHLCSeriesCollection dataset;
  
  public GraphWindow() {
    dataset = OHLCMaker.makeDataset(new ArrayList<Trade>(), "Trades", false);
    JFreeChart chart = OHLCMaker.makeChart("Trades", dataset);
    ChartPanel panel = new ChartPanel(chart); 
    panel.setFillZoomRectangle(true);
    panel.setMouseWheelEnabled(true);
    
    GraphPanel pnl = new GraphPanel();
    pnl.add(panel);
    add(pnl);
  }
  
  //add trades to existing datasets
  public void addTradesToDatasets(List<Trade> trade) {
    OHLCMaker.sortByExecutionTimestamp(trade);
    int count = dataset.getSeriesCount();
    for (int i = 0; i < count; i++) {
      OHLCMaker.updateSeries(dataset, i, trade);
    }
  }
  
}
