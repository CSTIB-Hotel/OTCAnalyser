package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class UPI {
	
	// This is a class to represent the UPI/taxonomy of a trade.
	// These have the format:
	// [Asset class]:[Base product]:[Sub-product]:[Transaction type]:[Settlement type]
	
	String fullTaxonomy;
	AssetClass assetClass;
	String baseProduct;
	String subProduct;
	String transactionType;
	String settlementType;
	
	/*
	* Splits a string containing a full taxonomy into its component parts to allow
	* for easier access.
	*/	
	public UPI(String taxonomy) {
	}
	
}
