package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

public class NetworkLayer {
    static Date lastUpdateDate = Database.getLastUpdateTime();
    static int lastSlice = 0; //the ID of the last received slice today, counting from 1
    static Date targetUpdateDate;
    
    static final String repo = "https://kgc0418-tdw-data-0.s3.amazonaws.com";
    static final String splitter = ",";
    
    public static void initialUpdate() { //called at startup and every day afterwards
    	
    	Thread update = new Thread() {
    	    public void run() {
    	    	
    	        synchronized(lastUpdateDate) {
    	        	Date now = new Date();
    	        	Calendar target = Calendar.getInstance();
    	        	target.setTime(now);
    	        	target.add(Calendar.DATE, -1);
    	        	targetUpdateDate = target.getTime();
    	        	
    	        	Calendar lastUpdate = Calendar.getInstance();
    	        	lastUpdate.setTime(lastUpdateDate);
    	        	
    	        	while (target.get(Calendar.YEAR) != lastUpdate.get(Calendar.YEAR) ||
    	        			target.get(Calendar.MONTH) != lastUpdate.get(Calendar.MONTH) ||
    	        			target.get(Calendar.DAY_OF_MONTH) != lastUpdate.get(Calendar.DAY_OF_MONTH)) {
    	        		
    	        		lastUpdate.add(Calendar.DATE, 1);
    	        		
	        			String formatDate = lastUpdate.get(Calendar.YEAR) + "_" + 
	        					(lastUpdate.get(Calendar.MONTH) < 10 ? "0" : "") +
	        					lastUpdate.get(Calendar.MONTH) + "_" + 
	        					(lastUpdate.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
	        					lastUpdate.get(Calendar.DAY_OF_MONTH);
	        			List<String> zipURLString = new LinkedList<String>();
	        			zipURLString.add(repo + "/slices/CUMULATIVE_COMMODITIES_" + formatDate + ".zip");
	        			zipURLString.add(repo + "/slices/CUMULATIVE_CREDITS_" + formatDate + ".zip");
	        			zipURLString.add(repo + "/slices/CUMULATIVE_EQUITIES_" + formatDate + ".zip");
	        			zipURLString.add(repo + "/slices/CUMULATIVE_FOREX_" + formatDate + ".zip");
	        			zipURLString.add(repo + "/slices/CUMULATIVE_RATES_" + formatDate + ".zip");
	        			
	        			for (String s : zipURLString) {
	        				try {
								List<Trade> newTrades = ParseZIP.downloadData(s, splitter);
								for (Trade t : newTrades) {
									Database.addTrade(t);
								}
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								
							}
	        				
	        				
	        			}
    	        		/*}
    	        		catch () {
    	        			
    	        		}*/
    	        		//todo: do the download and update
    	        		//todo: figure out a way to get url
    	        		//todo: implement fail/retry policy
    	        		//todo: implement exception throwing policy
    	        		
    	        		//todo: only if successful
    	        		lastUpdateDate = lastUpdate.getTime();
    	        	}
    	        			
    	        }
    	    }  
    	};

    	update.start();
    }
    
    public static void poll() { //called internally by a timer
    	
    }
}
