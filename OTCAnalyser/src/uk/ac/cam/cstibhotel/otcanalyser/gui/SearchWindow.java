package uk.ac.cam.cst1bhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private TaxonomySelector tax;
	private JLabel UnderLyingAssetDescriptor;
	private JTextField UnderLyingAsset;
	private DateSelector StartDate;
	private DateSelector EndDate;
	private JButton SearchButton;
	
	public SearchWindow() {
			setTitle("Search Window");
			setSize(400,400); // default size is 0,0
			setLocation(100,200); // default is 0,0 (top left corner)
			setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			tax = new TaxonomySelector();
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
			SearchButton = new JButton("Search");
			this.add(SearchButton);
			SearchButton.setVisible(true);
	}
		
	public static void main(String[] args) {
		JFrame f = new SearchWindow();
		f.setVisible(true);
	}
}
