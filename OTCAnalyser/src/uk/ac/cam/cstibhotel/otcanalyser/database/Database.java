package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cam.cstibhotel.otcanalyser.trade.EmptyTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.InvalidTaxonomyException;

/**
 *
 * @author Wai-Wai Ng
 */
public class Database {

	protected static Database db;
	protected static Connection connection;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Database d = getDB();
		addTrade(new Trade());
	}
	
	public static Database getDB() throws SQLException, ClassNotFoundException{
		if(db == null){
			db = new Database("/Users/waiwaing/Library/OTCAnalyser/database.db");
		}
		return db;
	}

	private Database(String s) throws SQLException, ClassNotFoundException  {
		Class.forName("org.hsqldb.jdbcDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:file:" + s);
		connection.setAutoCommit(false);

		Statement statement = connection.createStatement(); // TODO: probably want to convert to prepared statements 
		try {
			statement.execute("SET WRITE_DELAY FALSE"); //Always update data on disk

			StringBuilder dataTableCreator = new StringBuilder("CREATE TABLE data (");
			HashMap<String, SQLField> DBNameDBType = TradeFieldMapping.getMapping(new Trade());
			Iterator<Entry<String, SQLField>> i = DBNameDBType.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, SQLField> mapEntry = i.next();
				dataTableCreator.append(mapEntry.getKey()).append(" ").append(mapEntry.getValue().getType()).append(", ");
			}
			dataTableCreator.setLength(dataTableCreator.length()-2);
			dataTableCreator.append(");");
			statement.execute(dataTableCreator.toString());
			
			String infoTableCreator = "CREATE TABLE info ("
					+ "key VARCHAR(255), value VARCHAR(255)";
			statement.execute(infoTableCreator);
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO info (key, value) VALUES (?, ?)");
			ps.setString(1, "last_update");
			ps.setString(2, "0");
			ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			statement.close();
		}

	}

	/**
	 * Adds a trade to the database
	 *
	 * @param trade a trade to be added to the database
	 */
	public static void addTrade(Trade trade) throws SQLException {
		StringBuilder a = new StringBuilder("INSERT INTO data (");
		StringBuilder b = new StringBuilder(") VALUES (");

		HashMap<String, SQLField> DBNameValue = TradeFieldMapping.getMapping(trade);
		Iterator<Entry<String, SQLField>> iterator = DBNameValue.entrySet().iterator();
		int index = 1;
		while (iterator.hasNext()) {
			Entry<String, SQLField> mapEntry = iterator.next();
			a.append(mapEntry.getKey()).append(", ");
			b.append("?, ");
			mapEntry.getValue().index = index;
			index++;
		}
		a.setLength(a.length() - 2);
		b.setLength(b.length() - 2);
		a.append(b).append(")");
		
		PreparedStatement p = connection.prepareStatement(a.toString());
	
		iterator = DBNameValue.entrySet().iterator();
		while (iterator.hasNext()) {
			iterator.next().getValue().addToPreparedStatement(p);
		}
		
		p.execute();
	}

	/**
	 *
	 * @return The time the database was last updated
	 */
	public static java.util.Date getLastUpdateTime() throws SQLException {
		Statement s = connection.createStatement();
		s.execute("SELECT value FROM info WHERE key = last_update");
		String timeString = s.getResultSet().getString(1);
		return new java.util.Date(Long.getLong(timeString));
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
	public static SearchResult search(Search s) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM data WHERE "
				+ "tradeType = ? AND"
				+ "assetClass = ? AND"
				+ "taxonomy = ? AND"
				+ "optionStrikePrice >= ? AND"
				+ "optionStrikePrice <= ? AND"
				+ "currency = ? AND"
				+ "startTime >= ? AND"
				+ "endTime <= ? AND"
				+ "1 = 1");
		ps.setShort(1, s.getTradeType().getValue());
		ps.setShort(2, s.getAssetClass().getValue());
		ps.setString(3, s.getAsset());
		ps.setFloat(4, s.getMinPrice());
		ps.setFloat(5, s.getMaxPrice());
		ps.setString(6, s.getCurrency().getCurrencyCode());
		ps.setTimestamp(7, new Timestamp(s.getStartTime().getTime()));
		ps.setTimestamp(8, new Timestamp(s.getEndTime().getTime()));
		ps.execute();		
		
 		return null;
	}

	/**
	 *
	 * @param s the search to save
	 */
	public static void saveSearch(Search s) {
	}

	/**
	 *
	 * @return A list of previously saved searches
	 */
	public static List<Search> getSavedSearches() {
		return null;
	}

	/**
	 *
	 * @param s The string to search against
	 * @return A list of UPIs which have the parameter as a substring
	 */
	public static List<UPI> getMatchingUPI(String s) throws SQLException {
		List<UPI> UPIs = new ArrayList<>();
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM data WHERE "
				+ "taxonomy = LIKE ?");
		ps.setString(1, "%" + s + "%");
		ps.execute();
		
		ResultSet rs = ps.getResultSet();
		if(rs.first()){ // are there any matching UPIs?
			do{
				try {
					UPIs.add(new UPI(rs.getString(1)));
				} catch (InvalidTaxonomyException ex) {
					System.err.println("ITE: " + ex);
				} catch (EmptyTaxonomyException ex) {
					System.err.println("ETE: " + ex);
				} // we want to fail silently except for logging as this is best graceful degradation
			} while (rs.next());
		}
		
		return UPIs;
	}

}
