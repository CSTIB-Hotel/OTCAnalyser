package uk.ac.cam.cstibhotel.otcanalyser.networklayer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

public class ParseZIPTest {

	public ParseZIPTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test(expected =  MalformedURLException.class)
	public void malformedUrlTest() throws MalformedURLException, IOException{
		ParseZIP.downloadData("wrongurlhere", "", "");
	}
	
	@Test(expected = IOException.class)
	public void wrongURLTest() throws MalformedURLException, IOException{
		ParseZIP.downloadData("http://Www.google.com", "","");
	}
	
	

}
