package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.Dimension;
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
	HelpPane help = HelpPane.getInstance();
	
	public static GUI getInstance() {
		if (gui==null) {
			gui = new GUI();
		}
		return gui;
	}
	
	public GUI() {
		setTitle("OTC Analyser");
		setMinimumSize(new Dimension(1250,760));
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowCloseListener());
		GridBagConstraints helpPaneConstraints = new GridBagConstraints();
		helpPaneConstraints.fill = GridBagConstraints.HORIZONTAL;
		//add(help,helpPaneConstraints);
		GridBagConstraints searchWindowConstraints = new GridBagConstraints();
		searchWindowConstraints.gridx = 0;
		searchWindowConstraints.gridy = 1;
		searchWindowConstraints.gridheight = 1;
		searchWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		searchWindowConstraints.fill = GridBagConstraints.VERTICAL;
		add(searchWindow,searchWindowConstraints);
		GridBagConstraints statusBarConstraints = new GridBagConstraints();
		statusBarConstraints.gridx = 0;
		statusBarConstraints.gridy = 3;
		statusBarConstraints.gridwidth = 0;
		add(statusBar,statusBarConstraints);
		GridBagConstraints analysisSummaryConstraints = new GridBagConstraints();
		analysisSummaryConstraints.gridx = 0;
		analysisSummaryConstraints.gridy = 2;
		analysisSummaryConstraints.gridwidth = 0;
		analysisSummaryConstraints.fill = GridBagConstraints.HORIZONTAL;
		analysisSummaryConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(analysis,analysisSummaryConstraints);
		GridBagConstraints dataViewerConstraints = new GridBagConstraints();
		dataViewerConstraints.gridx = 1;
		dataViewerConstraints.gridy = 0;
		dataViewerConstraints.fill = GridBagConstraints.BOTH;
		dataViewerConstraints.weightx = 1;
		dataViewerConstraints.weighty = 1;
		dataViewerConstraints.gridheight = 2;
		add(dataViewer,dataViewerConstraints);
		setVisible(true);
	}
	
	@Override
	public void getSearchResult(SearchResult s) {
		DataViewer.clearTrades();
		DataViewer.addTrades(s.getResultData());
	}
	
	public void addAnalyses(
    String mostTraded,
    int numResults,
    List<AnalysisItem> maxWithCurrency,
    List<AnalysisItem> minWithCurrency,
    List<AnalysisItem> avgWithCurrency) {
		AnalysisSummary.getInstance().UpdateWindow(mostTraded, numResults);
		DataViewer.addAnalysis(maxWithCurrency, minWithCurrency, avgWithCurrency);
	}

}
