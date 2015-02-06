package uk.ac.cam.cstibhotel.otcanalyser.gui;

public class TextStrings {
	static final String[] Assets = {"Credit","Interest","Commodity","Foreign Exchange","Equity"};
	static final String[] CreditBaseProducts = {"Single Name","Index Tranche","Index", "TotalReturn Swap","Swaptions","Exotic"};
	static final String[] CreditSingleNameSubProducts = {"ABS","Corporate","Recovery CDS","Loans","Muni","Sovereign","ABS"};
	static final String[] CreditIndexTrancheSubProducts = {"CDX","LCDX","MCDX","CDX Structured Tranche","iTraxx","iTraxx Structured Trade","ABX"};
	static final String[] CreditIndexSubProducts = {"CDX","LCDX","MCDX","iTraxx","ABX","CMBX","IOS","MBX","PO","PrimeX","TRX"};
	//STILL MORE TODO
	static final String[] InterestBaseProducts = {"IR Swap","FRA","Cap Floor","Cross Currency","Option","Exotic"};
	static final String[] InterestIRSwapSubProducts = {"Fixed Float","Fixed Fixed","Basis","Inflation","OIS"};
	static final String[] InterestCrossCurrencySubProducts = {"Basis","Fixed Float","Fixed Fixed"};
	static final String[] InterestOptionSubProducts = {"Debt Option","Swaption"};
	static final String[] CommodityBaseProducts = {"Metals","Energy","Index","Agriculture","Environmental","Freight","Multi Commodity Exotic"};
	static final String[] CommodityMetalsSubProducts = {"Precious","Non-precious"};
	static final String[] CommodityEnergySubProducts = {"Oil","Nat Gas","Coal","Elec","Inter Energy"};
	static final String[] CommodityAgricultureSubProducts = {"Grains Oilseeds","Dairy","Livestock","Forestry","Softs"};
	static final String[] CommodityEnvironmentalSubProducts = {"Weather","Emissions"};
	static final String[] ForexBaseProducts = {"Spot","NDF","NDO","Forward,Vanilla Option","Simple Exotic","Complex Exotic"};
	static final String[] ForexSimpleExoticSubProducts = {"Barrier","Digital"};
	//EQUITY TODO
	static final String[] Years = {"2015","2014","2013","2012","2011","2010"};
	static final String[] Months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	static final Integer[] Days28 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
	static final Integer[] Days30 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
	static final Integer[] Days31 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
	
}
