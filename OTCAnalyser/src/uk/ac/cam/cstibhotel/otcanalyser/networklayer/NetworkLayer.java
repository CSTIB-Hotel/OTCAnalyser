package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.util.Date;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;

public class NetworkLayer {
    static Date lastUpdateDate = Database.getLastUpdateTime();
    static int lastSlice = 0; //the ID of the last received slice today, counting from 1
    static Date targetUpdateDate;
    
    static final String repo = "https://kgc0418-tdw-data-0.s3.amazonaws.com";
    static final String splitter = "\",\"";
    
    public static void initialUpdate() { //called at startup and every day afterwards
    	
    	Thread updater = new InitialUpdateWorker();

    	updater.start();
    }
    
    public static void poll() { //called internally by a timer
    	
    }
}
