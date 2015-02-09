package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.zip.ZipInputStream;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.ActionFormatException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Collateralization;
import uk.ac.cam.cstibhotel.otcanalyser.trade.CollateralizationFormatException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;


/*
 * 
 * @author: ts579
 * 
 */

public class ParseZIP {
	
	private static Boolean convertToBool(String tv, String fv, String input) {
		if(input.equals(tv)){
			return true;
		}
		else if(input.equals(fv)){
			return false;
		}
		else {
			return null;
		}
	}
	
	private static long parseLong(String input) {
		if (input.equals("")) {
			return 0;
		} else {
			return Long.parseLong(input);
		}
	}
	
	//TODO: implement this bit
	private static Trade stringVectorToTrade(String[] tradeIn){
		Trade tradeOut = new Trade();
		//Setting fields in tradeOut appropriately
		try{
			tradeOut.setDisseminationID(parseLong(tradeIn[0]));
			tradeOut.setOriginalDisseminationID(parseLong(tradeIn[1]));
			tradeOut.setAction(Action.parseAct(tradeIn[2]));
			
			//Parsing the execution timestamp date
			try {
				DateFormat etf = new SimpleDateFormat("yyy-MM-dd'T'kk:mm:ss");
				tradeOut.setExecutionTimestamp(etf.parse(tradeIn[3]));
			} catch ( ParseException e ) {
				tradeOut.setExecutionTimestamp(null);
			}
			
			//Cleared
			tradeOut.setCleared(convertToBool("C", "U", tradeIn[4]));
			
			//Collateralization
			tradeOut.setCollateralization(Collateralization.parseColl(tradeIn[5]));
			
			//End user exception
			tradeOut.setEndUserException(convertToBool("Y", "N", tradeIn[6]));
			
			//Other price affecting term TODO: Is this the INDICATION_OF_OTHER_PRICE_AFFECTING_TERM?
			tradeOut.setBespoke(convertToBool("Y", "N", tradeIn[7]));
			
			//BLOCK
			tradeOut.setBlockTrades(convertToBool("Y", "N", tradeIn[8]));
			
			//EXECUTION_VENUE
			tradeOut.setExecutionVenue(convertToBool("ON","OFF", tradeIn[9]));
			
			
			

		}
		catch(ActionFormatException e){
			e.printStackTrace();
		}
		catch(CollateralizationFormatException e){
			e.printStackTrace();
		}
		return tradeOut;
	}
	
	public static LinkedList<Trade> downloadData(String zipFile, String splitBy, String secondarySplitBy) throws IOException {
		String line;
		int i = 0;
		LinkedList<Trade> dataOut = new LinkedList<Trade>();
		
		URL url = new URL(zipFile);
		ZipInputStream zis = new ZipInputStream(url.openStream());
		zis.getNextEntry();
		InputStreamReader isr = new InputStreamReader(zis);
		BufferedReader br = new BufferedReader(isr);
		
		//reading line by line
		while((line = br.readLine()) != null){
 			//do not read the first line
			if(i!=0){
				//fix quotes around dissemination ID
				if (!line.startsWith("\"")) 
					line = "\"" + line;
				if (line.charAt(line.indexOf(",") - 1) != '"') {
					line = line.substring(0, line.indexOf(",")) + "\"" + line.substring(line.indexOf(","));
				}
				
				String[] tradeIn = line.split(splitBy);
				
				/*
				 * todo: research normal # of fields
				 * currently it is around 45-48
				 */
				
				if (tradeIn.length < 10)
					tradeIn = line.split(secondarySplitBy);
				
				for (int j = 0; j < tradeIn.length; j++) {
					//remove quotes
					if (tradeIn[j].startsWith("\""))
						tradeIn[j] = tradeIn[j].substring(1, tradeIn[j].length());
					if (tradeIn[j].endsWith("\""))
						tradeIn[j] = tradeIn[j].substring(0, tradeIn[j].length() - 1);
				}

 				dataOut.add(stringVectorToTrade(tradeIn));
 			}
 			i++;
 		}
		
 		try {
 			br.close();
 		} catch (IOException IOEx) {
 			//safe to ignore
 		}
 		
 		return dataOut;
	}
	
	public static void main(String[] args) {
		try {
			ParseZIP.downloadData("https://kgc0418-tdw-data-0.s3.amazonaws.com/slices/CUMULATIVE_COMMODITIES_2015_02_04.zip","\",\"", ",");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
