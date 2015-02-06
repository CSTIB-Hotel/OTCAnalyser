package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	static SearchWindow searchWindow;
	public static GUI theGUI;
	
	public GUI() {
		setTitle("OTC Analyser");
		setSize(800,600);
		searchWindow = new SearchWindow();
		add(searchWindow,BorderLayout.WEST);
		searchWindow.setVisible(true);
		this.setVisible(true);
	}
}
