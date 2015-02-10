package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class TaxonomySelector extends JPanel {
	static boolean baseClassflag;
	private static final long serialVersionUID = 1L;
	public JComboBox<String> Asset;
	public JComboBox<String> BaseClass;
	public JComboBox<String> SubClass;
	private AssetChange assetChange;
	private BaseProductChange baseProductChange;
	private static TaxonomySelector instance;
	
	public static TaxonomySelector getInstance() {
		if (instance==null) instance = new TaxonomySelector();
		return instance;
	}
	
	private TaxonomySelector() {
		baseClassflag = true;
		setSize(300,50);
		setLocation(100,200);
		assetChange = new AssetChange(this);
		Asset = new JComboBox<String>(TextStrings.Assets);
		Asset.addActionListener(assetChange);
		BaseClass = new JComboBox<String>(TextStrings.CreditBaseProducts);
		baseProductChange  = new BaseProductChange(this);
		BaseClass.addActionListener(baseProductChange);
		SubClass = new JComboBox<String>(TextStrings.CreditSingleNameSubProducts);
		Asset.setVisible(true);
		BaseClass.setVisible(true);
		SubClass.setVisible(true);
		this.add(Asset,BorderLayout.WEST);
		this.add(BaseClass);
		this.add(SubClass,BorderLayout.EAST);
	}

}
