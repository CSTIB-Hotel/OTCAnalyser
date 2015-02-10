package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class AnalysisWindow extends CBLPanel {
	
	  private static final long serialVersionUID = 1L;
	  
	  private class AnalysisPanel extends ContentPanel {
		  
		  private static final long serialVersionUID = 1L;
		  
		  public AnalysisPanel(String text) {
			  super();
			  setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10),
			      BorderFactory.createEtchedBorder()));
			  JLabel analysis = new JLabel(text);
			  analysis.setBorder(new EmptyBorder(5,5,5,5));
			  analysis.setOpaque(false);
			  add(analysis);
		  }
	  }
	  
	  public AnalysisWindow() {
		  super();
		  setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	  }
	  
	  public void addAnalysis(String analysis) {
		  add(new AnalysisPanel(analysis));
	  }

}
