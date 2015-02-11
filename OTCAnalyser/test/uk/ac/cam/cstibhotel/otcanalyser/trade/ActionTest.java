package uk.ac.cam.cstibhotel.otcanalyser.trade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionTest {
	
	// Tries to parse a valid NEW action
	@Test
	public void testValidNewAction() throws ActionFormatException {
		assertEquals(Action.parseAct("NEW"), Action.NEW);
	}
	
	// Tries to parse a valid CANCEL action
	@Test
	public void testValidCancelAction() throws ActionFormatException {
		assertEquals(Action.parseAct("CANCEL"), Action.CANCEL);
	}
	
	// Tries to parse a valid CORRECT action
	@Test
	public void testValidCorrectAction() throws ActionFormatException {
		assertEquals(Action.parseAct("CORRECT"), Action.CORRECT);
	}
	
	// Tries to parse an invalid action string - expect an exception
	@Test(expected = ActionFormatException.class)
	public void testInvalidParseAct() throws ActionFormatException {
		Action.parseAct("test");
	}

}
