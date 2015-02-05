package uk.ac.cam.cstibhotel.otcanalyser.trade;

/* 
 * This is a class to represent the UPI/taxonomy of a trade.
 * 
 * These have the format:
 * [Asset class]:[Base product]:[Sub-product]:[Transaction type]:[Settlement type]
 * 
 * Not every class of asset with have all of these fields filled - see the associated unit test
 * for more information
 */

public class UPI {
	
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
		
		String[] splitTaxonomy = fullTaxonomy.split(":");
		
		try {
			// If the taxonomy splits into > 5 parts or < 2 parts, we can't treat it as valid
			if (splitTaxonomy.length >= 6) {
				throw new InvalidTaxonomyException(taxonomy);
			}
			// All asset classes have a base product, set this now
			baseProduct = splitTaxonomy[1];
			
			// A foreign exchange or rates taxonomy may not necessarily have a sub-product, so 
			// check for null before setting this
			if (splitTaxonomy[2] != null) {
				subProduct = splitTaxonomy[2];
			}
			
			// Set the other fields dependent on asset class
			switch (splitTaxonomy[0]) {
			case "Commodity":
				if (splitTaxonomy.length == 5) {
					assetClass = AssetClass.Commodity;
					baseProduct = splitTaxonomy[1];
					subProduct = splitTaxonomy[2];
					transactionType = splitTaxonomy[3];
					settlementType = splitTaxonomy[4];
				} else {
					throw new InvalidTaxonomyException(taxonomy);
				}
				break;
			case "Credit":
				assetClass = AssetClass.Credit;
				transactionType = splitTaxonomy[3];
				break;
			case "Equity":
				assetClass = AssetClass.Equity;
				transactionType = splitTaxonomy[3];
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
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidTaxonomyException(taxonomy);
		} catch (InvalidTaxonomyException e) {
			System.err.println("The taxonomy: " + e.getMessage() + " is invalid.");
		}
	}
	
}
