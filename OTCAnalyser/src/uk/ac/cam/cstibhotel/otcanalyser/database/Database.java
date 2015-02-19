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
import uk.ac.cam.cstibhotel.otcanalyser.trade.EmptyTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.InvalidTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
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
		connection = DriverManager.getConnection("jdbc:hsqldb:file:"+getDatabasePath());
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
	private boolean commit() {
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

			if (trade.getAction().equals(Action.CANCEL)) {
				deleteTrade(trade.getDisseminationID());
				break; // TODO
			} else if (trade.getAction().equals(Action.CORRECT)) {
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
	 * @return An SQL update string
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
		return true; // commit();
	}

	/**
	 *
	 * @param trade the trade to use to calculate the last update time
	 */
	private boolean updateLastUpdateTime(Trade trade) {
		java.util.Date thisUpdateTime = trade.getExecutionTimestamp(); // is this the right date?
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
			String query = "SELECT * FROM data WHERE "
					+"tradeType = ? AND "
					+" assetClass = ? AND ";
			if (s.getAsset().equals("")||s.getAsset()==null) {
				query += " (underlyingAsset1 LIKE ? OR underlyingAsset2 LIKE ?) AND ";
			}
			query += " optionStrikePrice >= ? AND "
					+" optionStrikePrice <= ? AND ";
			if (s.getCurrency().equals("")||s.getCurrency()==null) {
				query += " (notionalCurrency1 LIKE ? OR notionalCurrency2 LIKE ? ) AND ";
			}
			query += " executionTime >= ? AND "
					+" executionTime <= ?";

			PreparedStatement ps = connection.prepareStatement(query);
				//	+"taxonomy LIKE ?");

			int i = 1;

			ps.setShort(i, s.getTradeType().getValue()); i++;
			ps.setShort(i, s.getAssetClass().getValue()); i++;

			if (s.getAsset().equals("") || s.getAsset()==null) {
				ps.setString(i, "%"+s.getAsset()+"%"); i++;
				ps.setString(i, "%"+s.getAsset()+"%"); i++;
			}

			ps.setFloat(i, s.getMinPrice()); i++;
			ps.setFloat(i, s.getMaxPrice()); i++;
			
			if (s.getCurrency().equals("") || s.getCurrency()==null) {
				ps.setString(i, "%"+s.getCurrency()+"%"); i++;
				ps.setString(i, "%"+s.getCurrency()+"%"); i++;
			}
			
			ps.setTimestamp(i, new Timestamp(s.getStartTime().getTime())); i++;
			ps.setTimestamp(i, new Timestamp(s.getEndTime().getTime())); i++;
			//	ps.setString(i, "%" + s.getUPI().toString() + "%"); i++; */
			ResultSet rs = ps.executeQuery();

			List<Trade> trades = new LinkedList<>();

			while (rs.next()) {
				trades.add(TradeFieldMapping.makeObjectFromRecord(rs));
			}

			// TODO no idea what time is for
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
	public Connection getConnection() {
		return connection;
	}

	public static void main(String[] args) throws SQLException {
		Connection c = Database.getDB().getConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT settlementCurrency FROM data GROUP BY settlementCurrency");
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

}
