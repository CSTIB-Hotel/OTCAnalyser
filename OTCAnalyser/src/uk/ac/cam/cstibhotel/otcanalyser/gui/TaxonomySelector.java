package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

public class TaxonomySelector extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> Asset;
	private JComboBox<String> BaseClass;
	private JComboBox<String> SubClass;
	
	public TaxonomySelector() {
		setTitle("Taxonomy Selector");
		setSize(300,50); // default size is 0,0
		setLocation(100,200); // default is 0,0 (top left corner)
		Asset = new JComboBox<String>(TextStrings.Assets);
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
