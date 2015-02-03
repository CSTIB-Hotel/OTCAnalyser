package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/*
 * This is a unit test for the UPI class, which takes a full taxonomy and breaks it down into
 * component parts.
 */
public class UPITest {
	
	// Test a valid commodity taxonomy
	@Test
	public void testValidCommodityTaxonomy() throws InvalidTaxonomyException {
		UPI toTest = new UPI("Commodity:Metals:Precious:SpotFwd:Physical");
		assertEquals(toTest.assetClass, AssetClass.Commodity);
		assertEquals(toTest.baseProduct, "Metals");
		assertEquals(toTest.subProduct, "Precious");
		assertEquals(toTest.transactionType, "SpotFwd");
		assertEquals(toTest.settlementType, "Physical");
	}
	
	// Test a valid credit taxonomy
	@Test
	public void testValidCreditTaxonomy() throws InvalidTaxonomyException {
		UPI toTest = new UPI("Credit:SingleName:Corporate:StandardEuropeanCorporate");
		assertEquals(toTest.assetClass, AssetClass.Credit);
		assertEquals(toTest.baseProduct, "SingleName");
		assertEquals(toTest.subProduct, "Corporate");
		assertEquals(toTest.transactionType, "StandardEuropeanCorporate");
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid equity taxonomy
	@Test
	public void testValidEquityTaxonomy() throws InvalidTaxonomyException {
		UPI toTest = new UPI("Equity:PortfolioSwap:PriceReturnBasicPerformance:SingleName");
		assertEquals(toTest.assetClass, AssetClass.Equity);
		assertEquals(toTest.baseProduct, "PortfolioSwap");
		assertEquals(toTest.subProduct, "PriceReturnBasicPerformance");
		assertEquals(toTest.transactionType, "SingleName");
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid foreign exchange taxonomy (with a sub-product)
	@Test
	public void testValidForeignExchangeTaxonomyWithSubProduct() throws InvalidTaxonomyException {
		UPI toTest = new UPI("ForeignExchange:SimpleExotic:IntradayDigital");
		assertEquals(toTest.assetClass, AssetClass.ForeignExchange);
		assertEquals(toTest.baseProduct, "SimpleExotic");
		assertEquals(toTest.subProduct, "IntradayDigital");
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid foreign exchange taxonomy (without a sub-product)
	@Test
	public void testValidForeignExchangeTaxonomyWithoutSubProduct() 
			throws InvalidTaxonomyException {
		UPI toTest = new UPI("ForeignExchange:NDF");
		assertEquals(toTest.assetClass, AssetClass.ForeignExchange);
		assertEquals(toTest.baseProduct, "NDF");
		assertEquals(toTest.subProduct, null);
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid interest rate taxonomy (with a sub-product)
	@Test
	public void testValidRatesTaxonomyWithSubProduct() throws InvalidTaxonomyException {
		UPI toTest = new UPI("InterestRate:CrossCurrency:Basis");
		assertEquals(toTest.assetClass, AssetClass.Rates);
		assertEquals(toTest.baseProduct, "CrossCurrency");
		assertEquals(toTest.subProduct, "Basis");
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid interest rate taxonomy (without a sub-product)
	@Test
	public void testValidRatesTaxonomyWithoutSubProduct() throws InvalidTaxonomyException {
		UPI toTest = new UPI("InterestRate:CapFloor");
		assertEquals(toTest.assetClass, AssetClass.Rates);
		assertEquals(toTest.baseProduct, "CapFloor");
		assertEquals(toTest.subProduct, null);
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a taxonomy with an invalid asset class - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidAssetClassTaxonomy() throws InvalidTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Fail:Metals:Precious:SpotFwd:Physical");
	}
	
	// Test an empty taxonomy - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testEmptyTaxonomy() throws InvalidTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("");
	}
	
	// Test an invalid commodity taxonomy (not enough fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidCommodityTaxonomy() throws InvalidTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Commodity:Metals:Precious");
	}
	
}
