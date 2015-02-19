package uk.ac.cam.cstibhotel.otcanalyser.trade;

/*
 * This class contains the possible substrings of a UPI. There is a one-to-one equivalence with the
 * strings in the TextStrings class in the GUI package.
 */

public class UPIStrings {
	
	static final String[] TradeType = {"Swap","Option"};
	
	static final String[] Assets = {"Credit","InterestRate","Commodity","ForeignExchange","Equity"};

	static final String[] CreditBaseProducts = {"All","SingleName","IndexTranche","Index",
			"TotalReturnSwap","Swaptions","Exotic"};
	static final String[] CreditSingleNameSubProducts = {"ABS","Corporate","RecoveryCDS","Loans",
			"Muni","Sovereign"};
	static final String[] CreditIndexTrancheSubProducts = {"CDX","LCDX","MCDX",
			"CDXStructuredTranche","iTraxx","iTraxx Structured Trade","ABX"};
	static final String[] CreditIndexSubProducts = {"CDX","LCDXstock market analysis equations",
			"MCDX","iTraxx","ABX","CMBX","IOS","MBX","PO","PrimeX","TRX"};
	static final String[] CreditSwaptionsSubProducts = {"iTraxx","Muni","CDX","MCDX","Sovereign",
			"Corporate"};
	static final String[] CreditExoticSubProducts = {"Corporate","StructuredCDS","Other"};
	
	static final String[] InterestBaseProducts = {"All","IRSwap","FRA","CapFloor","CrossCurrency",
			"Option","Exotic"};
	static final String[] InterestIRSwapSubProducts = {"FixedFloat","FixedFixed","Basis",
			"Inflation","OIS"};
	static final String[] InterestCrossCurrencySubProducts = {"Basis","FixedFloat","FixedFixed"};
	static final String[] InterestOptionSubProducts = {"DebtOption","Swaption"};
	
	static final String[] CommodityBaseProducts = {"All","Metals","Energy","Index","Agriculture",
			"Environmental","Freight","MultiCommodityExotic"};
	static final String[] CommodityMetalsSubProducts = {"Precious","NonPrecious"};
	static final String[] CommodityEnergySubProducts = {"Oil","NatGas","Coal","Elec","InterEnergy"};
	static final String[] CommodityAgricultureSubProducts = {"GrainsOilSeeds","Dairy","Livestock",
			"Forestry","Softs"};
	static final String[] CommodityEnvironmentalSubProducts = {"Weather","Emissions"};
	
	static final String[] ForexBaseProducts = {"All","Spot","NDF","NDO","Forward,VanillaOption",
			"SimpleExotic","ComplexExotic"};
	static final String[] ForexSimpleExoticSubProducts = {"Barrier","Digital"};
	
	static final String[] EquityBaseProducts = {"All","Swap","PortfolioSwap",
			"ContractForDifference","Option","Forward","Other"};
	static final String[] EquitySwapSubProducts = {"PriceReturnBasicPerformance",
			"ParameterReturnDividend","ParameterReturnVariance","ParameterReturnVolatility"};
	static final String[] EquityPortfolioSwapSubProducts = {"PriceReturnBasicPerformance"};
	static final String[] EquityContractForDifferenceSubProducts = {"PriceReturnBasicPerformance"};
	static final String[] EquityOptionSubProducts = {"PriceReturnBasicPerformance",
			"ParameterReturnDividend","ParameterReturnVariance","ParameterReturnVolatility"};
	static final String[] EquityForwardSubProducts = {"PriceReturnBasicPerformance"};
	
	static final String[] SettlementTypes = {"Cash","Physical"};
	
	static final Integer[] Years = {2015,2014,2013,2012,2011,2010};
	static final String[] Months = {"January","February","March","April","May","June","July",
			"August","September","October","November","December"};
	static final Integer[] Days28 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
			25,26,27,28};
	static final Integer[] Days30 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
			25,26,27,28,29,30};
	static final Integer[] Days31 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
			25,26,27,2};
	
}
