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
	
	private static Boolean convertToBool(String tv, String fv, String input) throws BooleanFieldFormatException{
		if(input == tv){
			return true;
		}
		else if(input == fv){
			return false;
		}
		else if(input ==""){
			return null;
		}
		else throw new BooleanFieldFormatException();
	}
	
	//TODO: implement this bit
	private static Trade stringVectorToTrade(String[] tradeIn){
		Trade tradeOut = new Trade();
		//Setting fields in tradeOut appropriately
		try{
			
	
			tradeOut.setDisseminationID(Long.parseLong(tradeIn[0]));
			tradeOut.setOriginalDisseminationID(Long.parseLong(tradeIn[1]));
			tradeOut.setAction(Action.parseAct(tradeIn[2]));
			
			//Parsing the execution timestamp date
			DateFormat etf = new SimpleDateFormat("yyy-MM-dd'T'kk:mm:ss");
			tradeOut.setExecutionTimestamp(etf.parse(tradeIn[3]));
			
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
		catch (ParseException e) {
			e.printStackTrace();
		}
		catch(BooleanFieldFormatException e){
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
