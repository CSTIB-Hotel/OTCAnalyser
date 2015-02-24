package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

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
	public JTextField currency;
	public JButton SearchButton;
	public SaveWindow saveWindow;
	
	private static SearchWindow instance;
	
	public static SearchWindow getInstance() {
		if (instance==null) instance = new SearchWindow();
		return instance;
	}
	
	private SearchWindow() {
			setSize(200,100);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			TaxonomySelectorDescriptor =  new CenteredJLabel("Select a taxonomy");

			this.add(TaxonomySelectorDescriptor);
			TaxonomySelectorDescriptor.setVisible(true);
			tax = TaxonomySelector.getInstance();
			this.add(tax);
			tax.setVisible(true);
			UnderLyingAssetDescriptor = new CenteredJLabel("Underlying Asset (optional)");

			this.add(UnderLyingAssetDescriptor);
			UnderLyingAssetDescriptor.setVisible(true);
			UnderLyingAsset = new JTextField();
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
			minValueDescriptor = new CenteredJLabel("Select minimum price (optional)");

			this.add(minValueDescriptor);
			minValueDescriptor.setVisible(true);
			minValue = new JSpinner();
			this.add(minValue);
			minValue.setVisible(true);
			maxValueDescriptor = new CenteredJLabel("Select maximum price (optional)");
			this.add(maxValueDescriptor);
			maxValueDescriptor.setVisible(true);
			maxValue = new JSpinner();
			this.add(maxValue);
			maxValue.setVisible(true);
			currencyDescriptor = new CenteredJLabel("Select a currency (optional)");
			this.add(currencyDescriptor);
			currencyDescriptor.setVisible(true);
			currency = new JTextField();	
			Configurator.enableAutoCompletion(myList,currency);
			this.add(currency);
			currency.setVisible(true);
			SearchButton = new JButton("Search");
			SearchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(SearchButton);
			SearchButton.setVisible(true);
			SearchButton.addActionListener(new SearchButtonListener());
			saveWindow = SaveWindow.getInstance();
			this.add(saveWindow);
			saveWindow.setVisible(true);
	}
	
}
