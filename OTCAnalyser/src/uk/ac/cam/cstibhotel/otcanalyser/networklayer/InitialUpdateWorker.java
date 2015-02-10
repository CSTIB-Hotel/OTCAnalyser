package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

public class InitialUpdateWorker extends Thread {
	@Override
	public void run() {
    	System.out.println("NetworkLayer: initial update requested");
        synchronized(NetworkLayer.lastUpdateDate) {
        	System.out.println("NetworkLayer: initial update started");
        	
        	Date now = new Date();
        	Calendar target = Calendar.getInstance();
        	target.setTime(now);
        	target.add(Calendar.DATE, -1);
        	NetworkLayer.targetUpdateDate = target.getTime();
        	
        	Calendar lastUpdate = Calendar.getInstance();
        	lastUpdate.setTime(NetworkLayer.lastUpdateDate);
        	
        	while (target.get(Calendar.YEAR) != lastUpdate.get(Calendar.YEAR) ||
        			target.get(Calendar.MONTH) + 1 != lastUpdate.get(Calendar.MONTH) + 1 ||
        			target.get(Calendar.DAY_OF_MONTH) != lastUpdate.get(Calendar.DAY_OF_MONTH)) {
        		
        		lastUpdate.add(Calendar.DATE, 1);
        		
    			String formatDate = lastUpdate.get(Calendar.YEAR) + "_" + 
    					((lastUpdate.get(Calendar.MONTH) + 1) < 10 ? "0" : "") +
    					(lastUpdate.get(Calendar.MONTH) + 1) + "_" + 
    					(lastUpdate.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
    					lastUpdate.get(Calendar.DAY_OF_MONTH);
    			List<String> zipURLString = new LinkedList<String>();
    			zipURLString.add(NetworkLayer.repo + "/slices/CUMULATIVE_COMMODITIES_" + formatDate + ".zip");
    			zipURLString.add(NetworkLayer.repo + "/slices/CUMULATIVE_CREDITS_" + formatDate + ".zip");
    			zipURLString.add(NetworkLayer.repo + "/slices/CUMULATIVE_EQUITIES_" + formatDate + ".zip");
    			zipURLString.add(NetworkLayer.repo + "/slices/CUMULATIVE_FOREX_" + formatDate + ".zip");
    			zipURLString.add(NetworkLayer.repo + "/slices/CUMULATIVE_RATES_" + formatDate + ".zip");
    			
    			for (String s : zipURLString) {
    				try {
						List<Trade> newTrades = ParseZIP.downloadData(s, NetworkLayer.splitter, NetworkLayer.secondarySplitter);
						for (Trade t : newTrades) {
							Database.addTrade(t);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
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
    			NetworkLayer.lastUpdateDate = lastUpdate.getTime();
    			System.out.println("NetworkLayer: current version is " + lastUpdate.get(Calendar.YEAR) + " "+
        			(lastUpdate.get(Calendar.MONTH) + 1) + " " + lastUpdate.get(Calendar.DAY_OF_MONTH));
        	}
        	System.out.println("NetworkLayer: initial update completed");
        }
    }  


}
