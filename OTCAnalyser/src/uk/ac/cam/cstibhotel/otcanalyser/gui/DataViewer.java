package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;

public class DataViewer extends JTabbedPane {
  
  private DataWindow data;
  private GraphWindow graph;
  private static DataViewer dataViewer = new DataViewer();
  
  private DataViewer() {
    
    data = new DataWindow();
    graph = new GraphWindow();
    
    addTab("Graph", graph);
    addTab("Data", data);
    addTab("Extended Analysis", new JLabel("Extended Analysis"));
    
  }
  
  public static void addTrades(List<Trade> trades) {
    for (Trade t : trades) {
      dataViewer.data.getTable().addRow(t);
    }
    dataViewer.graph.addTradesToDatasets(trades);
  }
  
  public static void clearTrades() {
  	dataViewer.data = new DataWindow();
  	dataViewer.graph = new GraphWindow();
  }
  
  public static void main(String[] args) {
    JFrame frame = new JFrame("Tabs");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(dataViewer);
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
        t.setAction(Action.NEW);
        t.setRoundedNotionalAmount1(200 + Math.random() * 500 + "+");
        t.setExecutionTimestamp(d);
        trades.add(t);
      }
    }
    trades.get(11).setRoundedNotionalAmount1("invalid");
    addTrades(trades);
    clearTrades();
    addTrades(trades);
    
   }

}
