package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import uk.ac.cam.cstibhotel.otcanalyser.networklayer.InitialUpdateWorker;
import uk.ac.cam.cstibhotel.otcanalyser.networklayer.NetworkLayer;

public class WindowCloseListener extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent arg0) {
		GUI.getInstance().dispose();
		((InitialUpdateWorker) NetworkLayer.updater).setRunning(false);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.exit(0);
	}

}
