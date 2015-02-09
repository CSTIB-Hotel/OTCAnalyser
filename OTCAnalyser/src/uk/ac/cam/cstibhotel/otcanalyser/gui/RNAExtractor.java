package uk.ac.cam.cstibhotel.otcanalyser.gui;

//class that extracts rounded notional amount values as doubles
public class RNAExtractor {
	
  //returns true if rna is in valid Rounded Notional Amount format
  public static boolean validRNA(String rna) {
  	try {
  		getDoubleRNA(rna);
  		return true;
  	} catch (Exception e) {
  		return false;
  	}
  }
	
  //get double form of the Rounded Notional Amount, which sometimes contains the '+' character
  public static double getDoubleRNA(String rna) {
  	int index = rna.indexOf('+');
  	if (index > -1) {
  		rna = rna.substring(0, index);
  	}
  	return Double.parseDouble(rna);
  }
}
