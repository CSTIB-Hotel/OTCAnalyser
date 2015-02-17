package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Collateralization;
import uk.ac.cam.cstibhotel.otcanalyser.trade.EmptyTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.InvalidTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.PriceFormingContinuationData;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;

/**
 *
 * @author Wai-Wai Ng
 */
public class Database {

	private static Database db;
	private static Connection connection;
	
	private static String getDatabasePath() {
		// Local file path regardless of OS
		return "database.db";
	}

	public static Database getDB() {
		if (db==null) {
			try {
				db = new Database();
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.err.println("There was a fatal database error, class 1");
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.err.println("Error: Could not locate database driver");
				System.exit(2);
			}
		}
		return db;
	}

	private Database() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:file:" + getDatabasePath());
		//for memory mapped would have "jdbc:hsqldb:mem:."
		connection.setAutoCommit(false);

		connection.createStatement().execute("SET WRITE_DELAY FALSE"); // always update data on disk

		createDataTable();
		createInfoTable();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					connection.createStatement().execute("SHUTDOWN");
				} catch (SQLException ex) {
					System.err.println("Database shutdown failed");
				}
			}
		});
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
		String infoTableCreator = "CREATE TABLE info ( key VARCHAR(255), vvalue VARCHAR(255) )";
		try {
			connection.createStatement().execute(infoTableCreator);			
			connection.createStatement().execute("INSERT INTO info (key, vvalue) VALUES ('last_update', '0')");
			commit();
		} catch (SQLException e) {
			// do nothing as this just means the info table already exists
			// todo check error message to make sure that we throw any other errors
		}
	}
	
	/**
	 * 
	 * @return true if the current transaction was successfully committed
	 */
	private boolean commit(){
		try {
			connection.createStatement().execute("COMMIT;");
			return true;
		} catch (SQLException ex) {
			return false;
		}
	}

	/**
	 * Adds a trade to the database
	 *
	 * @param trade a trade to be added to the database
	 * @return true if the database was successfully updated
	 */
	public boolean addTrade(List<Trade> trades) {
		
		
		for (Trade trade : trades) {
			
			HashMap<String, SQLField> DBNameValue = TradeFieldMapping.getMapping(trade);
			Iterator<Entry<String, SQLField>> iterator = DBNameValue.entrySet().iterator();
			
			String executeString;
			
			if(trade.getAction().equals(Action.CANCEL)) {
				deleteTrade(trade.getDisseminationID());
				break; // TODO
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
		}

		return commit();
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
		return true; // commit();
	}
	
	/**
	 * 
	 * @param trade the trade to use to calculate the last update time
	 */
	private boolean updateLastUpdateTime(Trade trade){
		java.util.Date thisUpdateTime = trade.getExecutionTimestamp(); // is this the right date?
		java.util.Date lastUpdateTime = getLastUpdateTime();
		
		if (thisUpdateTime == null)
			return true;
		
		if (thisUpdateTime.after(lastUpdateTime)) {
			try {
				PreparedStatement ps = connection.prepareCall("UPDATE info SET vvalue = ? WHERE key = 'last_update'");
				ps.setString(1, Long.toString(thisUpdateTime.getTime()));
				ps.execute();
			} catch (SQLException e) {
				System.err.println("Failed to update last update time");
			}
		}
		
		return true;// commit();
	}
	
	/**
	 *
	 * @return The time the database was last updated
	 */
	public java.util.Date getLastUpdateTime() {
		try {
			Statement s = connection.createStatement();
			s.execute("SELECT vvalue FROM info WHERE key = 'last_update'");
		
			String timeString;
			
			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				timeString = rs.getString(1);
				if (Long.parseLong(timeString) == 0L) {
					Calendar c = Calendar.getInstance();
					// First available data is from 28th February 2013
					c.set(2013, 1, 27);
					return c.getTime();
				}
				return new Date(Long.parseLong(timeString));
			} else {
				throw new RuntimeException("no data");
			}
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
	public SearchResult search(Search s) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM data WHERE "
					+"tradeType = ? AND "
					+"assetClass = ? AND "
					+"underlyingAsset1 LIKE ? AND "
					+"optionStrikePrice >= ? AND "
					+"optionStrikePrice <= ? AND "
					+"settlementCurrency LIKE ? AND "
					+"executionTime >= ? AND "
					+"executionTime <= ?");
				//	+"taxonomy LIKE ?");
			ps.setShort(1, s.getTradeType().getValue());
			ps.setShort(2, s.getAssetClass().getValue());
			ps.setString(3, "%" + s.getAsset() + "%");
			ps.setFloat(4, s.getMinPrice());
			ps.setFloat(5, s.getMaxPrice());
			ps.setString(6, "%" + s.getCurrency() + "%");
			ps.setTimestamp(7, new Timestamp(s.getStartTime().getTime()));
			ps.setTimestamp(8, new Timestamp(s.getEndTime().getTime()));
		//	ps.setString(9, "%" + s.getUPI().toString() + "%");*/
	//		PreparedStatement ps = connection.prepareStatement("SELECT * FROM data");
			ResultSet rs = ps.executeQuery();
			
			// TODO no idea what time is for
			List<Trade> trades = new LinkedList<>();
			
			while(rs.next()){
				Trade t = new Trade();
				
				t.setDisseminationID(rs.getLong("id"));
				t.setOriginalDisseminationID(rs.getLong("origId"));
				t.setAction(Action.lookup(rs.getShort("action")));
				t.setExecutionTimestamp(rs.getTimestamp("executionTime"));
				t.setCleared(rs.getBoolean("cleared"));
				t.setCollateralization(Collateralization.lookup(rs.getShort("collat")));
				t.setEndUserException(rs.getBoolean("endUserException"));
				t.setBespoke(rs.getBoolean("bespoke"));
				t.setExecutionVenue(rs.getBoolean("executionVenue"));
				t.setBlockTrades(rs.getBoolean("blockTrades"));
				t.setEffectiveDate(rs.getDate("effectiveDate"));
				t.setEndDate(rs.getDate("endDate"));
				t.setDayCountConvention(rs.getString("dayCountConvention"));
				t.setSettlementCurrency(rs.getString("settlementCurrency"));
				t.setTradeType(TradeType.lookup(rs.getShort("tradeType")));
				t.setAssetClass(AssetClass.lookup(rs.getShort("assetClass")));
				// t.setSubAssetClass(); // todo
				// t.setTaxonomy(); // todo
				t.setPriceFormingContinuationData(PriceFormingContinuationData.lookup(rs.getShort("priceFormingContinuationData")));
				t.setUnderlyingAsset1(rs.getString("underlyingAsset1"));
				t.setUnderlyingAsset2(rs.getString("underlyingAsset2"));
				t.setPriceNotationType(rs.getString("priceNotationType"));
				t.setPriceNotation(rs.getDouble("priceNotation"));
				t.setAdditionalPriceNotationType(rs.getString("additionalPriceNotationType"));
				t.setAdditionalPriceNotation(rs.getDouble("additionalPriceNotation"));
				t.setNotionalCurrency1(rs.getString("notionalCurrency1"));
				t.setNotionalCurrency2(rs.getString("notionalCurrency2"));
				t.setRoundedNotionalAmount1(rs.getString("roundedNotionalAmount1"));
				t.setRoundedNotionalAmount2(rs.getString("roundedNotionalAmount2"));
				t.setPaymentFrequency1(rs.getString("paymentFrequency1"));
				t.setPaymentFrequency2(rs.getString("paymentFrequency2"));
				t.setResetFrequency1(rs.getString("resetFrequency1"));
				t.setResetFrequency2(rs.getString("resetFrequency2"));
				t.setEmbeddedOption(rs.getString("embeddedOption"));
				t.setOptionStrikePrice(rs.getDouble("optionStrikePrice"));
				t.setOptionType(rs.getString("optionType"));
				t.setOptionFamily(rs.getString("optionFamily"));
				t.setOptionCurrency(rs.getString("optionCurrency"));
				t.setOptionPremium(rs.getDouble("optionPremium"));
				t.setOptionLockPeriod(rs.getDate("optionLockPeriod"));
				t.setOptionExpirationDate(rs.getDate("optionExpirationDate"));
				t.setPriceNotation2Type(rs.getString("priceNotation2Type"));
				t.setPriceNotation2(rs.getDouble("priceNotation2"));
				t.setPriceNotation3Type(rs.getString("priceNotation3Type"));
				t.setPriceNotation3(rs.getDouble("priceNotation3"));
				
				
				trades.add(t);
			}
			
			System.out.println(trades.size());

			return new SearchResult(trades, 0);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
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
	
	// this method exists only for testing to allow arbitrary SQL queries to be run
	public Connection getConnection(){
		return connection;
	}

}
