package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.JPanel;

public class AnalysisSummary extends JPanel {
/* returning
	underlying asset
	price (rounded notional amount)
	execution timestamp
*/	
	private static final long serialVersionUID = 1L;

	private static AnalysisSummary analysisSummary;
	
	//public AnalysisItem maxTrade;
	//public AnalysisItem minTrade;
	
	public static AnalysisSummary getInstance() {
		if (analysisSummary == null) {
			analysisSummary = new AnalysisSummary();
		}
		return analysisSummary;
	}
	
}
