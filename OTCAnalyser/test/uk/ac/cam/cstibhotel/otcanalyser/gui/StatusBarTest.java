package uk.ac.cam.cstibhotel.otcanalyser.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatusBarTest {
	
	@Test
	public void test() {
		StatusBar.getInstance();
		StatusBar.setMessage("testing1",1);
		assertEquals(StatusBar.textArea.getText(),"testing1");
		StatusBar.setMessage("testing2",2);
		assertEquals(StatusBar.textArea.getText(),"testing2");
		StatusBar.setMessage("testing3", 1);
		assertEquals(StatusBar.textArea.getText(),"testing2");
		StatusBar.reset();
		assertEquals(StatusBar.textArea.getText(),"No errors");
	}
}
