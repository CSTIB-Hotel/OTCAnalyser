package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;
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
	public JTextField minValue;
	private JLabel maxValueDescriptor;
	public JTextField maxValue;
	private JLabel currencyDescriptor;
	public JTextField currency;
	public JButton SearchButton;
	
	private static SearchWindow instance;
	
	public static SearchWindow getInstance() {
		if (instance==null) instance = new SearchWindow();
		return instance;
	}
	
	private SearchWindow() {
			setSize(200,100); // default size is 0,0
			setLocation(100,200); // default is 0,0 (top left corner)
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			TaxonomySelectorDescriptor =  new JLabel("Select a taxonomy");
			this.add(TaxonomySelectorDescriptor);
			TaxonomySelectorDescriptor.setVisible(true);
			tax = TaxonomySelector.getInstance();
			this.add(tax);
			tax.setVisible(true);
			UnderLyingAssetDescriptor = new JLabel("Underlying Asset (optional)");
			this.add(UnderLyingAssetDescriptor);
			UnderLyingAssetDescriptor.setVisible(true);
			UnderLyingAsset = new JTextField();
			this.add(UnderLyingAsset);
			UnderLyingAsset.setVisible(true);
			TradeTypeDescriptor = new JLabel("Select trade type");
			this.add(TradeTypeDescriptor);
			TradeTypeDescriptor.setVisible(true);
			TradeType = new JComboBox<String>(TextStrings.TradeType);
			this.add(TradeType);
			TradeType.setVisible(true);
			StartDateDescriptor = new JLabel("Select start date");
			this.add(StartDateDescriptor);
			StartDateDescriptor.setVisible(true);
			StartDate = new DateSelector();
			this.add(StartDate);
			StartDate.setVisible(true);
			EndDateDescriptor = new JLabel("Select end date");
			this.add(EndDateDescriptor);
			EndDateDescriptor.setVisible(true);
			EndDate = new DateSelector();
			this.add(EndDate);
			EndDate.setVisible(true);
			minValueDescriptor = new JLabel("Select minimum price");
			this.add(minValueDescriptor);
			minValueDescriptor.setVisible(true);
			minValue = new JTextField();
			this.add(minValue);
			minValue.setVisible(true);
			maxValueDescriptor = new JLabel("Select maximum price");
			this.add(maxValueDescriptor);
			maxValueDescriptor.setVisible(true);
			maxValue = new JTextField();
			this.add(maxValue);
			maxValue.setVisible(true);
			currencyDescriptor = new JLabel("Select a currency");
			this.add(currencyDescriptor);
			currencyDescriptor.setVisible(true);
			currency = new JTextField();
			this.add(currency);
			currency.setVisible(true);
			SearchButton = new JButton("Search");
			this.add(SearchButton);
			SearchButton.setVisible(true);
	}
	
}
