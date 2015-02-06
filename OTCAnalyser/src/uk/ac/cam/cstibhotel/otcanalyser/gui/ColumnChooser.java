package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.GridLayout;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ColumnChooser extends JDialog{
  private static final long serialVersionUID = 1L;
  public static ColumnChooser columnChooser = new ColumnChooser();
  private final ColCheckBox[] boxes;
  
  private ColumnChooser() {
    JPanel panel = new JPanel(new GridLayout(15,3));
    panel.setBorder(
        BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
        BorderFactory.createTitledBorder("Choose Columns to Display")));
    add(panel);
    boxes = new ColCheckBox[DataTableModel.columnNames.length];
    for (int i = 0; i < DataTableModel.columnNames.length; i++) {
      boxes[i] = new ColCheckBox (DataTableModel.columnNames[i], i);
      panel.add(boxes[i]);
    }
    //some default settings
    boxes[2].setSelected(true);
    boxes[3].setSelected(true);
    boxes[9].setSelected(true);
    boxes[10].setSelected(true);
    boxes[16].setSelected(true);
    pack();
  }
  
  public void addItemListener(ItemListener listener){
    for (ColCheckBox box : boxes){
      box.addItemListener(listener);
    }
  }

  public boolean getChecked(int i){
    if (i < boxes.length && i > -1) {
      return boxes[i].isSelected();
    }
    return false;
  }
  
}
