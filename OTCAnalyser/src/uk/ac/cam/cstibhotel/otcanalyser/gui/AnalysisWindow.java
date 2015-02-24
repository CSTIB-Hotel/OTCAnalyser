package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class AnalysisWindow extends CBLPanel {
	
	  private static final long serialVersionUID = 1L;
	  private ContentPanel content;
	  
	  private class AnalysisPanel extends JTextPane {
		  
	    private static final long serialVersionUID = 1L;
		  
		public AnalysisPanel(String text) {
		  super();
		  setEditable(false);
		  setText(text);
		  setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,0,5,0),
				  BorderFactory.createEtchedBorder()));
	    }
		
		public AnalysisPanel(String text, String title) {
			super();
			setEditable(false);
			setText(text);
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
			setOpaque(false);
		}
          
	  }
	  
	  public AnalysisWindow() {
		  super();
		  content = new ContentPanel();
		  content.setBorder(new EmptyBorder(5,5,5,5));
		  JScrollPane sp = new JScrollPane(content);
		  sp.getViewport().setOpaque(false);
		  sp.setOpaque(false);
		  sp.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,5,5,5),
			      BorderFactory.createEtchedBorder()));
		  content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		  add(sp);
	  }
	  
	  public void addAnalysis(String analysis) {
		  content.add(new AnalysisPanel(analysis));
	  }
	  
	  public void addAnalysis(String analysis, String title) {
		  content.add(new AnalysisPanel(analysis, title));
	  }
	  
	  public void clear() {
	  	content.removeAll();
	  }
	  
}
