package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class StatusBar extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private static int logLevel;
	private static JLabel textArea;
	private static StatusBar instance; 
	
	private StatusBar() {
		this.setTitle("Status");
		textArea = new JLabel();
		textArea.setVisible(true);
		this.add(textArea);
		logLevel = 0;
		this.setVisible(true);
	}

	public static StatusBar getInstance() {
		if (instance==null) instance = new StatusBar();
		return instance;
	}
	
	public synchronized static void setMessage(String message,int log) {
		if (log>=logLevel) {
			logLevel = log;
			textArea.setText(message);
		}
	}
	
	public synchronized static void reset() {
		logLevel = 0;
		textArea.setText("");
	}
	
}
