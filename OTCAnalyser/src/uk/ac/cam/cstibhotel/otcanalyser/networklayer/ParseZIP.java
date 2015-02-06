package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.ZipInputStream;
import java.net.URL;
import java.text.ParseException;

import uk.ac.cam.cstibhotel.otcanalyser.trade.*;


/*
 * 
 * @author: ts579
 * 
 */

public class ParseZIP {
	
	//TODO: implement this bit
	private static Trade stringVectorToTrade(String[] tradeIn){
		Trade tradeOut = new Trade();
		//Setting fields in tradeOut appropriately
		try{
			
	
			tradeOut.setDisseminationID(Long.parseLong(tradeIn[0]));
			tradeOut.setOriginalDisseminationID(Long.parseLong(tradeIn[1]));
			tradeOut.setAction(Action.parseAction(tradeIn[2]));
			
			//Parsing the execution timestamp date
			DateFormat etf = new SimpleDateFormat("yyy-MM-dd'T'kk:mm:ss");
			tradeOut.setExecutionTimestamp(etf.parse(tradeIn[3]));
			
			//Parsing cleared. U == uncleared
			boolean cleared;
			if(tradeIn[4] == "U"){
				cleared = false;
			}
			else if(tradeIn[4] == "C"){
				cleared = true;
			}
			else throw new TradeFieldFormatException("ERROR: Cleared field is wrongly formatted");
			
			tradeOut.setCleared(cleared);
			
			//Collateralization
			tradeOut.setCollateralization(Collateralization.parse(tradeIn[5]));
			
			//End user exception
			boolean eue = false;
			if(tradeIn[6] == "N"){
				cleared = false;
			}
			else if((tradeIn[6] == "Y") || (tradeIn[6] == "")){
				cleared = true;
			}
			else throw new TradeFieldFormatException("ERROR: End user exception field is wrongly formatted");
			
			tradeOut.setEndUserException(eue);
			
			//Other price affecting term
			
			

		}
		catch(ActionFormatException e){
			e.printStackTrace();
		}
		catch(CollateralizationFormatException e){
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		catch(TradeFieldFormatException e){
			e.printStackTrace();
		}
		return tradeOut;
	}
	
	public static LinkedList<Trade> downloadData(String zipFile, String splitBy){
		String line;
		int i = 0;
		LinkedList<Trade> dataOut = new LinkedList<Trade>();
		
		try{
			URL url = new URL(zipFile);
			ZipInputStream zis = new ZipInputStream(url.openStream());
			zis.getNextEntry();
			InputStreamReader isr = new InputStreamReader(zis);
			BufferedReader br = new BufferedReader(isr);
			
			//reading line by line
			while((line = br.readLine()) != null){
	 			//do not read the first line
				if(i!=0){
	 				dataOut.add(stringVectorToTrade(line.split(splitBy)));
	 			}
	 			i++;
	 		}
	 		
	 		br.close();
	 		
	 		return dataOut;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		ParseZIP.downloadData("https://kgc0418-tdw-data-0.s3.amazonaws.com/slices/CUMULATIVE_COMMODITIES_2015_02_04.zip",",");
	}

}
