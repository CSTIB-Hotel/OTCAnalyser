package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.SearchWindow;
import uk.ac.cam.cstibhotel.otcanalyser.gui.StatusBar;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;

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
	public static void search() throws ParseException {
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
		String monthString = (String) SearchWindow.getInstance().StartDate.Months.getSelectedItem();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(monthString));
		int month = cal.get(Calendar.MONTH);
		int year = (int) SearchWindow.getInstance().StartDate.Year.getSelectedItem();
		cal.set(year, month, day);
		Date startTime = cal.getTime();
		s.setStartTime(startTime);
		
		day = (int) SearchWindow.getInstance().EndDate.Day.getSelectedItem();
		monthString = (String) SearchWindow.getInstance().EndDate.Months.getSelectedItem();
		cal.setTime(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(monthString));
		month = cal.get(Calendar.MONTH);
		year = (int) SearchWindow.getInstance().EndDate.Year.getSelectedItem();
		cal.set(year, month, day);
		Date endTime = cal.getTime();
		s.setEndTime(endTime);
		
		String fullTaxonomy = "";
		/*
		 * Can't just use getSelectedItem(), because UPI isn't expecting
		 * "Foreign Exchange" but rather "ForeignExchange" and not "Interest"
		 * but "InterestRate"!
		 */
		if (SearchWindow.getInstance().tax.Asset.getSelectedIndex() != 1 &&
			SearchWindow.getInstance().tax.Asset.getSelectedIndex() != 3) {
			fullTaxonomy += SearchWindow.getInstance().tax.Asset.getSelectedItem();
			System.out.println(SearchWindow.getInstance().tax.Asset.getSelectedItem());
		} else if (SearchWindow.getInstance().tax.Asset.getSelectedIndex() == 1) {
			fullTaxonomy += "InterestRate";
		} else {
			fullTaxonomy += "ForeignExchange";
		}
		fullTaxonomy += ":";
		fullTaxonomy += SearchWindow.getInstance().tax.BaseClass.getSelectedItem();
		fullTaxonomy += ":";
		fullTaxonomy += SearchWindow.getInstance().tax.SubClass.getSelectedItem();
		s.setUPI(fullTaxonomy);
		
		// Set the asset class based on the value in the drop-down box
		switch ((String) SearchWindow.getInstance().tax.Asset.getSelectedItem()) {
		case "Credit":
			s.setAssetClass(AssetClass.CREDIT);
			break;
		case "Interest":
			s.setAssetClass(AssetClass.RATES);
			break;
		case "Commodity":
			s.setAssetClass(AssetClass.COMMODITY);
			break;
		case "Foreign Exchange":
			s.setAssetClass(AssetClass.FOREX);
			break;
		case "Equity":
			s.setAssetClass(AssetClass.EQUITY);
		}
		
		// Get the result from the database
		SearchResult result = Database.getDB().search(s);
		
		// Send it to each member of searchListeners
		for (SearchListener l : searchListeners) {
			l.getSearchResult(result);
		}
	}
	
}
