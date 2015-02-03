package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/*
 * This is a unit test for the UPI class, which takes a full taxonomy and breaks it down into
 * component parts.
 */
public class UPITest {
	
	@Test
	public void testValidCommodityTaxonomy() throws InvalidTaxonomyException {
		String taxonomy = "Commodity:Metals:Precious:SpotFwd:Physical";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.Commodity);
		assertEquals(toTest.baseProduct, "Metals");
		assertEquals(toTest.subProduct, "Precious");
		assertEquals(toTest.transactionType, "SpotFwd");
		assertEquals(toTest.settlementType, "Physical");
	}
	
	@Test
	public void testValidCreditTaxonomy() throws InvalidTaxonomyException {
		String taxonomy = "Credit:SingleName:Corporate:StandardEuropeanCorporate";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.Credit);
		assertEquals(toTest.baseProduct, "SingleName");
		assertEquals(toTest.subProduct, "Corporate");
		assertEquals(toTest.transactionType, "StandardEuropeanCorporate");
		assertEquals(toTest.settlementType, null);
	}
	
	@Test
	public void testValidEquityTaxonomy() throws InvalidTaxonomyException {
		String taxonomy = "Equity:PortfolioSwap:PriceReturnBasicPerformance:SingleName";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.Equity);
		assertEquals(toTest.baseProduct, "PortfolioSwap");
		assertEquals(toTest.subProduct, "PriceReturnBasicPerformance");
		assertEquals(toTest.transactionType, "SingleName");
		assertEquals(toTest.settlementType, null);
	}
	
	@Test
	public void testValidForeignExchangeTaxonomyWithSubProduct() throws InvalidTaxonomyException {
		String taxonomy = "ForeignExchange:SimpleExotic:IntradayDigital";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.ForeignExchange);
		assertEquals(toTest.baseProduct, "SimpleExotic");
		assertEquals(toTest.subProduct, "IntradayDigital");
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	@Test
	public void testValidForeignExchangeTaxonomyWithoutSubProduct() 
			throws InvalidTaxonomyException {
		String taxonomy = "ForeignExchange:NDF";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.ForeignExchange);
		assertEquals(toTest.baseProduct, "NDF");
		assertEquals(toTest.subProduct, null);
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	@Test
	public void testValidRatesTaxonomyWithSubProduct() throws InvalidTaxonomyException {
		String taxonomy = "InterestRate:CrossCurrency:Basis";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.Rates);
		assertEquals(toTest.baseProduct, "CrossCurrency");
		assertEquals(toTest.subProduct, "Basis");
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	@Test
	public void testValidRatesTaxonomyWithoutSubProduct() throws InvalidTaxonomyException {
		String taxonomy = "InterestRate:CapFloor";
		UPI toTest = new UPI(taxonomy);
		assertEquals(toTest.assetClass, AssetClass.Rates);
		assertEquals(toTest.baseProduct, "CapFloor");
		assertEquals(toTest.subProduct, null);
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
}
