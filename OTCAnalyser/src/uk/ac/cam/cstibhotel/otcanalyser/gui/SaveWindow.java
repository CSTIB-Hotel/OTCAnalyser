package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel WindowLabel;
	private JLabel TextboxLabel;
	public JTextField Name;
	public JButton SaveButton;
	//private SaveButtonListener listener;
	
	private static SaveWindow instance;
	
	public static SaveWindow getInstance() {
		if (instance==null) instance = new SaveWindow();
		return instance;
	}
	
	private SaveWindow() {
			setSize(200,100); // default size is 0,0
			setLocation(100,200); // default is 0,0 (top left corner)
			
			WindowLabel =  new CenteredJLabel("Save a Trade");
			this.add(WindowLabel,BorderLayout.NORTH);
			WindowLabel.setVisible(true);
			
			TextboxLabel = new CenteredJLabel("Name");
			this.add(TextboxLabel);
			TextboxLabel.setVisible(true);
			
			Name = new JTextField();
			this.add(UnderLyingAsset);
			UnderLyingAsset.setVisible(true);
			TradeTypeDescriptor = new CenteredJLabel("Select trade type");

			this.add(TradeTypeDescriptor);
			TradeTypeDescriptor.setVisible(true);
			TradeType = new JComboBox<String>(TextStrings.TradeType);
			this.add(TradeType);
			TradeType.setVisible(true);
			StartDateDescriptor = new CenteredJLabel("Select start date");

			this.add(StartDateDescriptor);
			StartDateDescriptor.setVisible(true);
			StartDate = new DateSelector();
			this.add(StartDate);
			StartDate.setVisible(true);
			EndDateDescriptor = new CenteredJLabel("Select end date");

			this.add(EndDateDescriptor);
			EndDateDescriptor.setVisible(true);
			EndDate = new DateSelector();
			this.add(EndDate);
			EndDate.setVisible(true);
			minValueDescriptor = new CenteredJLabel("Select minimum price");

			this.add(minValueDescriptor);
			minValueDescriptor.setVisible(true);
			minValue = new JTextField();
			this.add(minValue);
			minValue.setVisible(true);
			maxValueDescriptor = new CenteredJLabel("Select maximum price");
			this.add(maxValueDescriptor);
			maxValueDescriptor.setVisible(true);
			maxValue = new JTextField();
			this.add(maxValue);
			maxValue.setVisible(true);
			currencyDescriptor = new CenteredJLabel("Select a currency");
			this.add(currencyDescriptor);
			currencyDescriptor.setVisible(true);
			currency = new JTextField();
			this.add(currency);
			currency.setVisible(true);
			SearchButton = new JButton("Search");
			SearchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(SearchButton);
			SearchButton.setVisible(true);
			listener = new SearchButtonListener();
			SearchButton.addActionListener(listener);
	}
	
}