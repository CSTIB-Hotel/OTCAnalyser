package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class AnalysisSummary extends JPanel {

	private static final long serialVersionUID = 1L;
	/* returning
	underlying asset
	price (rounded notional amount)
	execution timestamp
  */	
	
	TradeDisplayer analysisInfo = new TradeDisplayer();

	private JLabel mostTraded = new JLabel("Most Traded Underlying Asset: N/A");
	private JLabel numberOfTrades = new JLabel("Number of Trades: 0");

	private static AnalysisSummary analysisSummary;
	
	public static AnalysisSummary getInstance() {
		if (analysisSummary == null) {
			analysisSummary = new AnalysisSummary();
		}
		return analysisSummary;
	}
	
	AnalysisSummary() {
		this.setBorder(BorderFactory.createEtchedBorder());
		GridLayout layout = new GridLayout(1,2);
		layout.minimumLayoutSize(this);
		this.setLayout(layout);
		add(mostTraded);
		add(numberOfTrades);
		setVisible(true);
	}

	public void UpdateWindow(String mostTradedAsset, int numTrades) {
		remove(mostTraded);
		remove(numberOfTrades);
		
		if (numTrades > 0) {
		  mostTraded = new CenteredJLabel("Most Traded Underlying Asset: " + mostTradedAsset);
		} else {
			mostTraded = new CenteredJLabel("Most Traded Underlying Asset: N/A");
		}
	  mostTraded.setBorder(new EmptyBorder(1,5,1,5));
	  
		numberOfTrades = new CenteredJLabel("Number of Trades: " + numTrades);
		numberOfTrades.setBorder(new EmptyBorder(1,5,1,5));

	  add(mostTraded);
		add(numberOfTrades);
		setVisible(true);
		repaint();
	}
	
}
