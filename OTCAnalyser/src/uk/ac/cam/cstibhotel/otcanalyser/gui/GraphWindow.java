package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;

public class GraphWindow extends JPanel{
  
  private OHLCSeriesCollection dataset;
  
  public GraphWindow() {
  super(new BorderLayout());
    setOpaque(false);
    
    dataset = OHLCMaker.makeDataset(new ArrayList<Trade>(), "Trades", false);
    JFreeChart chart = OHLCMaker.makeChart("Trades", dataset);
    ChartPanel panel = new ChartPanel(chart); 
    panel.setFillZoomRectangle(true);
    panel.setMouseWheelEnabled(true);
    
    GraphPanel pnl = new GraphPanel();
    pnl.add(panel);
    add(pnl);
  }
  
  public void addTradesToDatasets(List<Trade> trade) {
    OHLCMaker.sortByExecutionTimestamp(trade);
    int count = dataset.getSeriesCount();
    for (int i = 0; i < count; i++) {
      for (int j = 0; j < 100; j++){
      OHLCMaker.updateSeries(dataset, i, trade);}
    }
  }
  
}
