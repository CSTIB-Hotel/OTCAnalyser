package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PriceFormingContinuationDataTest {
	
	// Tries to parse a valid TERMINATION PFCD
	@Test
	public void testValidTermination() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD("Termination"),
				PriceFormingContinuationData.TERMINATION);
	}
	
	// Tries to parse a valid TRADE PFCD
	@Test
	public void testValidTrade() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD("Trade"),
				PriceFormingContinuationData.TRADE);
	}
	
	// Tries to parse a valid AMENDMENT PFCD
	@Test
	public void testValidAmendment() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD("Amendment"),
				PriceFormingContinuationData.AMENDMENT);
	}
	
	// Tries to parse a valid INCREASE PFCD
	@Test
	public void testValidIncrease() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD("Increase"),
				PriceFormingContinuationData.INCREASE);
	}
	
	// Tries to parse a valid NOVATION PFCD
	@Test
	public void testValidNovation() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD("Novation"),
				PriceFormingContinuationData.NOVATION);
	}
	
	// Tries to parse a valid TERMINATION PFCD
	@Test
	public void testValidPartialTermination() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD("Partialtermination"),
				PriceFormingContinuationData.PARTIALTERMINATION);
	}
	
	// Tries to parse an empty string
	@Test
	public void testEmptyString() throws PFCDFormatException {
		assertEquals(PriceFormingContinuationData.parsePFCD(""), null);
	}
	
	// Tries to parse an invalid PFCD - expect an exception
	@Test(expected = PFCDFormatException.class)
	public void testInvalidPFCD() throws PFCDFormatException {
		PriceFormingContinuationData.parsePFCD("test");
	}

}
