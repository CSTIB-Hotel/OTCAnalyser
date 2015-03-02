package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.table.DefaultTableCellRenderer;

public class DataTableCellRenderer extends DefaultTableCellRenderer{
  private static final long serialVersionUID = 1L;
  //formatting for the values in the table

  @Override
  protected void setValue(Object o) {
  	if (o instanceof Boolean) {
  		if ((boolean)o) {
  			super.setValue("Yes");
  		} else {
  			super.setValue("No");
  		}
  	}
  	else super.setValue(o);
  }
  
}
