package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/*
 * 
 * @author: ts579
 * 
 */

public class ParseZIP {
	
	public static void unZip(String zipFile){
		
	}
	
	public static void parseCSV(String file, String splitBy){
		String line;
		
		try {
	 		BufferedReader br = new BufferedReader(new FileReader(file));
	 		while((line = br.readLine()) != null){
	 			String[] trade = line.split(splitBy);
	 			
	 			System.out.println(trade[0]);
	 		}
	 		
	 		br.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ParseZIP.parseCSV("COMMODITIES.csv", ",");

	}

}
