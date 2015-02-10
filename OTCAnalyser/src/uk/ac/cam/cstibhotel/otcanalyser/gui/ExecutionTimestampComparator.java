package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import java.util.Comparator;

public class ExecutionTimestampComparator implements Comparator<Trade>{
  
  @Override
  public int compare(Trade t1, Trade t2) {
    return t1.getExecutionTimestamp().compareTo(t2.getExecutionTimestamp());
  }
  
}
