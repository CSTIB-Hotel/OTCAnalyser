package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class SaveWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel WindowLabel;
	private JLabel TextboxLabel;
	public JTextField Name;
	public JButton SaveButton;
	private SaveListener listener;
	
	private static SaveWindow instance;
	
	public static SaveWindow getInstance() {
		if (instance==null) instance = new SaveWindow();
		return instance;
	}
	
	private SaveWindow() {
			setSize(300,80);
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			WindowLabel =  new CenteredJLabel("Save a trade");
			this.add(WindowLabel,BorderLayout.NORTH);
			WindowLabel.setVisible(true);
			
			TextboxLabel = new CenteredJLabel("Name");
			this.add(TextboxLabel,BorderLayout.WEST);
			TextboxLabel.setVisible(true);
			
			Name = new JTextField();
			this.add(Name);
			Name.setVisible(true);
			SaveButton = new JButton("Save");
			SaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(SaveButton,BorderLayout.SOUTH);
			SaveButton.setVisible(true);
			this.setVisible(true);
			listener = new SaveListener();
			SaveButton.addActionListener(listener);
	}
	
}