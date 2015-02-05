package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

class DataTableModel extends AbstractTableModel{
  
  public static final String[] columnNames = {
    "Dissemination ID",
    "Original Dissemination ID",
    "Action",
    "Execution Timestamp",
    "Cleared",
    "Collateralization",
    "End User Exception",
    "Bespoke Y/N",
    "Execution Venue",
    "Block Trades",
    "Effective Date",
    "Maturity Date",
    "Day Count Convention",
    "Settlement Currency",
    "Trade Type",
    "Asset Class",
    "Subasset Class",
    "UPI/Taxonomy",
    "Price Forming Continuation Data",
    "Underlying Asset 1",
    "Underlying Asset 2",
    "Price Notation Type",
    "Price Notation",
    "Additional Price Notation Type",
    "Additional Price Notation",
    "Notional Currency 1",
    "Notional Currency 2",
    "Rounded Notional Amount 1",
    "Rounded Notional Amount 2",
    "Payment Frequency 1",
    "Payment Frequency 2",
    "Reset Frequency 1",
    "Reset Frequency 2",
    "Embedded Option",
    "Option Strike Price",
    "Option Type",
    "Option Family",
    "Option Currency",
    "Option Premium",
    "Option Lock Period",
    "Option Expiration Date",
    "Price Notation 2 Type",
    "Price Notation 2",
    "Price Notation 3 Type",
    "Price Notation 3"
  };
  
  private final List<Object[]> data;
            
  public DataTableModel(List<Trade> trades){
    Class tradeClass = Trade.class;
    
    //fill columns
    data = new ArrayList<>(trades.size());
    for (Trade t : trades){
      addRow(t, false);
    }
  }
            
  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public int getRowCount() {
    return data.size();
  }
            
  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return data.get(rowIndex)[columnIndex];
  }
  
  public final void addRow(Trade t, boolean toBeginning){
    Class tradeClass = t.getClass();
    Field[] fields = tradeClass.getDeclaredFields();
    Object[] row = new Object[fields.length];
    for (int i = 0; i < fields.length; i++) {
      try {
      row[i] = fields[i].get(t);
      } catch (IllegalAccessException e) {
        System.err.println("Problem filling table.");
      }
    }
    if (toBeginning){
      data.add(0, row);
    }
    else {
      data.add(row);
    }
  }
    
}
