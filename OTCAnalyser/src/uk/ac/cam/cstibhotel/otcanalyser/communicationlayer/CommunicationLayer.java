package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import java.util.ArrayList;
import java.util.Date;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.SearchWindow;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;

public class CommunicationLayer {
	
	// A list of all components expecting the results of a search query
	private static ArrayList<SearchListener> searchListeners;
	
	// Singleton instance of the communication layer
	private static CommunicationLayer communicationLayer;
	
	public static CommunicationLayer getInstance() {
		if (communicationLayer == null) {
			communicationLayer = new CommunicationLayer();
		}
		return communicationLayer;
	}
	
	private CommunicationLayer() {
		searchListeners = new ArrayList<SearchListener>();
	}
	
	// Adds a listener to the list of searchListeners to allow them to
	// receive results of a query
	public static void registerListener(SearchListener s) {
		searchListeners.add(s);
	}
	
	// Creates a Search and then sends it to the database
	public static void search() {
		Search s = new Search();
		
		//TODO: Any of these missing properties are ones where I have very little idea of how to
		// get them
		s.setTradeType();
		s.setAssetClass();
		s.setAsset(SearchWindow.getInstance().UnderLyingAsset.getText());
		s.setMinPrice();
		s.setMaxPrice();
		s.setCurrency();
		s.setStartTime(new Date());
		s.setEndTime(new Date());
		
		String fullTaxonomy;
		fullTaxonomy += SearchWindow.getInstance().tax.Asset.getSelectedItem();
		fullTaxonomy += ":";
		fullTaxonomy += SearchWindow.getInstance().tax.BaseClass.getSelectedItem();
		fullTaxonomy += ":";
		fullTaxonomy += SearchWindow.getInstance().tax.SubClass.getSelectedItem();
		UPI taxonomy = new UPI(fullTaxonomy);
		s.setUPI(taxonomy);
		
		// Get the result from the database
		SearchResult result = Database.search(s);
		
		// Send it to each member of searchListeners
		for (SearchListener l : searchListeners) {
			l.getSearchResult(result);
		}
	}
	
}
