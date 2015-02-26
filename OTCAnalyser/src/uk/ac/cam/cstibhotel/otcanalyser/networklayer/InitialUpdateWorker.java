package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.StatusBar;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

public class InitialUpdateWorker extends Thread {
	private boolean running;
	
	@Override
	public void run() {
    	System.out.println("NetworkLayer: initial update requested");
    	running = true;
        synchronized(NetworkLayer.targetUpdateDate) {
        	initialUpdate();
        	if (running) sliceUpdate();
        }
    }
	
	private void initialUpdate() {
		System.out.println("NetworkLayer: initial update started");
    	Date now = new Date();
    	Calendar target = Calendar.getInstance();
    	target.setTime(now);
    	target.add(Calendar.DATE, -1);
    	NetworkLayer.targetUpdateDate = target.getTime();
    	
    	Calendar lastUpdate = Calendar.getInstance();
    	lastUpdate.setTime(Database.getDB().getLastUpdateTime());
    	lastUpdate.add(Calendar.DATE, -1);
    	
    	while ((target.get(Calendar.YEAR) != lastUpdate.get(Calendar.YEAR) ||
    			target.get(Calendar.MONTH) + 1 != lastUpdate.get(Calendar.MONTH) + 1 ||
    			target.get(Calendar.DAY_OF_MONTH) != lastUpdate.get(Calendar.DAY_OF_MONTH)) 
    			&& running) {
    		
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
				boolean done = false;
				
				while (!done) {
					try {
						List<Trade> newTrades = ParseZIP.downloadData(s, NetworkLayer.splitter, NetworkLayer.secondarySplitter);
						Database.getDB().addTrade(newTrades);
						done = true;
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						if (e.getMessage().startsWith("Server returned HTTP response code: 403")) {
							done = true; //could log that server doesn't have data
						} else {
							StatusBar.setMessage("Error: Could not download data for " + formatDate + 
									". Check your internet connection.", 0);
							try {
								Thread.sleep(10000);
							}
							catch (InterruptedException ie) {
								
							}
						}
						e.printStackTrace();
					}
				}
			}

			System.out.println("NetworkLayer: current version is " + lastUpdate.get(Calendar.YEAR) + " "+
    			(lastUpdate.get(Calendar.MONTH) + 1) + " " + lastUpdate.get(Calendar.DAY_OF_MONTH));
			StatusBar.setMessage("NetworkLayer: current version is " + lastUpdate.get(Calendar.YEAR) + " "+
        			(lastUpdate.get(Calendar.MONTH) + 1) + " " + lastUpdate.get(Calendar.DAY_OF_MONTH), 0);
    	}
    	System.out.println("NetworkLayer: initial update completed");
    }
	
	
	private void sliceUpdate() {
    	System.out.println("NetworkLayer: slice update started");

    	for (;;) {
        	Calendar today = Calendar.getInstance();
        	String formatDate = today.get(Calendar.YEAR) + "_" + 
					((today.get(Calendar.MONTH) + 1) < 10 ? "0" : "") +
					(today.get(Calendar.MONTH) + 1) + "_" + 
					(today.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
					today.get(Calendar.DAY_OF_MONTH);
        	
        	int slice[] = new int[]{1, 1, 1, 1, 1};
        	int todayct = 0;
        	
        	while (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
        		
    			String[] zipURLString = new String[5];
    			zipURLString[0] = NetworkLayer.repo + "/slices/SLICE_COMMODITIES_" + formatDate + "_" + slice[0] + ".zip";
    			zipURLString[1] = NetworkLayer.repo + "/slices/SLICE_CREDITS_" + formatDate + "_" + slice[1] + ".zip";
    			zipURLString[2] = NetworkLayer.repo + "/slices/SLICE_EQUITIES_" + formatDate + "_" + slice[2] + ".zip";
    			zipURLString[3] = NetworkLayer.repo + "/slices/SLICE_FOREX_" + formatDate + "_" + slice[3] + ".zip";
    			zipURLString[4] = NetworkLayer.repo + "/slices/SLICE_RATES_" + formatDate + "_" + slice[4] + ".zip";
    			
    			boolean gotAnything = false;
    			for (int i = 0; i<5; i++) {
    				boolean done = false;
    				
    				while (!done) {
    					try {
    						List<Trade> newTrades = ParseZIP.downloadData(zipURLString[i], NetworkLayer.splitter, NetworkLayer.secondarySplitter);
    						Database.getDB().addTrade(newTrades);
    						slice[i]++;
    						gotAnything = true;
    						StatusBar.setMessage(++todayct + " slices received today.", 0);
    						done = true;
    					} catch (MalformedURLException e) {
    						e.printStackTrace();
    					} catch (IOException e) {
    						if (e.getMessage().startsWith("Server returned HTTP response code: 403")) {
    							done = true;
    						} else {
    							StatusBar.setMessage("Error: Could not download slice for " + formatDate + 
    									". Check your internet connection.", 0);
    							try {
    								Thread.sleep(10000);
    							}
    							catch (InterruptedException ie) {
    								
    							}
    							e.printStackTrace();
    						}
    					}
    				}
    			}
    			
    			if (!gotAnything)
    				try {
						Thread.sleep(1000);
					}
					catch (InterruptedException ie) {
						
					}
    			if (!running) {
            		return;
            	}
        	}
    	}
    }

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
