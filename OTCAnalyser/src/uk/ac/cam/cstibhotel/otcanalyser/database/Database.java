package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;
import java.util.Date;
import java.sql.*;

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
	    statement.executeQuery("CREATE TABLE data "
		    + "(id INTEGER, "
		    + "origId INTEGER, "
		    + "action SMALLINT, "
		    + "cleared BOOLEAN, "
		    + "collat SMALLINT, "
		    + "endUserException BOOLEAN, "
		    + "bespoke BOOLEAN, "
		    + "executionVenue BOOLEAN, "
		    + "blockTrades BOOLEAN, "
		    + "effectiveDate DATE, "
		    + "endDate DATE, "
		    + "dayCountConvention VARCHAR(255), "
		    + "settlementCurrency VARCHAR(3), "
		    + "tradeType SMALLINT, "
		    + "assetClass SMALLINT, "
		    + "subAssetClass VARCHAR(255), "
		    + "taxonomy VARCHAR(255), "
		    + "priceFormingContinuationData SMALLINT, "
		    + "underlyingAsset1 VARCHAR(255), "
		    + "underlyingAsset2 VARCHAR(255), "
		    + "priceNotationType VARCHAR(255), "
		    + "priceNotation FLOAT, "
		    + "additionalPriceNotationType VARCHAR(255), "
		    + "additionalPriceNotationType FLOAT, "
		    + "notionalCurrency1 VARCHAR(3), "
		    + "notionalCurrency2 VARCHAR(3), "
		    + "roundedNotionalAmount1 VARCHAR(255), "
		    + "roundedNotionalAmount2 VARCHAR(255), "
		    + "paymentFrequency1 VARCHAR(255), "
		    + "paymentFrequency2 VARCHAR(255), "
		    + "resetFrequency1 VARCHAR(255), "
		    + "resetFrequency2 VARCHAR(255), "
		    + "embeddedOption VARCHAR(255), "
		    + "optionStrikePrice FLOAT, "
		    + "optionType VARCHAR(255), "
		    + "optionFamily VARCHAR(255), "
		    + "optionCurrency VARCHAR(3), "
		    + "optionPremium FLOAT, "
		    + "optionLockPeriod DATE, "
		    + "optionExpirationDate DATE, "
		    + "priceNotation2Type VARCHAR(255), "
		    + "priceNotation2 FLOAT, "
		    + "priceNotation3Type VARCHAR(255), "
		    + "priceNotation3 FLOAT); ");
	   // statement.execute("SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_TABLES");
	 //   ResultSet r = statement.getResultSet();
	 //   Object o = r.getString(0);
	 //   System.out.println(o);
	//    ResultSet result = statement.executeQuery("SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_TABLES"); // COUNT(1) ");
	//	+ "FROM INFORMATION_SCHEMA.SYSTEM_TABLES ");
	//	+ "WHERE TABLE_NAME = 'data'");
	//    int i = result.getInt(0);
	//    System.out.println(i);
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
