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
	public void testValidCommodityTaxonomy() throws InvalidTaxonomyException, EmptyTaxonomyException {
		UPI toTest = new UPI("Commodity:Metals:Precious:SpotFwd:Physical");
		assertEquals(toTest.assetClass, AssetClass.COMMODITY);
		assertEquals(toTest.baseProduct, "Metals");
		assertEquals(toTest.subProduct, "Precious");
		assertEquals(toTest.transactionType, "SpotFwd");
		assertEquals(toTest.settlementType, "Physical");
	}
	
	// Test a valid credit taxonomy
	@Test
	public void testValidCreditTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("Credit:SingleName:Corporate:StandardEuropeanCorporate");
		assertEquals(toTest.assetClass, AssetClass.CREDIT);
		assertEquals(toTest.baseProduct, "SingleName");
		assertEquals(toTest.subProduct, "Corporate");
		assertEquals(toTest.transactionType, "StandardEuropeanCorporate");
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid equity taxonomy
	@Test
	public void testValidEquityTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("Equity:PortfolioSwap:PriceReturnBasicPerformance:SingleName");
		assertEquals(toTest.assetClass, AssetClass.EQUITY);
		assertEquals(toTest.baseProduct, "PortfolioSwap");
		assertEquals(toTest.subProduct, "PriceReturnBasicPerformance");
		assertEquals(toTest.transactionType, "SingleName");
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid foreign exchange taxonomy (with a sub-product)
	@Test
	public void testValidForeignExchangeTaxonomyWithSubProduct() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("ForeignExchange:SimpleExotic:IntradayDigital");
		assertEquals(toTest.assetClass, AssetClass.FOREX);
		assertEquals(toTest.baseProduct, "SimpleExotic");
		assertEquals(toTest.subProduct, "IntradayDigital");
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid foreign exchange taxonomy (without a sub-product)
	@Test
	public void testValidForeignExchangeTaxonomyWithoutSubProduct() 
			throws InvalidTaxonomyException, EmptyTaxonomyException {
		UPI toTest = new UPI("ForeignExchange:NDF");
		assertEquals(toTest.assetClass, AssetClass.FOREX);
		assertEquals(toTest.baseProduct, "NDF");
		assertEquals(toTest.subProduct, null);
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid interest rate taxonomy (with a sub-product)
	@Test
	public void testValidRatesTaxonomyWithSubProduct() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("InterestRate:CrossCurrency:Basis");
		assertEquals(toTest.assetClass, AssetClass.RATES);
		assertEquals(toTest.baseProduct, "CrossCurrency");
		assertEquals(toTest.subProduct, "Basis");
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a valid interest rate taxonomy (without a sub-product)
	@Test
	public void testValidRatesTaxonomyWithoutSubProduct() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("InterestRate:CapFloor");
		assertEquals(toTest.assetClass, AssetClass.RATES);
		assertEquals(toTest.baseProduct, "CapFloor");
		assertEquals(toTest.subProduct, null);
		assertEquals(toTest.transactionType, null);
		assertEquals(toTest.settlementType, null);
	}
	
	// Test a taxonomy with an invalid asset class - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidAssetClassTaxonomy() throws InvalidTaxonomyException, 
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Fail:Metals:Precious:SpotFwd:Physical");
	}
	
	// Test an empty taxonomy - expect an exception
	@Test(expected = EmptyTaxonomyException.class)
	public void testEmptyTaxonomy() throws EmptyTaxonomyException, 
			InvalidTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("");
	}
	
	// Test an invalid commodity taxonomy (not enough fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidShortCommodityTaxonomy() throws InvalidTaxonomyException, 
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Commodity:Metals:Precious");
	}
	
	// Test an invalid commodity taxonomy (too many fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidLongCommodityTaxonomy() throws InvalidTaxonomyException, 
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Commodity:Metals:Precious:SpotFwd:Physical:Fail");
	}
	
	// Test an invalid credit taxonomy (not enough fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidShortCreditTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Credit:SingleName:Corporate");
	}
	
	// Test an invalid credit taxonomy (too many fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidLongCreditTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Credit:SingleName:Corporate:StandardEuropeanCorporate:Fail");
	}
	
	// Test an invalid equity taxonomy (not enough fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidShortEquityTaxonomy() throws InvalidTaxonomyException, 
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Equity:PortfolioSwap:PriceReturnBasicPerformance");
	}
	
	// Test an invalid equity taxonomy (too many fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidLongEquityTaxonomy() throws InvalidTaxonomyException, 
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Equity:PortfolioSwap:PriceReturnBasicPerformance:SingleName:Fail");
	}
	
	// Test an invalid foreign exchange taxonomy (not enough fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidShortForeignExchangeTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("ForeignExchange");
	}
	
	// Test an invalid foreign exchange taxonomy (too many fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidLongForeignExchangeTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("ForeignExchange:SimpleExotic:IntradayDigital:Fail");
	}
	
	// Test an invalid rates taxonomy (not enough fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidShortRatesTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("InterestRate");
	}
	
	// Test an invalid rates taxonomy (too many fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidLongRatesTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("InterestRate:CrossCurrency:Basis:Fail");
	}
	
}
