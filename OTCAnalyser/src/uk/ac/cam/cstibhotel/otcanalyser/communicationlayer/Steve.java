package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import uk.ac.cam.cstibhotel.otcanalyser.gui.GUI;
import uk.ac.cam.cstibhotel.otcanalyser.networklayer.NetworkLayer;

public class Steve {

	public static void main(String[] args) {
		//main entry point for the application
		GUI.getInstance();
		NetworkLayer.initialUpdate();
	}

}
