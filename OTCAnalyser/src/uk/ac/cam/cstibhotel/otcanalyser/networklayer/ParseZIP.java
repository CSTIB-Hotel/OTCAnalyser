package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/*
 * 
 * @author: ts579
 * 
 */

public class ParseZIP {
	
	public static void zipToCsv(String zipFile, String splitBy){
		String line;
		try{
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(fis);
			zis.getNextEntry();
			InputStreamReader isr = new InputStreamReader(zis);
			BufferedReader br = new BufferedReader(isr);
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
		ParseZIP.zipToCsv("SLICE_COMMODITIES_2015_02_05_278.zip",",");
	}

}
