package uk.ac.cam.cstibhotel.otcanalyser.trade;

public class UPI {
	
	// This is a class to represent the UPI/taxonomy of a trade.
	// These have the format:
	// [Asset class]:[Base product]:[Sub-product]:[Transaction type]:[Settlement type]
	
	public String fullTaxonomy;
	public AssetClass assetClass;
	public String baseProduct;
	public String subProduct;
	public String transactionType;
	public String settlementType;
	
	/*
	* Splits a string containing a full taxonomy into its component parts to allow
	* for easier access.
	*/	
	public UPI(String taxonomy) throws InvalidTaxonomyException {
		fullTaxonomy = taxonomy;
		
		String[] splitTaxonomy = new String[5];
		splitTaxonomy = fullTaxonomy.split(":");
		
		try {
			
			switch (splitTaxonomy[0]) {
			case "Commodity":
				assetClass = AssetClass.Commodity;
				break;
			case "Credit":
				assetClass = AssetClass.Credit;
				break;
			case "Equity":
				assetClass = AssetClass.Equity;
				break;
			case "ForeignExhange":
				assetClass = AssetClass.ForeignExchange;
				break;
			case "Rates":
				assetClass = AssetClass.Rates;
				break;
			default:
				throw new InvalidTaxonomyException(taxonomy);
			} 
			
			baseProduct = splitTaxonomy[1];
			subProduct = splitTaxonomy[2];
			transactionType = splitTaxonomy[3];
			settlementType = splitTaxonomy[4];
		
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidTaxonomyException(taxonomy);
		}
	}
	
}
