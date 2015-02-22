package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.CommunicationLayer;

public class SaveListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		try {
			CommunicationLayer.saveSearch(SaveWindow.getInstance().Name.getText());
		} catch (ParseException e1) {
			StatusBar.setMessage("There was an error parsing the search parameters", 1);
		}
	}

}
