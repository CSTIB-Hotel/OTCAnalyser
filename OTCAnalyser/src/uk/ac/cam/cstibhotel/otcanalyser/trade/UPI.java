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
	public UPI(String taxonomy) throws InvalidTaxonomyException, EmptyTaxonomyException {
		fullTaxonomy = taxonomy;
		
		if(taxonomy.equals("")) {
			throw new EmptyTaxonomyException();
		}
		
		String[] splitTaxonomy = taxonomy.split(":");
		
		try {
			// If the taxonomy splits into > 5 parts or < 2 parts, we can't treat it as valid
			if (splitTaxonomy.length > 5 || splitTaxonomy.length < 2) {
				throw new InvalidTaxonomyException(taxonomy);
			}
			
			// Set the fields dependent on asset class
			switch (splitTaxonomy[0]) {
			case "Commodity":
				assetClass = AssetClass.COMMODITY;
				baseProduct = splitTaxonomy[1];
				subProduct = splitTaxonomy[2];
				transactionType = splitTaxonomy[3];
				if (splitTaxonomy.length != 4) {
					settlementType = splitTaxonomy[4];
				}
				break;
			case "Credit":
				if (splitTaxonomy.length >= 5) {
					throw new InvalidTaxonomyException(taxonomy);
				}
				assetClass = AssetClass.CREDIT;
				baseProduct = splitTaxonomy[1];
				subProduct = splitTaxonomy[2];
				transactionType = splitTaxonomy[3];
				break;
			case "Equity":
				if (splitTaxonomy.length >= 5) {
					throw new InvalidTaxonomyException(taxonomy);
				}
				assetClass = AssetClass.EQUITY;
				baseProduct= splitTaxonomy[1];
				subProduct = splitTaxonomy[2];
				transactionType = splitTaxonomy[3];
				break;
			case "ForeignExchange":
				if (splitTaxonomy.length >= 4) {
					throw new InvalidTaxonomyException(taxonomy);
				}
				assetClass = AssetClass.FOREX;
				baseProduct= splitTaxonomy[1];
				if (splitTaxonomy.length != 2) {
					subProduct = splitTaxonomy[2];
				}
				break;
			case "InterestRate":
				if (splitTaxonomy.length >= 4) {
					throw new InvalidTaxonomyException(taxonomy);
				}
				assetClass = AssetClass.RATES;
				baseProduct= splitTaxonomy[1];
				if (splitTaxonomy.length != 2) {
					subProduct = splitTaxonomy[2];
				}
				break;
			default:
				throw new InvalidTaxonomyException(taxonomy);
			} 
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidTaxonomyException(taxonomy);
		}
	}
	
}
