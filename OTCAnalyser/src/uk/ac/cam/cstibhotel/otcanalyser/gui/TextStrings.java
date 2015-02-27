package uk.ac.cam.cstibhotel.otcanalyser.gui;

//The strings used in the dropdown boxes

public class TextStrings {
	
	static final String[] Currencies = {"", "USD", "JPY", "KRW", "AUD", "BRL", "BBL", "EUR", "CAD", "GBP", "MYR", "CNY", "RUB", "INR", "TWD", "PHP", "CLP", "IDR", "COP", "VND", "PEN", "ARS", "ZMW", "NGN", "DOP", "NZD", "PLN", "MMBTU", "MT", "MXN", "CHF", "THB", "SGD", "ZAR", "HKD", "SEK", "NOK", "DKK", "HUF", "CLF", "TRY", "T", "THERM", "CZK", "MXV", "ILS", "XPD", "XAU", "XAG", "XPT"};
	
	static final String[] TradeType = {"Swap","Option"};
	
	static final String[] Assets = {"Credit","Interest","Commodity","Foreign Exchange","Equity"};
	
	static final String[] CreditBaseProducts = {"All","Single Name","Index Tranche","Index", "Total Return Swap","Swaptions","Exotic"};
	static final String[] CreditSingleNameSubProducts = {"ABS","Corporate","Recovery CDS","Loans","Muni","Sovereign"};
	static final String[] CreditIndexTrancheSubProducts = {"CDX","LCDX","MCDX","CDX Structured Tranche","iTraxx","iTraxx Structured Trade","ABX"};
	static final String[] CreditIndexSubProducts = {"CDX","LCDXstock market analysis equations","MCDX","iTraxx","ABX","CMBX","IOS","MBX","PO","PrimeX","TRX"};
	static final String[] CreditSwaptionsSubProducts = {"iTraxx","Muni","CDX","MCDX","Sovereign","Corporate"};
	static final String[] CreditExoticSubProducts = {"Corporate","Structured CDS","Other"};
	
	static final String[] InterestBaseProducts = {"All","IR Swap","FRA","Cap Floor","Cross Currency","Option","Exotic"};
	static final String[] InterestIRSwapSubProducts = {"Fixed Float","Fixed Fixed","Basis","Inflation","OIS"};
	static final String[] InterestCrossCurrencySubProducts = {"Basis","Fixed Float","Fixed Fixed"};
	static final String[] InterestOptionSubProducts = {"Debt Option","Swaption"};
	
	static final String[] CommodityBaseProducts = {"All","Metals","Energy","Index","Agriculture","Environmental","Freight","Multi Commodity Exotic"};
	static final String[] CommodityMetalsSubProducts = {"Precious","Non-precious"};
	static final String[] CommodityEnergySubProducts = {"Oil","Nat Gas","Coal","Elec","Inter Energy"};
	static final String[] CommodityAgricultureSubProducts = {"Grains Oilseeds","Dairy","Livestock","Forestry","Softs"};
	static final String[] CommodityEnvironmentalSubProducts = {"Weather","Emissions"};
	
	static final String[] ForexBaseProducts = {"All","Spot","NDF","NDO","Forward,Vanilla Option","Simple Exotic","Complex Exotic"};
	static final String[] ForexSimpleExoticSubProducts = {"Barrier","Digital"};
	
	static final String[] EquityBaseProducts = {"All","Swap","Portfolio Swap","Contract for Difference","Option","Forward","Other"};
	static final String[] EquitySwapSubProducts = {"Price Return Basic Performance","Parameter Return Dividend","Parameter Return Variance","Parameter Return Volatility"};
	static final String[] EquityPortfolioSwapSubProducts = {"Price Return Basic Performance"};
	static final String[] EquityContractForDifferenceSubProducts = {"Price Return Basic Performance"};
	static final String[] EquityOptionSubProducts = {"Price Return Basic Performance","Parameter Return Dividend","Parameter Return Variance","Parameter Return Volatility"};
	static final String[] EquityForwardSubProducts = {"Price Return Basic Performance"};
	
	static final String[] SettlementTypes = {"Cash","Physical"};
	
	static final Integer[] Years = {2015,2014,2013};
	static final String[] Months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	static final Integer[] Days28 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
	static final Integer[] Days30 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
	static final Integer[] Days31 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
}
