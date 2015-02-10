package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public StatusBar statusBar;
	
	public static GUI gui;
	static SearchWindow searchWindow;
	
	public static GUI getInstance() {
		if (gui==null) {
			gui = new GUI();
		}
		return gui;
	}
	
	public GUI() {
		setTitle("OTC Analyser");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		searchWindow = SearchWindow.getInstance();
		add(searchWindow,BorderLayout.WEST);
		searchWindow.setVisible(true);
		statusBar = StatusBar.getInstance();
		add(statusBar,BorderLayout.SOUTH);
		statusBar.setVisible(true);
		this.setVisible(true);
	}
	
	public static void main (String[] args) {
		getInstance();
	}

}
