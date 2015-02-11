package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CollateralizationTest {
	
	// Tries to parse UC collateralization
	@Test
	public void testValidParseUC() throws CollateralizationFormatException {
		assertEquals(Collateralization.parseColl("UC"), Collateralization.UC);
	}
	
	// Tries to parse PC collateralization
	@Test
	public void testValidParsePC() throws CollateralizationFormatException {
		assertEquals(Collateralization.parseColl("PC"), Collateralization.PC);
	}
	
	// Tries to parse FC collateralization
	@Test
	public void testValidParseFC() throws CollateralizationFormatException {
		assertEquals(Collateralization.parseColl("FC"), Collateralization.FC);
	}
	
	// Tries to parse OC collateralization
	@Test
	public void testValidParseOC() throws CollateralizationFormatException {
		assertEquals(Collateralization.parseColl("OC"), Collateralization.OC);
	}
	
	// Tries to parse blank collateralization
	@Test
	public void testValidParseBLANK() throws CollateralizationFormatException {
		assertEquals(Collateralization.parseColl(""), Collateralization.BLANK);
	}
	
	// Tries to parse an invalid collateralization - expect an exception
	@Test(expected = CollateralizationFormatException.class)
	public void testInvalidCollateralization() throws CollateralizationFormatException {
		Collateralization.parseColl("test");
	}
	
}
