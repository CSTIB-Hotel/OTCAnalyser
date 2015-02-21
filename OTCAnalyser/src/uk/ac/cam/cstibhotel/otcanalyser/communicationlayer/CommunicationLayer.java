package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.SearchWindow;
import uk.ac.cam.cstibhotel.otcanalyser.gui.StatusBar;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPIStrings;

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
	
	/*
	 * Builds a Search object from the currently-selected parameters in the SearchWindow and returns
	 * it.
	 */
	public static Search createSearch() throws ParseException {
		Search s = new Search();

		String tradeType = (String) SearchWindow.getInstance().TradeType.getSelectedItem();
		if (tradeType.equals("Swap")) {
			s.setTradeType(TradeType.SWAP);
		} else if (tradeType.equals("Option")) {
			s.setTradeType(TradeType.OPTION);
		}
		
		s.setAsset(SearchWindow.getInstance().UnderLyingAsset.getText());
		
		try {
			s.setMinPrice(Math.max(0,
					(int) SearchWindow.getInstance().minValue.getValue()));
			s.setMaxPrice(Math.max(0,
					(int) SearchWindow.getInstance().maxValue.getValue()));
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
		String selectedAsset = (String) SearchWindow.getInstance().tax.Asset.getSelectedItem();
		
		int assetIndex = SearchWindow.getInstance().tax.Asset.getSelectedIndex();
		int baseIndex = SearchWindow.getInstance().tax.BaseClass.getSelectedIndex();
		int subIndex = SearchWindow.getInstance().tax.SubClass.getSelectedIndex();
		
		// Add the Asset to the taxonomy string
		fullTaxonomy += UPIStrings.Assets[assetIndex];
		fullTaxonomy += ":";
		
		/* 
		 * Add the Base Product and Sub-product to the taxonomy string
		 * Also set the AssetClass while we're here to make code slightly neater
		 */
		switch (selectedAsset) {
		case "Credit":
			s.setAssetClass(AssetClass.CREDIT);
			fullTaxonomy += UPIStrings.CreditBaseProducts[baseIndex];
			fullTaxonomy += ":";
			if (UPIStrings.CreditSubProducts[baseIndex].length != 0) {
				fullTaxonomy += UPIStrings.CreditSubProducts[baseIndex][subIndex];
			}
			break;
		case "Interest":
			s.setAssetClass(AssetClass.RATES);
			fullTaxonomy += UPIStrings.InterestBaseProducts[baseIndex];
			fullTaxonomy += ":";
			if (UPIStrings.InterestSubProducts[baseIndex].length != 0) {
				fullTaxonomy += UPIStrings.InterestSubProducts[baseIndex][subIndex];
			}
			break;
		case "Commodity":
			s.setAssetClass(AssetClass.COMMODITY);
			fullTaxonomy += UPIStrings.CommodityBaseProducts[baseIndex];
			fullTaxonomy += ":";
			if (UPIStrings.CommoditySubProducts[baseIndex].length != 0) {
				fullTaxonomy += UPIStrings.CommoditySubProducts[baseIndex][subIndex];
			}
			break;
		case "Foreign Exchange":
			s.setAssetClass(AssetClass.FOREX);
			fullTaxonomy += UPIStrings.ForexBaseProducts[baseIndex];
			fullTaxonomy += ":";
			if (UPIStrings.ForexSubProducts[baseIndex].length != 0) {
				fullTaxonomy += UPIStrings.ForexSubProducts[baseIndex][subIndex];
			}
			break;
		case "Equity":
			s.setAssetClass(AssetClass.EQUITY);
			fullTaxonomy += UPIStrings.EquityBaseProducts[baseIndex];
			fullTaxonomy += ":";
			if (UPIStrings.EquitySubProducts[baseIndex].length != 0) {
				fullTaxonomy += UPIStrings.EquitySubProducts[baseIndex][subIndex];
			}
			break;
		}
		
		s.setUPI(fullTaxonomy);
		
		return s;
	}
	
	/*
	 * Takes a Search and loads its values into the GUI for the user.
	 */
	public static void loadSearch(Search s) {
		// Set the trade type
		if (s.getTradeType() == TradeType.SWAP) {
			SearchWindow.getInstance().TradeType.setSelectedItem("Swap");
		} else if (s.getTradeType() == TradeType.OPTION) {
			SearchWindow.getInstance().TradeType.setSelectedItem("Option");
		}
		
		// Set the underlying asset
		SearchWindow.getInstance().UnderLyingAsset.setText(s.getAsset());
		
		// Set the minimum price
		SearchWindow.getInstance().minValue.setValue(s.getMinPrice());
		
		// Set the maximum price
		SearchWindow.getInstance().maxValue.setValue(s.getMaxPrice());
		
		// Set the currency
		SearchWindow.getInstance().currency.setText(s.getCurrency());
		
		// Set the start date and end date
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(s.getStartTime());
		SearchWindow.getInstance().StartDate.Day.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
		SearchWindow.getInstance().StartDate.Months.setSelectedIndex(cal.get(Calendar.MONTH));
		SearchWindow.getInstance().StartDate.Year.setSelectedItem(cal.get(Calendar.YEAR));
		
		cal.setTime(s.getEndTime());
		SearchWindow.getInstance().EndDate.Day.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
		SearchWindow.getInstance().EndDate.Months.setSelectedIndex(cal.get(Calendar.MONTH));
		SearchWindow.getInstance().EndDate.Year.setSelectedItem(cal.get(Calendar.YEAR));
		
		// Set the asset class
		switch (s.getAssetClass()) {
		case COMMODITY:
			SearchWindow.getInstance().tax.Asset.setSelectedItem("Commodity");
			break;
		case RATES:
			SearchWindow.getInstance().tax.Asset.setSelectedItem("Interest");
			break;
		case CREDIT:
			SearchWindow.getInstance().tax.Asset.setSelectedItem("Credit");
			break;
		case EQUITY:
			SearchWindow.getInstance().tax.Asset.setSelectedItem("Equity");
			break;
		case FOREX:
			SearchWindow.getInstance().tax.Asset.setSelectedItem("Foreign Exchange");
			break;
		}
		
		//TODO Set the base product
		
		//TODO Set the sub-product
	}
	
	/*
	 * Adds a listener to the list of searchListeners to allow them to
	 * receive results of a query
	 */
	public static void registerListener(SearchListener s) {
		searchListeners.add(s);
	}
	
	/*
	 * Builds a Search and attempts to save it in the database. If this is unsuccessful, puts an
	 * error message in the StatusBar.
	 */
	public static void saveSearch(String name) throws ParseException {
		// Build a search
		Search s = createSearch();
		
		boolean success = Database.getDB().saveSearch(s, name);
		
		if (!success) {
			StatusBar.setMessage("Error: could not save search", 1);
		}
	}
	
	/*
	 * Builds a Search, sends the query to the database and then passes the result to any of the
	 * SearchListeners registered to receive it.
	 */
	public static void search() throws ParseException {
		// Build a search
		Search s = createSearch();
		
		// Get the result from the database
		SearchResult result = Database.getDB().search(s);
		
		// Send it to each member of searchListeners
		for (SearchListener l : searchListeners) {
			l.getSearchResult(result);
		}
	}
	
}
