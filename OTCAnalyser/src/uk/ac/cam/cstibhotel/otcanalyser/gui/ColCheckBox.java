package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.JCheckBox;

public class ColCheckBox extends JCheckBox {
  int colNumber;
  public ColCheckBox(String name, int colNumber) {
    super(name);
    this.colNumber = colNumber;
  }
}
