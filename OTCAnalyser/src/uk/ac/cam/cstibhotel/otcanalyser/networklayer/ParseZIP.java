package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipInputStream;
import java.net.URL;

import uk.ac.cam.cstibhotel.otcanalyser.trade.*;


/*
 * 
 * @author: ts579
 * 
 */

public class ParseZIP {
	
	//TODO: implement this bit
	private static Trade stringVectorToTrade(String[] trade){
		Trade test = null;
		return test;
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
