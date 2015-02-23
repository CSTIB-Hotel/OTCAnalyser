package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class AnalysisSummary extends JPanel {

	private static final long serialVersionUID = 1L;
	/* returning
	underlying asset
	price (rounded notional amount)
	execution timestamp
*/	
	TradeDisplayer smallestTrade = new TradeDisplayer("Smallest trade");
	TradeDisplayer biggestTrade = new TradeDisplayer("Biggest trade");
	TradeDisplayer otherInfo = new TradeDisplayer();
	
	private AnalysisItem maxTrade;
	private AnalysisItem minTrade;
	private double average;
	private String mostTraded;
	private String leastTraded;
	private String currency;
	private double numberOfTrades;
	private double changeInAverageCost;
	
	public static void main(String[] args) {
		getInstance();
	}

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
		add(otherInfo);
		setVisible(true);
	}

	public void UpdateWindow(AnalysisItem Biggest,AnalysisItem Smallest, double Average,String MostTraded,
			String LeastTraded,String Currency,double NumTrades,double changeInCost) {
		maxTrade = Biggest;
		minTrade = Smallest;
		average = Average;
		mostTraded = MostTraded;
		leastTraded = LeastTraded;
		currency = Currency;
		numberOfTrades = NumTrades;
		changeInAverageCost = changeInCost;
		update();
	}
	
	private void update() {
		biggestTrade.update(maxTrade);
		smallestTrade.update(minTrade);
		otherInfo.update(average,mostTraded,leastTraded,currency,numberOfTrades,changeInAverageCost);
	}
	
}
