package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class DataTable extends JTable{
  
  public DataTable(List<Trade> t) {
    super(new DataTableModel(t));
    setFillsViewportHeight(true);
    setAutoCreateRowSorter(true); //sortable columns
    
    //if it isn't checked, don't display it
    for (int i = 0; i < DataTableModel.columnNames.length; i++){
      if (!ColumnChooser.columnChooser.getChecked(i)){
        removeColumn(DataTableModel.columnNames[i]);
      }
    }
  }
    
  public final void removeColumn(String s){
    getColumnModel().removeColumn(getColumn(s));
  }
  
  public final void addColumn(String s, int c){
    TableColumn col = new TableColumn(c);
    col.setIdentifier(s);
    col.setHeaderValue(s);
    getColumnModel().addColumn(col);
  }
  
  public void addRow(Trade t){
    ((DataTableModel)getModel()).addRow(t, false);
  }
  
  public void addRowToStart(Trade t){
    ((DataTableModel)getModel()).addRow(t, true);
  }
    
}
