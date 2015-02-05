package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;
import java.util.Date;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Wai-Wai Ng
 */
class Database {

    protected Connection connection;
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
	Database d = new Database();
    }

    public Database() throws SQLException, ClassNotFoundException {
	Class.forName("org.hsqldb.jdbcDriver");
	connection = DriverManager.getConnection("jdbc:hsqldb:file:"
		+"/Users/waiwaing/Library/OTCAnalyser/database.db"); // TODO: fix directory
	connection.setAutoCommit(false);

	Statement statement = connection.createStatement(); // TODO: probably want to convert to prepared statements 
	try {
	    statement.execute("SET WRITE_DELAY FALSE"); //Always update data on disk
	    
	    StringBuilder dataTableCreator = new StringBuilder("CREATE TABLE data (");
	    HashMap<String, String> DBNameDBType = TradeFieldMapping.getMapping();
	    Iterator i = DBNameDBType.entrySet().iterator();
	    while(i.hasNext()){
		@SuppressWarnings("unchecked")
		Entry<String, String> mapEntry = (Entry<String, String>) i.next();
		dataTableCreator.append(mapEntry.getKey()).append(" ").append(mapEntry.getValue()).append(", ");
	    }
	    dataTableCreator.setLength(dataTableCreator.length() - 2);
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
    void addTrade(Trade trade){	
    }

    /**
     *
     * @return The time the database was last updated
     */
    Date getLastUpdateTime(){
	return null;
    }

    /**
     * Adds a CSV file listing trades to the database
     *
     * @param csv the relevant CSV file
     */
    void addTrades(CSVTradeFile csv){
	
    }

    /**
     *
     * @param s the search parameters
     * @return all data matching the search
     */
    SearchResult search(Search s){
	return null;
    }

    /**
     *
     * @param s the search to save
     */
    void saveSearch(Search s){
    }

    /**
     *
     * @return A list of previously saved searches
     */
    Search[] getSavedSearches(){
	return null;
    }

    /**
     *
     * @param s The string to search against
     * @return A list of UPIs which have the parameter as a substring
     */
    UPI[] getMatchingUPI(String s){
	return null;
    }

}
