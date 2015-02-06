package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

public class DataWindow extends CBLPanel implements ActionListener, ItemListener{
  private static final long serialVersionUID = 1L;
  private DataTable table;
  
  public DataWindow(){
    super();
    
    table = new DataTable(new ArrayList<Trade>());

    JButton button = new JButton("Change Columns...");
    button.setActionCommand("columns");
    JToolBar toolbar = new JToolBar();
    toolbar.setOpaque(true);
    toolbar.setRollover(true);
    toolbar.setFloatable(false);
    toolbar.add(button);
    
    add(toolbar, BorderLayout.PAGE_END);
    add(new JScrollPane(table), BorderLayout.CENTER);
    
    button.addActionListener(this);
    ColumnChooser.columnChooser.addItemListener(this);
  }
  
  public DataTable getTable() {
    return table;
  }
  
  @Override
  public void actionPerformed(ActionEvent e){
    if ("columns".equals(e.getActionCommand())) {
      ColumnChooser.columnChooser.setVisible(true);
    }
  }
   
  @Override
  public void itemStateChanged(ItemEvent e){
    ColCheckBox box = ((ColCheckBox)e.getItem());
    if (e.getStateChange() == ItemEvent.DESELECTED){
      table.removeColumn(box.getText());
    }
    else {
      table.addColumn(box.getText(), box.colNumber);
    }
  }
  
}
