package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

public class TaxonomySelector extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	public JComboBox<String> Asset;
	public JComboBox<String> BaseClass;
	public JComboBox<String> SubClass;
	private AssetChange assetChange;
	private static TaxonomySelector instance;
	
	public static TaxonomySelector getInstance() {
		if (instance==null) instance = new TaxonomySelector();
		return instance;
	}
	
	private TaxonomySelector() {
		setTitle("Taxonomy Selector");
		setSize(300,50); // default size is 0,0
		setLocation(100,200); // default is 0,0 (top left corner)
		assetChange = new AssetChange(this);
		Asset = new JComboBox<String>(TextStrings.Assets);
		Asset.addActionListener(assetChange);
		BaseClass = new JComboBox<String>(TextStrings.CommodityBaseProducts);
		SubClass = new JComboBox<String>(TextStrings.CommodityMetalsSubProducts);
		Asset.setVisible(true);
		BaseClass.setVisible(true);
		SubClass.setVisible(true);
		this.getContentPane().add(Asset,BorderLayout.WEST);
		this.getContentPane().add(BaseClass);
		this.getContentPane().add(SubClass,BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		JInternalFrame f = new TaxonomySelector();
		f.setVisible(true);
	}
	
	
}
