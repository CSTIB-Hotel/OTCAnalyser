package uk.ac.cam.cstibhotel.otcanalyser.trade;

/*
 * This class contains the possible substrings of a UPI. There is a one-to-one equivalence with the
 * strings in the TextStrings class in the GUI package.
 */

public class UPIStrings {
	
	public static final String[] TradeType = {"Swap","Option"};
	
	public static final String[] Assets = {"Credit","InterestRate","Commodity","ForeignExchange",
			"Equity"};

	public static final String[] CreditBaseProducts = {"%","SingleName","IndexTranche","Index",
			"TotalReturnSwap","Swaptions","Exotic"};
	
	public static final String[][] CreditSubProducts = {
		{},
		{"ABS","Corporate","RecoveryCDS","Loans","Muni","Sovereign"},
		{"CDX","LCDX","MCDX","CDXStructuredTranche","iTraxx","iTraxx Structured Trade","ABX"},
		{"CDX","LCDXstock market analysis equations", "MCDX","iTraxx","ABX","CMBX","IOS","MBX","PO",
				"PrimeX","TRX"},
		{},
		{"iTraxx","Muni","CDX","MCDX","Sovereign","Corporate"},
		{"Corporate","StructuredCDS","Other"}
		};
	
	public static final String[] InterestBaseProducts = {"%","IRSwap","FRA","CapFloor",
			"CrossCurrency","Option","Exotic"};
	
	public static final String[][] InterestSubProducts = {
		{},
		{"FixedFloat","FixedFixed","Basis","Inflation","OIS"},
		{},
		{},
		{"Basis","FixedFloat","FixedFixed"},
		{"DebtOption","Swaption"},
		{}
	};

	public static final String[] CommodityBaseProducts = {"%","Metals","Energy","Index",
			"Agriculture","Environmental","Freight","MultiCommodityExotic"};
	
	public static final String[][] CommoditySubProducts = {
		{},
		{"Precious","NonPrecious"},
		{"Oil","NatGas","Coal","Elec","InterEnergy"},
		{},
		{"GrainsOilSeeds","Dairy","Livestock","Forestry","Softs"},
		{"Weather","Emissions"},
		{},
		{}
	};
	
	public static final String[] ForexBaseProducts = {"%","Spot","NDF","NDO","Forward",
			"VanillaOption","SimpleExotic","ComplexExotic"};
	
	public static final String[][] ForexSubProducts = {
		{},
		{},
		{},
		{},
		{},
		{},
		{"Barrier","Digital"},
		{}
	};
	
	public static final String[] EquityBaseProducts = {"%","Swap","PortfolioSwap",
			"ContractForDifference","Option","Forward","Other"};
	
	public static final String[][] EquitySubProducts = {
		{},
		{"PriceReturnBasicPerformance","ParameterReturnDividend","ParameterReturnVariance",
				"ParameterReturnVolatility"},
		{"PriceReturnBasicPerformance"},
		{"PriceReturnBasicPerformance"},
		{"PriceReturnBasicPerformance","ParameterReturnDividend","ParameterReturnVariance",
				"ParameterReturnVolatility"},
		{"PriceReturnBasicPerformance"},
		{}
	};
	
	public static final String[] SettlementTypes = {"Cash","Physical"};
	
}
