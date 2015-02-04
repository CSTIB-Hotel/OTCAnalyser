package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;
import java.util.Date;

/**
 * 
 * @author Wai-Wai Ng
 */
abstract class Database{
    
    /**
     * Adds a trade to the database
     * @param trade a trade to be added to the database
     */
    abstract void addTrade(Trade trade);
    
     /**
     * 
     * @return The time the database was last updated
     */
    abstract Date getLastUpdateTime();
       
    /**
     * Adds a CSV file listing trades to the database
     * @param csv the relevant CSV file
     */
    abstract void addTrades(CSVTradeFile csv);
    
    /**
     * 
     * @param s the search parameters
     * @return all data matching the search 
     */
    abstract SearchResult search(Search s);
    

    /**
     * 
     * @param s the search to save
     */
    abstract void saveSearch(Search s);
    
    
    /**
     * 
     * @return A list of previously saved searches
     */
    abstract Search[] getSavedSearches();
    
    /**
     * 
     * @param s The string to search against
     * @return A list of UPIs which have the parameter as a substring
     */
    abstract UPI[] getMatchingUPI(String s);
}
