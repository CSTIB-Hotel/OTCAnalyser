package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.util.Collections;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

public class DateSorter {
  
  //sorts trades by execution timestamp
  public static void sortByExecutionTimestamp(List<Trade> trade) {
    Collections.sort(trade, new ExecutionTimestampComparator());
  }
  
}
