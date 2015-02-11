package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AssetClassTest {
	
	// Tries to parse a valid COMMODITY asset class
	@Test
	public void testValidCommodityClass() throws AssetClassFormatException {
		assertEquals(AssetClass.parseAssetC("CO"), AssetClass.COMMODITY);
	}
	
	// Tries to parse a valid CREDIT asset class
	@Test
	public void testValidCreditClass() throws AssetClassFormatException {
		assertEquals(AssetClass.parseAssetC("CR"), AssetClass.CREDIT);
	}
	
	// Tries to parse a valid EQUITY asset class
	@Test
	public void testValidEquityClass() throws AssetClassFormatException {
		assertEquals(AssetClass.parseAssetC("EQ"), AssetClass.EQUITY);
	}
	
	// Tries to parse a valid FOREX asset class
	@Test
	public void testValidForexClass() throws AssetClassFormatException {
		assertEquals(AssetClass.parseAssetC("FX"), AssetClass.FOREX);
	}
	
	// Tries to parse a valid RATES asset class
	@Test
	public void testValidRatesClass() throws AssetClassFormatException {
		assertEquals(AssetClass.parseAssetC("IR"), AssetClass.RATES);
	}
	
	// Tries to parse an empty string
	@Test
	public void testEmptyString() throws AssetClassFormatException {
		assertEquals(AssetClass.parseAssetC(""), null);
	}
	
	// Tries to parse an invalid asset class - expect an exception
	@Test(expected = AssetClassFormatException.class)
	public void testInvalidAssetClass() throws AssetClassFormatException {
		AssetClass.parseAssetC("test");
	}
	
}
