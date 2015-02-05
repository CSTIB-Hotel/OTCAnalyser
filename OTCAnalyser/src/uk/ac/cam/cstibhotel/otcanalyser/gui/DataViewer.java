package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class DataViewer extends JTabbedPane {
  
  private DataWindow data;
  private GraphWindow graph;
  
  public DataViewer() {
    
    data = new DataWindow();
    graph = new GraphWindow();
    
    addTab("Graph", graph);
    addTab("Data", data);
    addTab("Extended Analysis", new JLabel("Extended Analysis"));
    
  }
  
  public final void addTrades(List<Trade> trades) {
    for (Trade t : trades) {
      data.getTable().addRow(t);
    }
    graph.addTradesToDatasets(trades);
  }
  
  public static void main(String[] args) {
    JFrame frame = new JFrame("Tabs");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    DataViewer dv = new DataViewer();
    frame.add(dv);
    frame.pack();
    frame.setVisible(true);
    
    //some made-up data
    List<Trade> trades = new ArrayList<>();
    Trade t;
    for (int i = 0; i < 20; i++) {
      Date d = new Date(Calendar.getInstance().getTime().getTime()
          - (long)(Math.random() * 500000000) * i);
      for (int j = 0; j < 10; j++) {
        t = new Trade();
        t.action = "action" + i;
        t.priceNotation = 200 + Math.random() * 500;
        t.executionTimestamp = d;
        trades.add(t);
      }
    }
    dv.addTrades(trades);
   }

}
