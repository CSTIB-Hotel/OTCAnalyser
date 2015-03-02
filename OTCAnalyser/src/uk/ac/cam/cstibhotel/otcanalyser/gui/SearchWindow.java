package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.*;

public class SearchWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JList<String> myList = new JList<String>(TextStrings.Currencies);
	private JLabel TaxonomySelectorDescriptor;
	public TaxonomySelector tax;
	private JLabel UnderLyingAssetDescriptor;
	public JTextField UnderLyingAsset;
	private JLabel StartDateDescriptor;
	public DateSelector StartDate;
	private JLabel EndDateDescriptor;
	public DateSelector EndDate;
	private JLabel TradeTypeDescriptor;
	public JComboBox<String> TradeType;
	private JLabel minValueDescriptor;
	public JSpinner minValue;
	private JLabel maxValueDescriptor;
	public JSpinner maxValue;
	private JLabel currencyDescriptor;
	public JComboBox<String> currency;
	public JButton SearchButton;
	public SaveWindow saveWindow;
	
	private static SearchWindow instance;
	
	public static SearchWindow getInstance() {
		if (instance==null) instance = new SearchWindow();
		return instance;
	}
	
	private SearchWindow() {
			setLayout(new BorderLayout());
			
			JPanel top = new JPanel();
			JPanel bottom = new JPanel();
			
			top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
			top.setBorder(new EmptyBorder(5,5,5,5));
			bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
			bottom.setBorder(new EmptyBorder(5,5,10,5));
			
			TaxonomySelectorDescriptor =  new CenteredJLabel("Select a taxonomy");
			top.add(TaxonomySelectorDescriptor);
			TaxonomySelectorDescriptor.setVisible(true);
			tax = TaxonomySelector.getInstance();
			top.add(tax);
			tax.setVisible(true);
			UnderLyingAssetDescriptor = new CenteredJLabel("Underlying Asset (optional)");

			top.add(UnderLyingAssetDescriptor);
			UnderLyingAssetDescriptor.setVisible(true);
			UnderLyingAsset = new JTextField();
			top.add(UnderLyingAsset);
			UnderLyingAsset.setVisible(true);
			TradeTypeDescriptor = new CenteredJLabel("Select trade type");

			top.add(TradeTypeDescriptor);
			TradeTypeDescriptor.setVisible(true);
			TradeType = new JComboBox<String>(TextStrings.TradeType);
			top.add(TradeType);
			TradeType.setVisible(true);
			StartDateDescriptor = new CenteredJLabel("Select start date");

			top.add(StartDateDescriptor);
			StartDateDescriptor.setVisible(true);
			StartDate = new DateSelector();
			top.add(StartDate);
			StartDate.Year.setSelectedIndex(2);
			StartDate.setVisible(true);
			EndDateDescriptor = new CenteredJLabel("Select end date");

			top.add(EndDateDescriptor);
			EndDateDescriptor.setVisible(true);
			EndDate = new DateSelector();
			top.add(EndDate);
			EndDate.setVisible(true);
			
			minValueDescriptor = new CenteredJLabel("Select minimum price (optional)");
			top.add(minValueDescriptor);
			minValueDescriptor.setToolTipText("Note: if the min and max are equal," +  "\n" +
			" then their values will be ignored");
			minValueDescriptor.setVisible(true);
	
			
			/*
			 * jSpinner bug fix
			 */
			Long val = 0L;
			Long min = 0L;
			Long max = Long.MAX_VALUE;
			Long step = 1L;
			//Negative values not allowed
			SpinnerNumberModel minSpinner = new SpinnerNumberModel(val, min, max ,step);
			minValue = new JSpinner(minSpinner);
			top.add(minValue);
			minValue.setToolTipText("This is bad");
			minValue.setVisible(true);
			
			maxValueDescriptor = new CenteredJLabel("Select maximum price (optional)");
			top.add(maxValueDescriptor);
			maxValueDescriptor.setVisible(true);
			
			//Negative values not allowed
			SpinnerNumberModel maxSpinner = new SpinnerNumberModel(val, min, max , step);
			maxValue = new JSpinner(maxSpinner);
			top.add(maxValue);
			maxValue.setVisible(true);
			
			currencyDescriptor = new CenteredJLabel("Select a currency (optional)");
			top.add(currencyDescriptor);
			currencyDescriptor.setVisible(true);
			currency = new JComboBox<String>(TextStrings.Currencies);
			currency.setEditable(true);
			Configurator.enableAutoCompletion(currency);
			top.add(currency);
			currency.setVisible(true);
			SearchButton = new JButton("Search");
			SearchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      top.add(SearchButton);
			SearchButton.setVisible(true);
			SearchButton.addActionListener(new SearchButtonListener());
			
			saveWindow = SaveWindow.getInstance();
			bottom.add(saveWindow);
			saveWindow.setVisible(true);
			
			add(top, BorderLayout.NORTH);
			add(bottom, BorderLayout.SOUTH);
	}
	
}
