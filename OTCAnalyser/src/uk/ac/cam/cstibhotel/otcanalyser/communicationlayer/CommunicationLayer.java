package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.SearchWindow;
import uk.ac.cam.cstibhotel.otcanalyser.gui.StatusBar;
import uk.ac.cam.cstibhotel.otcanalyser.trade.EmptyTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.InvalidTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;
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

		String tradeType = (String) SearchWindow.getInstance().TradeType.getSelectedItem();
		if (tradeType.equals("Swap")) {
			s.setTradeType(TradeType.SWAP);
		} else if (tradeType.equals("Option")) {
			s.setTradeType(TradeType.OPTION);
		}
		
		s.setAsset(SearchWindow.getInstance().UnderLyingAsset.getText());
		
		try {
			s.setMinPrice(Integer.parseInt(SearchWindow.getInstance().minValue.getText()));
			s.setMaxPrice(Integer.parseInt(SearchWindow.getInstance().maxValue.getText()));
		} catch (NumberFormatException e) {
			StatusBar.setMessage("Error: Price fields must contain integers", 1);
		}

		s.setCurrency(SearchWindow.getInstance().currency.getText());
		
		int day = (int) SearchWindow.getInstance().StartDate.Day.getSelectedItem();
		int month = (int) SearchWindow.getInstance().StartDate.Months.getSelectedItem();
		int year = (int) SearchWindow.getInstance().StartDate.Year.getSelectedItem();
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		Date startTime = cal.getTime();
		s.setStartTime(startTime);
		
		day = (int) SearchWindow.getInstance().EndDate.Day.getSelectedItem();
		month = (int) SearchWindow.getInstance().EndDate.Months.getSelectedItem();
		year = (int) SearchWindow.getInstance().EndDate.Year.getSelectedItem();
		cal.set(year, month, day);
		Date endTime = cal.getTime();
		s.setEndTime(endTime);
		
		String fullTaxonomy = null;
		fullTaxonomy += SearchWindow.getInstance().tax.Asset.getSelectedItem();
		fullTaxonomy += ":";
		fullTaxonomy += SearchWindow.getInstance().tax.BaseClass.getSelectedItem();
		fullTaxonomy += ":";
		fullTaxonomy += SearchWindow.getInstance().tax.SubClass.getSelectedItem();
		try {
			UPI taxonomy = new UPI(fullTaxonomy);
			s.setUPI(taxonomy);
		} catch (InvalidTaxonomyException | EmptyTaxonomyException e) {
			StatusBar.setMessage("Error: Invalid taxonomy " + fullTaxonomy, 1);
		}
		
		s.setAssetClass(s.getUPI().getAssetClass());
		
		// Get the result from the database
		SearchResult result = Database.getDB().search(s);
		
		// Send it to each member of searchListeners
		for (SearchListener l : searchListeners) {
			l.getSearchResult(result);
		}
	}
	
}
