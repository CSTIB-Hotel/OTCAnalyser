package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchListener;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class GUI extends JFrame implements SearchListener {

	private static final long serialVersionUID = 1L;
	
	
	public static GUI gui;
	static SearchWindow searchWindow = SearchWindow.getInstance();
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
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(searchWindow,BorderLayout.WEST);
		add(statusBar,BorderLayout.SOUTH);
		add(analysis,BorderLayout.EAST);
		add(dataViewer);
		setVisible(true);
	}
	
	@Override
	public void getSearchResult(SearchResult s) {
		DataViewer.clearTrades();
		DataViewer.addTrades(s.getResultData());
	}
	
	public void addAnalyses(AnalysisItem max, AnalysisItem min, List<AnalysisItem> avgs, double stddev) {
		//TODO: deal with AnalysisSummary here
		DataViewer.addAnalysis(max, min, avgs, stddev);
	}

}
