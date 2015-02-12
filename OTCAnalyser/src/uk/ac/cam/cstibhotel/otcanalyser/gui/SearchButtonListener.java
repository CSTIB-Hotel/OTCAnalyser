package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.CommunicationLayer;

public class SearchButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			CommunicationLayer.search();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
