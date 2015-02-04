package uk.ac.cam.cstibhotel.otcanalyser.database;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import java.io.File;

abstract class CSVTradeFile extends File{
    String[] columnHeaders;

    public CSVTradeFile(File f, String path){
	super(path);
	throw new UnsupportedOperationException("This function has not yet been implemented.");
	// you can't have abstract constructors
    }
    
    /**
     * 
     * @param i the ith row to fetch
     * @return the ith row as a Trade object
     */
    abstract Trade getTrade(int i);
}
