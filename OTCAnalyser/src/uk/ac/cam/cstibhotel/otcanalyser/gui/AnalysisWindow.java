package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class AnalysisWindow extends CurrencyToolbarWindow {
	
	  private static final long serialVersionUID = 1L;
	  private List<ContentPanel> contentPanel = new ArrayList<>();
	  private ContentPanel currentPanel;
	  private ContentPanel pnl;
	  
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
		  pnl = new ContentPanel();
		  pnl.setBorder(new EmptyBorder(0,0,0,0));
		  add(pnl, BorderLayout.CENTER);
	  }
	  
	  public void addContentPanel(String currency) {
	  	ContentPanel cPnl = new ContentPanel();
	  	cPnl.setBorder(new EmptyBorder(5,5,5,5));
		  cPnl.setLayout(new BoxLayout(cPnl, BoxLayout.Y_AXIS));
	  	contentPanel.add(cPnl);
	  	cPnl.setBorder(BorderFactory.createEtchedBorder());
	  	currencies.add(currency);
	    if (currencies.size() > 1) {
	    	makeToolBar();
	    }
	  }
	  
	  //add analysis without title
	  public void addAnalysis(String analysis, String currency) {
	  	if (!currencies.contains(currency)) {
	  		addContentPanel(currency);
	  	}
	  	int i = currencies.indexOf(currency);
	  	contentPanel.get(i).add(new AnalysisPanel(analysis));
	  	currentPanel = contentPanel.get(0);
	  	pnl.add(currentPanel);
	  	repaint();
	  }
	  
	  //add analysis with title
	  public void addAnalysis(String analysis, String title, String currency) {
	  	if (!currencies.contains(currency)) {
	  		addContentPanel(currency);
	  	}
	  	int i = currencies.indexOf(currency);
	  	contentPanel.get(i).add(new AnalysisPanel(analysis, title));
	  	currentPanel = contentPanel.get(0);
	  	pnl.add(currentPanel);
	  	repaint();
	  }
	  
	  public void clear() {
	  	contentPanel = new ArrayList<>();
	  	currencies = new ArrayList<>();
	  	pnl.removeAll();
	  	if (showToolbar) {
	  	  remove(toolbar);
	  	  showToolbar = false;
	  	};
	  }
	  
	  @Override
	  public void actionPerformed(ActionEvent e){
	    if (currencies.contains(e.getActionCommand())) {
	    	pnl.remove(currentPanel);
	    	currentPanel = contentPanel.get(currencies.indexOf(e.getActionCommand()));
	    	pnl.add(currentPanel);
	    	pnl.repaint();
	    	repaint();
	    }
	  }
	  
}
