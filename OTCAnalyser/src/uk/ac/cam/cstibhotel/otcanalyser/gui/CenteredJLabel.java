package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.Component;

import javax.swing.JLabel;

class CenteredJLabel extends JLabel{
	
	private static final long serialVersionUID = 1L;

	public CenteredJLabel() {
		super();
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	public CenteredJLabel(String s) {
		super(s);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
}
