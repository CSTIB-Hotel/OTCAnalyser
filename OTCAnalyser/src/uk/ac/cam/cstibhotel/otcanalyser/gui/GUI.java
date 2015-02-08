package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	SearchWindow searchWindow;
	StatusBar statusBar;
	
	private static GUI gui;
	
	public static GUI getInstance() {
		if (gui==null) {
			gui = new GUI();
		}
		return gui;
	}
	
	private GUI() {
		setTitle("OTC Analyser");
		setSize(800,600);
		searchWindow = SearchWindow.getInstance();
		add(searchWindow,BorderLayout.WEST);
		searchWindow.setVisible(true);
		statusBar = StatusBar.getInstance();
		add(statusBar,BorderLayout.SOUTH);
		statusBar.setVisible(true);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		getInstance();
		StatusBar.setMessage("the database has exploded", 1);
	}
}
