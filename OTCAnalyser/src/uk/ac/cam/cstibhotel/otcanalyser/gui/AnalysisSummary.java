package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class AnalysisSummary extends JPanel {
/* returning
	underlying asset
	price (rounded notional amount)
	execution timestamp
*/	
	
	public static void main(String[] args) {
		getInstance();
	}
	
	private static final long serialVersionUID = 1L;

	private static AnalysisSummary analysisSummary;
	
	public static AnalysisSummary getInstance() {
		if (analysisSummary == null) {
			analysisSummary = new AnalysisSummary();
		}
		return analysisSummary;
	}
	
	AnalysisSummary() {
		setSize(600,50);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(biggestTrade);
		add(smallestTrade);
		setVisible(true);
	}
	
	TradeDisplayer smallestTrade = new TradeDisplayer("Smallest trade");
	TradeDisplayer biggestTrade = new TradeDisplayer("Biggest trade");

	public void UpdateWindow(AnalysisItem Biggest,AnalysisItem Smallest, long Average,String MostTraded,
			String LeastTraded,String Currency,long NumTrades,long changeInCost) {
		maxTrade = Biggest;
		minTrade = Smallest;
		average = Average;
		mostTraded = MostTraded;
		leastTraded = LeastTraded;
		currency = Currency;
		numberOfTrades = NumTrades;
		changeInAverageCost = changeInCost;
		//update();
	}
	
	
	
	private AnalysisItem maxTrade;
	private AnalysisItem minTrade;
	private long average;
	private String mostTraded;
	private String leastTraded;
	private String currency;
	private long numberOfTrades;
	private long changeInAverageCost;
	
}
