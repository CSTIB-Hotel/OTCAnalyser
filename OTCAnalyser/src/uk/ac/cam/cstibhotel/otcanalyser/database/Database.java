package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.EmptyTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.InvalidTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;

/**
 *
 * @author Wai-Wai Ng
 */
public class Database {

	private static Database db;
	private static Connection connection;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Database d = getDB();
		db.addTrade(new Trade());
	}
	
	private static String getDatabasePath(){
		String os = System.getProperty("os.name");
		
		return "database.db";
		
//		if(os.contains("Windows")){
//			return "C:\\Program Files/OTCAnalyser/database.db";
//		} else if (os.contains("Mac")){
//			return "~/Library/OTCAnalyser/database.db";
//		} else {
//			return "/usr/share/OTCAnalyser/database.db";
//		}
	}

	public static Database getDB() {
		if (db==null) {
			try {
				db = new Database();
			} catch (SQLException ex) {
				System.err.println("There was a severe database error");
				System.exit(1); // TODO we probably don't want to actulaly quit
			} catch (ClassNotFoundException e) {
				System.err.println("There was a severe database error");
				System.exit(2); // TODO we probably don't want to actulaly quit

			}
		}
		return db;
	}

	private Database() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:file:" + getDatabasePath());
		connection.setAutoCommit(false);

		connection.createStatement().execute("SET WRITE_DELAY FALSE"); // always update data on disk

		createDataTable();
		createInfoTable();
	}

	private void createDataTable() {
		StringBuilder dataTableCreator = new StringBuilder("CREATE TABLE data (");

		HashMap<String, SQLField> DBNameDBType = TradeFieldMapping.getMapping(new Trade());
		Iterator<Entry<String, SQLField>> i = DBNameDBType.entrySet().iterator();

		while (i.hasNext()) {
			Entry<String, SQLField> mapEntry = i.next();
			dataTableCreator.append(mapEntry.getKey()).append(" ").append(mapEntry.getValue().getType()).append(", ");
		}

		dataTableCreator.setLength(dataTableCreator.length()-2);
		dataTableCreator.append(");");

		try {
			connection.createStatement().execute(dataTableCreator.toString());
		} catch (SQLException e) {
			// do nothing as this just means the data table already exists
		}
	}

	private void createInfoTable() {
		String infoTableCreator = "CREATE TABLE info ( key VARCHAR(255), value VARCHAR(255)";
		try {
			connection.createStatement().execute(infoTableCreator);

			PreparedStatement ps = connection.prepareStatement("INSERT INTO info (key, value) VALUES (?, ?)");
			ps.setString(1, "last_update");
			ps.setString(2, "0");
			ps.executeQuery();
		} catch (SQLException e) {
			// do nothing as this just means the info table already exists
		}
	}

	/**
	 * Adds a trade to the database
	 *
	 * @param trade a trade to be added to the database
	 * @return true if the database was successfully updated
	 */
	public boolean addTrade(Trade trade) {
		HashMap<String, SQLField> DBNameValue = TradeFieldMapping.getMapping(trade);
		Iterator<Entry<String, SQLField>> iterator = DBNameValue.entrySet().iterator();
		
		String executeString;
		
		if(trade.getAction().equals(Action.CANCEL)) {
			deleteTrade(trade.getDisseminationID());
			return true;
		} else if(trade.getAction().equals(Action.CORRECT)) {
			executeString = buildUpdateString(iterator, trade.getDisseminationID());
		} else { // new entry
			executeString = buildInsertString(iterator);
		}

		try {
			PreparedStatement p = connection.prepareStatement(executeString);

			iterator = DBNameValue.entrySet().iterator();
			while (iterator.hasNext()) {
				iterator.next().getValue().addToPreparedStatement(p);
			}

			p.execute();
		} catch (SQLException e) {
			System.err.println("Failed to insert/update row");
			return false;
		}

		updateLastUpdateTime(trade);
		return true;
	}
	
	/**
	 * 
	 * @param iterator An iterator over a DB mapping
	 * @return An SQL INSERT string
	 */
	private String buildInsertString(Iterator<Entry<String, SQLField>> iterator){
		StringBuilder a = new StringBuilder("INSERT INTO data (");
		StringBuilder b = new StringBuilder(") VALUES (");

		int index = 1;
		while (iterator.hasNext()) {
			Entry<String, SQLField> mapEntry = iterator.next();
			a.append(mapEntry.getKey()).append(", ");
			b.append("?, ");
			mapEntry.getValue().index = index;
			index++;
		}
		a.setLength(a.length()-2);
		b.setLength(b.length()-2);
		a.append(b).append(")");
		
		return a.toString();
	}
	
	/**
	 * 
	 * @param iterator An iterator over a DB mapping
	 * @param origId The id of the trade to update
	 * @return An SQL update string
	 */
	private String buildUpdateString(Iterator<Entry<String, SQLField>> iterator, long origId){
		StringBuilder s = new StringBuilder("UPDATE data SET ");
		
		int index = 1;
		
		while (iterator.hasNext()) {
			Entry<String, SQLField> mapEntry = iterator.next();
			s.append(mapEntry.getKey()).append(" = ?, ");
			mapEntry.getValue().index = index;
			index++;
		}
		
		s.setLength(s.length() - 2);
		s.append(" WHERE id = ").append(origId);
		
		return s.toString();
	}
	
	/**
	 * 
	 * @param id The id of the trade to delete
	 * @return Whether the deletion was successful
	 */
	private boolean deleteTrade(long id){
		String deletionString = "DELETE FROM data WHERE id = " + id;
		try {
			connection.createStatement().execute(deletionString);
		} catch (SQLException ex) {
			System.err.println("Error deleting a trade");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param trade the trade to use to calculate the last update time
	 */
	private void updateLastUpdateTime(Trade trade){
		java.util.Date thisUpdateTime = trade.getExecutionTimestamp(); // is this the right date?
		java.util.Date lastUpdateTime = getLastUpdateTime();
		
		if (thisUpdateTime.after(lastUpdateTime)) {
			try {
				PreparedStatement ps = connection.prepareCall("UPDATE info SET value = ? WHERE key = last_update");
				ps.setString(1, Long.toString(thisUpdateTime.getTime()));
			} catch (SQLException e) {
				System.err.println("Failed to update last update time");
			}
		}
	}
	
	/**
	 *
	 * @return The time the database was last updated
	 */
	public java.util.Date getLastUpdateTime() {
		try {
			Statement s = connection.createStatement();
			s.execute("SELECT value FROM info WHERE key = last_update");
			String timeString = s.getResultSet().getString(1);
			return new java.util.Date(Long.getLong(timeString));
		} catch (SQLException ex) {
			System.err.println("Could not get the last update time");
			return new java.util.Date(0);
		}
	}

	/**
	 *
	 * @param s the search parameters
	 * @return all data matching the search
	 */
	/*
	 private TradeType tradeType;
	 private AssetClass assetClass;
	 private String asset;
	 private int minPrice, maxPrice;
	 private Currency currency;
	 private Date startTime, endTime;
	 private UPI upi;	
	
	 */
	public SearchResult search(Search s) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM data WHERE "
					+"tradeType = ? AND"
					+"assetClass = ? AND"
					+"taxonomy = ? AND"
					+"optionStrikePrice >= ? AND"
					+"optionStrikePrice <= ? AND"
					+"currency = ? AND"
					+"startTime >= ? AND"
					+"endTime <= ? AND"
					+"1 = 1");
			ps.setShort(1, s.getTradeType().getValue());
			ps.setShort(2, s.getAssetClass().getValue());
			ps.setString(3, s.getAsset());
			ps.setFloat(4, s.getMinPrice());
			ps.setFloat(5, s.getMaxPrice());
			ps.setString(6, s.getCurrency());
			ps.setTimestamp(7, new Timestamp(s.getStartTime().getTime()));
			ps.setTimestamp(8, new Timestamp(s.getEndTime().getTime()));
			ps.execute();

			return null;
		} catch (SQLException ex) {
			System.err.println("Search failed");
			return new SearchResult(new LinkedList<Trade>(), 0);
		}
	}

	/**
	 *
	 * @param s the search to save
	 * @return true if the search was successfully saved
	 */
	public boolean saveSearch(Search s) {
		return false;
	}

	/**
	 *
	 * @return A list of previously saved searches
	 */
	public List<Search> getSavedSearches() {
		return null;
	}

	/**
	 *
	 * @param s The string to search against
	 * @return A list of UPIs which have the parameter as a substring
	 */
	public List<UPI> getMatchingUPI(String s) {
		List<UPI> UPIs = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM data WHERE "
					+"taxonomy = LIKE ?");
			ps.setString(1, "%"+s+"%");
			ps.execute();

			ResultSet rs = ps.getResultSet();
			if (rs.first()) { // are there any matching UPIs?
				do {
					try {
						UPIs.add(new UPI(rs.getString(1)));
					} catch (InvalidTaxonomyException ex) {
						System.err.println("ITE: "+ex);
					} catch (EmptyTaxonomyException ex) {
						System.err.println("ETE: "+ex);
					} // we want to fail silently except for logging as this is best graceful degradation
				} while (rs.next());
			}

			return UPIs;
		} catch (SQLException ex) {
			System.err.println("Failed to fetch UPIs");
			return UPIs;
		}
	}

}
