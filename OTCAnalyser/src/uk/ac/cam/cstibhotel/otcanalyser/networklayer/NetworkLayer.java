package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.util.Calendar;
import java.util.Date;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;

class NetworkLayer {
    static Date lastUpdateDate = Database.getLastUpdateTime();
    static int lastSlice = 0; //the ID of the last received slice today, counting from 1
    static Date targetUpdateDate;
    
    void initialUpdate() { //called at startup and every day afterwards
    	
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
    
    void poll() { //called internally by a timer
    	
    }
}
