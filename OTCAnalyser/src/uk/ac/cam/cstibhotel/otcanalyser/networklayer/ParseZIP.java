package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.zip.ZipInputStream;
import java.util.Currency;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.ActionFormatException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClassFormatException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Collateralization;
import uk.ac.cam.cstibhotel.otcanalyser.trade.CollateralizationFormatException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.EmptyTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.InvalidTaxonomyException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.PFCDFormatException;
import uk.ac.cam.cstibhotel.otcanalyser.trade.PriceFormingContinuationData;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;


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
	
	private static Double parseDouble(String input) throws NumberFormatException{
		if(input.equals("")){
			return null;
		}
		else{
			return Double.parseDouble(input.replace(",",""));
		}
	}
	
	private static Date parseDate(DateFormat df, String s){
		try {
			return df.parse(s);
		} catch ( ParseException e ) {
			return null;
		}
	}
	
	//TODO: implement this bit
	private static Trade stringVectorToTrade(String[] tradeIn){
		Trade tradeOut = new Trade();
		DateFormat df = new SimpleDateFormat("yyy-MM-dd");
		DateFormat etf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
		//Setting fields in tradeOut appropriately
		try{
			tradeOut.setDisseminationID(parseLong(tradeIn[0]));
			tradeOut.setOriginalDisseminationID(parseLong(tradeIn[1]));
			tradeOut.setAction(Action.parseAct(tradeIn[2]));
			
			//Parsing the execution timestamp date
			tradeOut.setExecutionTimestamp(parseDate(etf,tradeIn[3]));
			
			
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
			
			//EFFECTIVE_DATE
			tradeOut.setEffectiveDate(parseDate(df,tradeIn[10]));
			
			//END_DATE
			tradeOut.setEndDate(parseDate(df,tradeIn[11]));
		
			
			//DAY_COUNT_CONVENTION
			tradeOut.setDayCountConvention(tradeIn[12]);
			
			//SETTLEMENT_CURRENCY
			/*try {
				Currency c = Currency.getInstance(tradeIn[13]);
				tradeOut.setSettlementCurrency(c.getDisplayName());
			} catch (IllegalArgumentException e){
				e.printStackTrace();
				//Illegal currency entry, not ISO 4217, it stays "GBP"
				//TODO: What does this mean that this field is empty? Why GBP the default?
			}*/
			
			//ASSET_CLASS
			tradeOut.setAssetClass(AssetClass.parseAssetC(tradeIn[14]));
			
			//SUB_ASSET_CLASS tradeIn[15]
			
			//UPI ie: taxonomy 
			tradeOut.setTaxonomy(new UPI(tradeIn[16]));
			
			//PRICE_FORMING_CONT_DATA
			tradeOut.setPriceFormingContinuationData(PriceFormingContinuationData.parsePFCD(tradeIn[17]));
			
			//UNDERLYING_ASSET_1
			tradeOut.setUnderlyingAsset1(tradeIn[18]);
			
			//UNDERLYING_ASSET_2
			tradeOut.setUnderlyingAsset2(tradeIn[19]);
			
			//PRICE_NOTATION_TYPE
			tradeOut.setPriceNotationType(tradeIn[20]);
			
			//PRICE_NOTATION
			tradeOut.setPriceNotation(parseDouble(tradeIn[21]));
				
			//ADDITIONAL_PRICE_NOTATION_TYPE
			tradeOut.setAdditionalPriceNotationType(tradeIn[22]);
			
			//ADDITIONAL_PRICE_NOTATION
			tradeOut.setAdditionalPriceNotation(parseDouble(tradeIn[23]));
			
			//NOTIONAL_CURRENCY_1
			tradeOut.setNotionalCurrency1(tradeIn[24]);
			
			//NOTIONAL_CURRENCY_2
			tradeOut.setNotionalCurrency2(tradeIn[25]);
			
			//ROUNDED_NOTIONAL_AMOUNT_1
			tradeOut.setRoundedNotionalAmount1(tradeIn[26]);
			
			//ROUNDED_NOTIONAL_AMOUNT_2
			tradeOut.setRoundedNotionalAmount2(tradeIn[27]);
			
			//PAYMENT_FREQUENCY_1
			tradeOut.setPaymentFrequency1(tradeIn[28]);
			
			//PAYMENT_FREQUENCY_2
			tradeOut.setPaymentFrequency2(tradeIn[29]);
			
			//RESET_FREQUENCY_1
			tradeOut.setResetFrequency1(tradeIn[30]);
			
			//RESET_FREQUENCY_2
			tradeOut.setResetFrequency2(tradeIn[31]);
			
			//EMBEDED_OPTION
			tradeOut.setEmbeddedOption(tradeIn[32]);

			//OPTION_STRIKE_PRICE
			tradeOut.setOptionStrikePrice(parseDouble(tradeIn[33]));
			
			//OPTION_TYPE
			tradeOut.setOptionType(tradeIn[34]);
			
			//OPTION_FAMILY
			tradeOut.setOptionFamily(tradeIn[35]);
			
			//OPTION_CURRENCY
			/*try {
				Currency c = Currency.getInstance(tradeIn[36]);
				tradeOut.setOptionCurrency(c.getDisplayName());
			} catch (IllegalArgumentException e){
				e.printStackTrace();
				//Illegal currency entry, not ISO 4217, it stays "GBP"
				//TODO: What does this mean that this field is empty? Why GBP the default?
			}*/
			
			//OPTION_PREMIUM
			tradeOut.setOptionPremium(parseDouble(tradeIn[37]));
			
			//OPTION_LOCK_PERIOD
			tradeOut.setEffectiveDate(parseDate(df,tradeIn[38]));
			
			//OPTION_EXPIRATION_DATE
			tradeOut.setEffectiveDate(parseDate(df,tradeIn[39]));
			
			//PRICE_NOTATION2_TYPE
			tradeOut.setPriceNotation2Type(tradeIn[40]);
			
			//PRICE_NOTATION2
			tradeOut.setPriceNotation2(parseDouble(tradeIn[41]));
			
			//PRICE_NOTATION3_TYPE
			tradeOut.setPriceNotation3Type(tradeIn[42]);
			
			//PRICE_NOTATION3
			tradeOut.setPriceNotation3(parseDouble(tradeIn[43]));
			
			
			
		} catch(ActionFormatException e){
			e.printStackTrace();
		} catch(CollateralizationFormatException e){
			e.printStackTrace();
		} catch(AssetClassFormatException e){
			e.printStackTrace();
		} catch (InvalidTaxonomyException e) {
			System.err.println("The taxonomy: "+ e.getMessage()+" is invalid. in ParseZIP.java");
		} catch (PFCDFormatException e) {
			e.printStackTrace();
		} catch (NumberFormatException e){ //thrown by praseDouble: price notation + additional price notation
			e.printStackTrace();
		} catch (EmptyTaxonomyException e) {
			e.printStackTrace();
		}
		
		return tradeOut;
	}
	
	public static LinkedList<Trade> downloadData(String zipFile, String splitBy, String secondarySplitBy) throws IOException, MalformedURLException {
		String line;
		String prevLine = "";
		int i = 0;
		LinkedList<Trade> dataOut = new LinkedList<Trade>();
		
		URL url = new URL(zipFile);
		ZipInputStream zis = new ZipInputStream(url.openStream());
		zis.getNextEntry();
		InputStreamReader isr = new InputStreamReader(zis);
		BufferedReader br = new BufferedReader(isr);
		
		//reading line by line
		while((line = br.readLine()) != null){
			if (prevLine.equals(line)) {
				break;
			}
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
				 * TODO: research normal # of fields
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
			prevLine = line;
			i++;
 		}
		
 		try {
 			br.close();
 		} catch (IOException IOEx) {
 			IOEx.printStackTrace();
 			//safe to ignore
 		}
 		
 		return dataOut;
	}
	
	public static void main(String[] args) {
		try {
			ParseZIP.downloadData("hgc0418-tdw-data-0.s3.amazonaws.com/slices/CUMULATIVE_CREDITS_2015_02_04.zip","\",\"", ",");
			System.out.println(Integer.parseInt("1,234".replace(",","")));
		} catch (MalformedURLException e){}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
