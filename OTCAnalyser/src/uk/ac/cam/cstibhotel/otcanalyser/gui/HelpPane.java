package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

public class HelpPane extends JTextPane {
	
	private static HelpPane instance;
	
	public static HelpPane getInstance() {
		if (instance==null) {
			instance = new HelpPane();
		}
		return instance;
	}
	
	private HelpPane() {
		setBorder(new TitledBorder("Help"));
		setText(text);
		this.setVisible(true);
	}
	
	private static final long serialVersionUID = 1L;

	private final static String text = "This application allows you to search for details of OTC trades that have occured over the past 2 years"+"\n"
			+ "The most important thing to specify is the UPI taxonomy, this is necessary to find what you're looking for."+"\n"
			+ "Each other parameter is optional, but will help to narrow your search:" +  "\n"
			+ "Each taxonomy will have multiple underlying assets. For example, both Gold and Silver are Precious Metals. Specifying the underlying asset will narrow your search considerably." + "\n"
			+ "There are two trade types: options and swaps. Swaps are searched for by default." + "\n"
			+ "If the min and max trade prices are the same, the application will assume you don't want to specify these values.";	
	
}
