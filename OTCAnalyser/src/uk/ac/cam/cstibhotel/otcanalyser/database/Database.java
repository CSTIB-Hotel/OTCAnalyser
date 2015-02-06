package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Wai-Wai Ng
 */
public class Database {

	protected Connection connection;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Database d = new Database();
		d.addTrade(new Trade());
	}

	public Database() throws SQLException, ClassNotFoundException  {
		Class.forName("org.hsqldb.jdbcDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:file:"
				+"/Users/waiwaing/Library/OTCAnalyser/database.db"); // TODO: fix directory
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

		} finally {
			statement.close();
		}

	}

	/**
	 * Adds a trade to the database
	 *
	 * @param trade a trade to be added to the database
	 */
	void addTrade(Trade trade) throws SQLException {
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
	public static java.util.Date getLastUpdateTime() {
		return null;
	}

	/**
	 * Adds a CSV file listing trades to the database
	 *
	 * @param csv the relevant CSV file
	 */
	void addTrades(CSVTradeFile csv) {

	}

	/**
	 *
	 * @param s the search parameters
	 * @return all data matching the search
	 */
	SearchResult search(Search s) {
		return null;
	}

	/**
	 *
	 * @param s the search to save
	 */
	void saveSearch(Search s) {
	}

	/**
	 *
	 * @return A list of previously saved searches
	 */
	Search[] getSavedSearches() {
		return null;
	}

	/**
	 *
	 * @param s The string to search against
	 * @return A list of UPIs which have the parameter as a substring
	 */
	UPI[] getMatchingUPI(String s) {
		return null;
	}

}
