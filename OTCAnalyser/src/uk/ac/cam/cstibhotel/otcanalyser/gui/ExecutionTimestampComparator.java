package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.util.Comparator;

public class ExecutionTimestampComparator implements Comparator<Trade>{
  
  @Override
  public int compare(Trade t1, Trade t2) {
    if(t1.executionTimestamp.before(t2.executionTimestamp)){
      return -1;
    } else if (t1.executionTimestamp.after(t2.executionTimestamp)) {
      return 1;
    } else {
      return 0;
    }
  }
  
}
