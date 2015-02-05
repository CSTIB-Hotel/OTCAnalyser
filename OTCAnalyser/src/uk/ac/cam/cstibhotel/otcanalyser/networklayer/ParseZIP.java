package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.net.URL;


/*
 * 
 * @author: ts579
 * 
 */

public class ParseZIP {
	
	//TODO: Translate the csv to Trade objects
	public static void zipToCsv(String zipFile, String splitBy){
		String line;
		try{
			URL url = new URL(zipFile);
			ZipInputStream zis = new ZipInputStream(url.openStream());
			zis.getNextEntry();
			InputStreamReader isr = new InputStreamReader(zis);
			BufferedReader br = new BufferedReader(isr);
			while((line = br.readLine()) != null){
	 			String[] trade = line.split(splitBy);
	 			for(String a : trade){
	 				System.out.print(a+":");
	 			}
	 			System.out.println("");
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
		ParseZIP.zipToCsv("https://kgc0418-tdw-data-0.s3.amazonaws.com/slices/SLICE_COMMODITIES_2015_02_05_569.zip",",");
	}

}
