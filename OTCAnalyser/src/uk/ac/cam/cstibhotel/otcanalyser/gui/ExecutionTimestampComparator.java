package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import java.util.Comparator;

public class ExecutionTimestampComparator implements Comparator<Trade>{
  
  @Override
  public int compare(Trade t1, Trade t2) {
    if(t1.getExecutionTimestamp().before(t2.getExecutionTimestamp())){
      return -1;
    } else if (t1.getExecutionTimestamp().after(t2.getExecutionTimestamp())) {
      return 1;
    } else {
      return 0;
    }
  }
  
}
