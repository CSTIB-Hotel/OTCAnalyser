package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchListener;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;

public class GUI extends JFrame implements SearchListener {

	private static final long serialVersionUID = 1L;
	
	public StatusBar statusBar;
	
	public static GUI gui;
	static SearchWindow searchWindow;
	DataViewer dataViewer;
	
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
		searchWindow = SearchWindow.getInstance();
		add(searchWindow,BorderLayout.WEST);
		searchWindow.setVisible(true);
		statusBar = StatusBar.getInstance();
		add(statusBar,BorderLayout.SOUTH);
		statusBar.setVisible(true);
		dataViewer = DataViewer.dataViewer;
		this.add(dataViewer);
		dataViewer.setVisible(true);
		this.setVisible(true);
	}
	
	@Override
	public void getSearchResult(SearchResult s) {
		DataViewer.clearTrades();
		DataViewer.addTrades(s.getResultData());
	}

}
