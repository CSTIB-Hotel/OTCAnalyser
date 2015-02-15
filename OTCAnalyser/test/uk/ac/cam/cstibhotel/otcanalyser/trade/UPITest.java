package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/*
 * This is a unit test for the UPI class, which takes a full taxonomy and breaks it down into
 * component parts.
 */
public class UPITest {
	
	@Test
	public void testNewUPI() throws InvalidTaxonomyException,
		EmptyTaxonomyException {
		UPI toTest1 = new UPI("Commodity:Metals");
		UPI toTest2 = new UPI("ForeignExchange:Metals");
		UPI toTest3 = new UPI("Credit:Metals");
		UPI toTest4 = new UPI("InterestRate:Metals");
		UPI toTest5 = new UPI("Equity:Metals");
		assertEquals(toTest1.getAssetClass(), AssetClass.COMMODITY);
		assertEquals(toTest2.getAssetClass(), AssetClass.FOREX);
		assertEquals(toTest3.getAssetClass(), AssetClass.CREDIT);
		assertEquals(toTest4.getAssetClass(), AssetClass.RATES);
		assertEquals(toTest5.getAssetClass(), AssetClass.EQUITY);
		
	}
	
	// Test a valid commodity taxonomy (full 5 terms)
	@Test
	public void testValidCommodityTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("Commodity:Metals:Precious:SpotFwd:Physical");
		assertEquals(toTest.getAssetClass(), AssetClass.COMMODITY);
		assertEquals(toTest.getBaseProduct(), "Metals");
		assertEquals(toTest.getSubProduct(), "Precious");
		assertEquals(toTest.getTransactionType(), "SpotFwd");
		assertEquals(toTest.getSettlementType(), "Physical");
	}
	
	// Test a valid commodity taxonomy (only 4 terms)
	@Test
	public void testValidShortCommodityTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("Commodity:Metals:Precious:SpotFwd");
		assertEquals(toTest.getAssetClass(), AssetClass.COMMODITY);
		assertEquals(toTest.getBaseProduct(), "Metals");
		assertEquals(toTest.getSubProduct(), "Precious");
		assertEquals(toTest.getTransactionType(), "SpotFwd");
		assertEquals(toTest.getSettlementType(), null);
	}
	
	// Test a valid credit taxonomy
	@Test
	public void testValidCreditTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("Credit:SingleName:Corporate:StandardEuropeanCorporate");
		assertEquals(toTest.getAssetClass(), AssetClass.CREDIT);
		assertEquals(toTest.getBaseProduct(), "SingleName");
		assertEquals(toTest.getSubProduct(), "Corporate");
		assertEquals(toTest.getTransactionType(), "StandardEuropeanCorporate");
		assertEquals(toTest.getSettlementType(), null);
	}
	
	// Test a valid equity taxonomy
	@Test
	public void testValidEquityTaxonomy() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("Equity:PortfolioSwap:PriceReturnBasicPerformance:SingleName");
		assertEquals(toTest.getAssetClass(), AssetClass.EQUITY);
		assertEquals(toTest.getBaseProduct(), "PortfolioSwap");
		assertEquals(toTest.getSubProduct(), "PriceReturnBasicPerformance");
		assertEquals(toTest.getTransactionType(), "SingleName");
		assertEquals(toTest.getSettlementType(), null);
	}
	
	// Test a valid foreign exchange taxonomy (with a sub-product)
	@Test
	public void testValidForeignExchangeTaxonomyWithSubProduct() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("ForeignExchange:SimpleExotic:IntradayDigital");
		assertEquals(toTest.getAssetClass(), AssetClass.FOREX);
		assertEquals(toTest.getBaseProduct(), "SimpleExotic");
		assertEquals(toTest.getSubProduct(), "IntradayDigital");
		assertEquals(toTest.getTransactionType(), null);
		assertEquals(toTest.getSettlementType(), null);
	}
	
	// Test a valid foreign exchange taxonomy (without a sub-product)
	@Test
	public void testValidForeignExchangeTaxonomyWithoutSubProduct() 
			throws InvalidTaxonomyException, EmptyTaxonomyException {
		UPI toTest = new UPI("ForeignExchange:NDF");
		assertEquals(toTest.getAssetClass(), AssetClass.FOREX);
		assertEquals(toTest.getBaseProduct(), "NDF");
		assertEquals(toTest.getSubProduct(), null);
		assertEquals(toTest.getTransactionType(), null);
		assertEquals(toTest.getSettlementType(), null);
	}
	
	// Test a valid interest rate taxonomy (with a sub-product)
	@Test
	public void testValidRatesTaxonomyWithSubProduct() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("InterestRate:CrossCurrency:Basis");
		assertEquals(toTest.getAssetClass(), AssetClass.RATES);
		assertEquals(toTest.getBaseProduct(), "CrossCurrency");
		assertEquals(toTest.getSubProduct(), "Basis");
		assertEquals(toTest.getTransactionType(), null);
		assertEquals(toTest.getSettlementType(), null);
	}
	
	// Test a valid interest rate taxonomy (without a sub-product)
	@Test
	public void testValidRatesTaxonomyWithoutSubProduct() throws InvalidTaxonomyException,
			EmptyTaxonomyException {
		UPI toTest = new UPI("InterestRate:CapFloor");
		assertEquals(toTest.getAssetClass(), AssetClass.RATES);
		assertEquals(toTest.getBaseProduct(), "CapFloor");
		assertEquals(toTest.getSubProduct(), null);
		assertEquals(toTest.getTransactionType(), null);
		assertEquals(toTest.getSettlementType(), null);
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
	
	// Test an invalid commodity taxonomy (too many fields) - expect an exception
	@Test(expected = InvalidTaxonomyException.class)
	public void testInvalidLongCommodityTaxonomy() throws InvalidTaxonomyException, 
			EmptyTaxonomyException {
		@SuppressWarnings("unused")
		UPI toTest = new UPI("Commodity:Metals:Precious:SpotFwd:Physical:Fail");
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
