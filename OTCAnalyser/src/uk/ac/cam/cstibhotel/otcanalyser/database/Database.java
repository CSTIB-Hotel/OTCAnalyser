package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;

/**
 * A database class for storing OTC trades
 * 
 * @author Wai-Wai Ng
 */
public class Database {

	private static Database db;
	private static Connection connection;
	private static final int TableAlreadyExistsError = -21; // as defined by HSQLDB Driver
	private static final int ObjectNameAlreadyExists = -5504; // as thrown by trying to create table twice
	private static final int RowWithUniqueFieldAlreadyExistsError = -104;

	/**
	 * Returns a string representing the filepath to where the database should be stored
	 * @return the file path to the database
	 */
	private static String getDatabasePath() {
		// originally stored in the appropriate directory for OSes, but it
		// transpired that permissions issues were rather more likely than
		// expected, so this was replaced with a local filepath
		return "database.db";
	}

	/**
	 * Returns the singleton database object
	 * @return the database
	 */
	public static Database getDB() {
		if (db==null) {
			try {
				db = new Database();
			} catch (SQLException ex) {
				System.err.println("There was a fatal database error.");
				System.err.println(ex.getMessage());
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.err.println("Error: Could not locate database driver");
				System.exit(2);
			}
		}
		return db;
	}

	/**
	 * Creates the database
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	private Database() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:file:"+getDatabasePath());
		connection.setAutoCommit(false);

		connection.createStatement().execute("SET WRITE_DELAY FALSE"); // always update data on disk

		createDataTable();
		createInfoTable();
		createSavedSearchTable();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					connection.createStatement().execute("SHUTDOWN");
				} catch (SQLException ex) {
					System.err.println("Database shutdown failed");
				}
			}
		});
	}

	/**
	 * Creates the table for trade data
	 * @throws SQLException 
	 */
	private void createDataTable() throws SQLException {
		StringBuilder dataTableCreator = new StringBuilder("CREATE TABLE data (");

		HashMap<String, SQLField> DBNameDBType = TradeFieldMapping.getMapping(new Trade());
		Iterator<Entry<String, SQLField>> i = DBNameDBType.entrySet().iterator();

		while (i.hasNext()) {
			Entry<String, SQLField> mapEntry = i.next();
			if(mapEntry != null && mapEntry.getValue() != null){
				dataTableCreator.append(mapEntry.getKey()).append(" ").append(mapEntry.getValue().getType()).append(", ");
			} else {
				if(mapEntry == null){ // TODO
					System.out.println("a");
				} else {
					System.out.println("b");
				}
			//	dataTableCreator.app
			}
		}

 		dataTableCreator.append(" UNIQUE (id));");
		
		try {
			connection.createStatement().execute(dataTableCreator.toString());
			connection.createStatement().executeQuery("CREATE INDEX ua1 ON data (underlyingAsset1)");
			connection.createStatement().executeQuery("CREATE INDEX eTcurr ON data (executionTime, notionalCurrency1)");
			connection.createStatement().executeQuery("CREATE INDEX rna1 ON data (roundedNotionalAmount1)");
		} catch (SQLException e) {
			if(e.getErrorCode() != TableAlreadyExistsError && e.getErrorCode() != ObjectNameAlreadyExists){
				System.err.println(e.getErrorCode());
				throw e;
			}
		}
	}

	/**
	 * Creates the table that stores internal metadata
	 * @throws SQLException 
	 */
	private void createInfoTable() throws SQLException {
		String infoTableCreator = "CREATE TABLE info ( key VARCHAR(255), vvalue VARCHAR(255) )";
		try {
			connection.createStatement().execute(infoTableCreator);
			connection.createStatement().execute("INSERT INTO info (key, vvalue) VALUES ('last_update', '0')");
			commit();
		} catch (SQLException e) {
			if(e.getErrorCode() != TableAlreadyExistsError && e.getErrorCode() != ObjectNameAlreadyExists){
				throw e;
			}
		}
	}
	
	/**
	 * Creates the table that stores saved searches
	 * @throws SQLException 
	 */
	private void createSavedSearchTable() throws SQLException {
		String savedSearchTableCreator = "CREATE TABLE savedsearches ("
				+ " id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
				+ " searchName VARCHAR(255),"
				+ " tradeType SMALLINT, "
				+ " assetClass SMALLINT, "
				+ " asset VARCHAR(255), "
				+ " minPrice BIGINT, "
				+ " maxPrice BIGINT, "
				+ " currency VARCHAR(20), "
				+ " startTime TIMESTAMP, "
				+ " endTime TIMESTAMP, "
				+ " upi VARCHAR(255),"
				+ " UNIQUE (searchName))";
		try{
			connection.createStatement().execute(savedSearchTableCreator);
		} catch (SQLException e){
			if(e.getErrorCode() != TableAlreadyExistsError && e.getErrorCode() != ObjectNameAlreadyExists){
				throw e;
			}
		}
		
	}

	/**
	 * Attempts to commit the current transaction to the database
	 * @return true if the current transaction was successfully committed
	 */
	private boolean commit() {
		try {
			connection.createStatement().execute("COMMIT;");
			return true;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	/**
	 * Adds trades to the database
	 *
	 * @param trades a list of trades to be added to the database
	 * @return true if the database was successfully updated
	 */
	public boolean addTrade(List<Trade> trades) {
		boolean success = true;
		
		Collections.sort(trades);
		
		for (Trade trade : trades) {

			HashMap<String, SQLField> DBNameValue = TradeFieldMapping.getMapping(trade);
			Iterator<Entry<String, SQLField>> iterator = DBNameValue.entrySet().iterator();
			
			String executeString;

			if (trade.getAction().equals(Action.CANCEL)) {
				boolean v = deleteTrade(trade.getDisseminationID());
				success &= v; // do not combine this with the above as Java will short-circuit boolean evaluation
				break; 
			} else if (trade.getAction().equals(Action.CORRECT)) {
				executeString = buildUpdateString(iterator, trade.getDisseminationID());
			} else { // equals(Action.NEW)
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
				if(e.getErrorCode() == RowWithUniqueFieldAlreadyExistsError){
					// ignore, we've just tried to duplicate a row
				} else {
					System.err.println(e.getMessage());
					System.err.println("Failed to insert/update a row");
					success = false;
				}
			}

			updateLastUpdateTime(trade);
		}

		boolean v = commit();
		return success && v;
	}

	/**
	 *
	 * @param iterator An iterator over a DB mapping
	 * @return An SQL INSERT string
	 */
	private String buildInsertString(Iterator<Entry<String, SQLField>> iterator) {
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
	 * @return An SQL UPDATE string
	 */
	private String buildUpdateString(Iterator<Entry<String, SQLField>> iterator, long origId) {
		StringBuilder s = new StringBuilder("UPDATE data SET ");

		int index = 1;

		while (iterator.hasNext()) {
			Entry<String, SQLField> mapEntry = iterator.next();
			s.append(mapEntry.getKey()).append(" = ?, ");
			mapEntry.getValue().index = index;
			index++;
		}

		s.setLength(s.length()-2);
		s.append(" WHERE id = ").append(origId);

		return s.toString();
	}

	/**
	 *
	 * @param id The id of the trade to delete
	 * @return Whether the deletion was successful
	 */
	private boolean deleteTrade(long id) {
		String deletionString = "DELETE FROM data WHERE id = "+id;
		try {
			connection.createStatement().execute(deletionString);
		} catch (SQLException ex) {
			System.err.println("Error deleting a trade");
			return false;
		}
		return true; 
	}

	/**
	 * Updates the metadata field that stores the most recent date for which the database has data
	 * @param trade the trade to use to calculate the last update time
	 */
	private boolean updateLastUpdateTime(Trade trade) {
		java.util.Date thisUpdateTime = trade.getExecutionTimestamp(); 
		java.util.Date lastUpdateTime = getLastUpdateTime();

		if (thisUpdateTime==null) {
			return true;
		}

		if (thisUpdateTime.after(lastUpdateTime)) {
			try {
				PreparedStatement ps = connection.prepareCall("UPDATE info SET vvalue = ? WHERE key = 'last_update'");
				ps.setString(1, Long.toString(thisUpdateTime.getTime()));
				ps.execute();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				System.err.println("Failed to update last update time");
			}
		}

		return true;// commit();
	}

	/**
	 * Returns the most recent date for which the database has data
	 * @return The date of the last database update
	 */
	public java.util.Date getLastUpdateTime() {
		try {
			Statement s = connection.createStatement();
			s.execute("SELECT vvalue FROM info WHERE key = 'last_update'");

			String timeString;

			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				timeString = rs.getString(1);
				if (Long.parseLong(timeString)==0L) {
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
			System.err.println(ex.getMessage());
			System.err.println("Could not get the last update time");
			return new java.util.Date(0);
		}
	}

	/**
	 * Searches the database
	 * @param s the search parameters
	 * @return all data matching the search
	 */
	public SearchResult search(Search s) {
		try {
			String query = "SELECT * FROM data WHERE "
					+"tradeType = ? AND "
					+"assetClass = ? AND ";
			if (!(s.getAsset() == null||s.getAsset().equals(""))) {
				query += " (underlyingAsset1 LIKE ? OR underlyingAsset2 LIKE ?) AND ";
			}
			if (!(s.getMinPrice() == s.getMaxPrice())){
				query += " roundedNotionalAmount1 >= ? AND "
						+" roundedNotionalAmount1 <= ? AND ";
			}
			if (!(s.getCurrency() == null || s.getCurrency().equals(""))) {
				query += " (notionalCurrency1 LIKE ? OR notionalCurrency2 LIKE ? ) AND ";
			}
			if (!(s.getUPI() == null || s.getUPI().equals(""))){
				query += " taxonomy LIKE ? AND ";
			}
			query += " executionTime >= ? AND "
					+" executionTime <= ?";
			
			PreparedStatement ps = connection.prepareStatement(query);

			int i = 1;

			ps.setShort(i, s.getTradeType().getValue()); i++;
			ps.setShort(i, s.getAssetClass().getValue()); i++;

			if (!(s.getAsset() == null||s.getAsset().equals(""))) {
				ps.setString(i, "%"+s.getAsset()+"%"); i++;
				ps.setString(i, "%"+s.getAsset()+"%"); i++;
			}

			if(!(s.getMinPrice() == s.getMaxPrice())){
				ps.setFloat(i, s.getMinPrice()); i++;
				ps.setFloat(i, s.getMaxPrice()); i++;
			}
			
			if (!(s.getCurrency() == null || s.getCurrency().equals(""))) {
				ps.setString(i, "%"+s.getCurrency()+"%"); i++;
				ps.setString(i, "%"+s.getCurrency()+"%"); i++;
			}
			
			if (!(s.getUPI() == null || s.getUPI().equals(""))){		
				ps.setString(i, "%" + s.getUPI() + "%"); i++; 
			}

			ps.setTimestamp(i, new Timestamp(s.getStartTime().getTime())); i++;
			ps.setTimestamp(i, new Timestamp(s.getEndTime().getTime())); i++;
			ResultSet rs = ps.executeQuery();

			List<Trade> trades = new LinkedList<>();

			while (rs.next()) {
				trades.add(TradeFieldMapping.makeObjectFromRecord(rs));
			}

			System.out.println(trades.size());

			return new SearchResult(trades, 0);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			System.err.println("Search failed");
			return new SearchResult(new LinkedList<Trade>(), 0.0);
		}
	}

	/**
	 * Saves a search
	 * @param s the search to save
	 * @param searchName the name of this search
	 * @return true if the search was successfully saved; false otherwise
	 */
	public boolean saveSearch(Search s, String searchName) {
		try{
			String searchAlreadyExistsQuery = "SELECT COUNT(1) FROM savedSearches WHERE searchName = ?";
			
			PreparedStatement sae = connection.prepareStatement(searchAlreadyExistsQuery);
			sae.setString(1, searchName);
			
			ResultSet saer = sae.executeQuery();
			
			String saveQuery;
			
			if(saer.next()){
				if(saer.getInt(1) == 0){
					saveQuery = "INSERT INTO savedSearches (searchName, tradeType, assetClass, "
							+ "asset, minPrice, maxPrice, currency, startTime, endTime, upi) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				} else {
					saveQuery = "UPDATE savedSearches SET searchName = ?, tradeType = ?, "
							+ "assetClass = ?, asset = ?, minPrice = ?, maxPrice = ?, "
							+ "currency = ?, startTime = ?, endTime = ?, upi = ? "
							+ "WHERE searchName = ?";
				}
			} else {
				return false;
			}
			
		/* String saveQuery = "MERGE INTO savedSearches USING (VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) "
				+ "AS I(a, b, c, d, e, f, g, h, i, j) ON savedSearches.searchName = I.a "
				
				+ "WHEN MATCHED THEN UPDATE SET savedSearches.tradeType = I.b, "
				+ "savedSearches.assetClass = I.c, savedSearches.asset = I.d, "
				+ "savedSearches.minPrice = I.e, savedSearches.maxPrice = I.f, "
				+ "savedSearches.currency = I.g, savedSearches.startTime = I.h, "
				+ "savedSearches.endTime = I.i, savedSearches.upi = I.j "
				 
				+ "WHEN NOT MATCHED THEN INSERT (searchName, tradeType, assetClass, "
				+ "asset, minPrice, maxPrice, currency, startTime, endTime, upi) "
				+ "VALUES (I.a, I.b, I.c, I.d, I.e, I.f, I.g, I.h, I.i, I.j)";
		
			// So it turns out HSQLDB doesn't support parameterized  MERGE statements 
		*/
		
		
			PreparedStatement ps = connection.prepareStatement(saveQuery);			
			ps.setString(1, searchName);
			ps.setShort(2, s.getTradeType().getValue());
			ps.setShort(3, s.getAssetClass().getValue());
			ps.setString(4, s.getAsset());
			ps.setLong(5, s.getMinPrice());
			ps.setLong(6, s.getMaxPrice());
			ps.setString(7, s.getCurrency());
			ps.setTimestamp(8, new Timestamp(s.getStartTime().getTime()));
			ps.setTimestamp(9, new Timestamp(s.getEndTime().getTime()));
			ps.setString(10, s.getUPI());
			
			if(saer.getInt(1) == 1){ // yes this is silly
				ps.setString(11, searchName);
			}
			
			ps.execute();
			 
			return true;
			
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Returns a saved search by string for the search name
	 * @param name the name of the search to lookup;
	 * @return the Search that matches the requested string, or null if no search exists/a db failure occurred
	 */
	public static Search getSavedSearch(String name) {
		String query = "SELECT * FROM savedSearches WHERE searchName = ?";
		try{
			if(connection == null){
				getDB();
			}
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Search s = new Search();
				s.setTradeType(TradeType.lookup(rs.getShort("tradeType")));
				s.setAssetClass(AssetClass.lookup(rs.getShort("assetClass")));
				s.setAsset(rs.getString("asset"));
				s.setMinPrice(rs.getInt("minPrice"));
				s.setMaxPrice(rs.getInt("maxPrice"));
				s.setCurrency(rs.getString("currency"));
				s.setStartTime(new Timestamp(rs.getTimestamp("startTime").getTime()));
				s.setEndTime(new Timestamp(rs.getTimestamp("endTime").getTime()));
				s.setUPI(rs.getString("upi"));
				return s;
			}
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return null;
		}
		
		return null;
	}

	/**
	 * Returns the five searches most recently saved
	 * @return The 5 most recently saved previously saved searches
	 */
	public static Map<String, Search> getSavedSearches() {
		Map<String, Search> savedSearches = new LinkedHashMap<>();
		
		if(connection == null){
				getDB();
		}
		String query = "SELECT * FROM savedSearches ORDER BY id DESC LIMIT 5";
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while(rs.next()){
				Search s = new Search();
				s.setTradeType(TradeType.lookup(rs.getShort("tradeType")));
				s.setAssetClass(AssetClass.lookup(rs.getShort("assetClass")));
				s.setAsset(rs.getString("asset"));
				s.setMinPrice(rs.getInt("minPrice"));
				s.setMaxPrice(rs.getInt("maxPrice"));
				s.setCurrency(rs.getString("currency"));
				s.setStartTime(new Timestamp(rs.getTimestamp("startTime").getTime()));
				s.setEndTime(new Timestamp(rs.getTimestamp("endTime").getTime()));
				s.setUPI(rs.getString("upi"));
				
				savedSearches.put(rs.getString("searchName"), s);
			}	
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return savedSearches;
	}

	/**
	 * Provides a DB Connection to allow raw SQL where needed
	 * 
	 * This method is provided for cases where direct exposure to the DB API is
	 * necessary. Its use breaks the information hiding principle and its use is
	 * therefore discouraged.
	 * 
	 * @return The database Connection
	 */
	public Connection getConnection() {
		return connection;
	}

}
