package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JFrame;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchListener;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class GUI extends JFrame implements SearchListener {

	private static final long serialVersionUID = 1L;
	
	public static GUI gui;
	SearchWindow searchWindow = SearchWindow.getInstance();
	DataViewer dataViewer = DataViewer.dataViewer;
	StatusBar statusBar = StatusBar.getInstance();
	AnalysisSummary analysis = AnalysisSummary.getInstance();
	
	public static GUI getInstance() {
		if (gui==null) {
			gui = new GUI();
		}
		return gui;
	}
	
	public GUI() {
		setTitle("OTC Analyser");
		setSize(1050,700);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagConstraints searchWindowConstraints = new GridBagConstraints();
		searchWindowConstraints.gridx = 0;
		searchWindowConstraints.gridy = 0;
		add(searchWindow,searchWindowConstraints);
		GridBagConstraints statusBarConstraints = new GridBagConstraints();
		statusBarConstraints.gridx = 0;
		statusBarConstraints.gridy = 2;
		add(statusBar,statusBarConstraints);
		GridBagConstraints analysisSummaryConstraints = new GridBagConstraints();
		analysisSummaryConstraints.gridx = 1;
		analysisSummaryConstraints.gridy = 1;
		add(analysis,analysisSummaryConstraints);
		GridBagConstraints dataViewerConstraints = new GridBagConstraints();
		dataViewerConstraints.gridx = 1;
		dataViewerConstraints.gridy = 0;
		add(dataViewer);
		setVisible(true);
	}
	
	@Override
	public void getSearchResult(SearchResult s) {
		DataViewer.clearTrades();
		DataViewer.addTrades(s.getResultData());
	}
	
	public void addAnalyses(AnalysisItem max, AnalysisItem min, List<AnalysisItem> avgs, double stddev, String curr,
	    String mostTradedAsset, String leastTradedAsset, double numTrades, double changeInCost, double avg) {
		/*
		 * Uses all values for max, min, and avg.
		 * Currency is that of the change in average cost, which is just the first one in the results.
		 */
		if (max != null && min != null) {
		AnalysisSummary.getInstance().UpdateWindow(max, min, avg, mostTradedAsset,
						    leastTradedAsset, curr, numTrades, changeInCost);
		}
		DataViewer.addAnalysis(max, min, avgs, stddev);
	}

}
