package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HelpListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
			 
			  try {
		 
				File pdfFile = new File("Documentation.pdf");
				if (pdfFile.exists()) {
		 
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pdfFile);
					}
				}
			  }
			catch (Exception e){}
	}

}
