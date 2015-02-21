package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.util.Date;

public class NetworkLayer {
    //static Date lastUpdateDate = Database.getDB().getLastUpdateTime();
    static int lastSlice = 0; //the ID of the last received slice today, counting from 1
    static Date targetUpdateDate = new Date();
    
    static final String repo = "https://kgc0418-tdw-data-0.s3.amazonaws.com";
    static final String splitter = "\",\"";
    static final String secondarySplitter = ",";
    
    public static void initialUpdate() { //called at startup and every day afterwards
    	
    	Thread updater = new InitialUpdateWorker();

    	updater.start();
    }
}
