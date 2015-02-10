package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchWindow extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	public TaxonomySelector tax;
	private JLabel UnderLyingAssetDescriptor;
	public JTextField UnderLyingAsset;
	public DateSelector StartDate;
	public DateSelector EndDate;
	public JButton SearchButton;
	public JComboBox<String> TradeType;
	public JTextField minValue;
	public JTextField maxValue;
	public JTextField currency;
	
	private static SearchWindow instance;
	
	public static SearchWindow getInstance() {
		if (instance==null) instance = new SearchWindow();
		return instance;
	}
	
	private SearchWindow() {
			setTitle("Search Window");
			setSize(400,100); // default size is 0,0
			setLocation(100,200); // default is 0,0 (top left corner)
			setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			tax = TaxonomySelector.getInstance();
			this.add(tax);
			tax.setVisible(true);
			UnderLyingAssetDescriptor = new JLabel("Underlying Asset (optional)");
			this.add(UnderLyingAssetDescriptor);
			UnderLyingAssetDescriptor.setVisible(true);
			UnderLyingAsset = new JTextField();
			this.add(UnderLyingAsset);
			UnderLyingAsset.setVisible(true);
			StartDate = new DateSelector("Start Date");
			this.add(StartDate);
			StartDate.setVisible(true);
			EndDate = new DateSelector("End Date");
			this.add(EndDate);
			EndDate.setVisible(true);
			TradeType = new JComboBox<String>(TextStrings.TradeType);
			SearchButton = new JButton("Search");
			this.add(SearchButton);
			SearchButton.setVisible(true);
			minValue = new JTextField();
			maxValue = new JTextField();
			currency = new JTextField();
	}
	
}
