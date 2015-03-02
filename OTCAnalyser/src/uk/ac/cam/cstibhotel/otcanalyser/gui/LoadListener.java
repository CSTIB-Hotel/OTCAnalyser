package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.CommunicationLayer;

public class LoadListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SaveWindow.getInstance();
		CommunicationLayer.loadSearch((String) SaveWindow.Loadbox.getSelectedItem());
	}

}
