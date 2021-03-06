package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class TaxonomySelector extends JPanel {
	static boolean baseClassflag;
	private static final long serialVersionUID = 1L;
	public JComboBox<String> Asset;
	public JComboBox<String> BaseClass;
	public JComboBox<String> SubClass;
	private AssetChange assetChange;
	BaseProductChange baseProductChange;
	private static TaxonomySelector instance;
	public JComboBox<String> settlementType;
	
	public static TaxonomySelector getInstance() {
		if (instance==null) instance = new TaxonomySelector();
		return instance;
	}
	
	private TaxonomySelector() {
		baseClassflag = true;
		setSize(300,50);
		setLocation(100,200);
		setLayout(new GridLayout(4,0));
		assetChange = new AssetChange(this);
		Asset = new JComboBox<String>(TextStrings.Assets);
		Asset.addActionListener(assetChange);
		BaseClass = new JComboBox<String>(TextStrings.CreditBaseProducts);
		baseProductChange  = new BaseProductChange(this);
		BaseClass.addActionListener(baseProductChange);
		SubClass = new JComboBox<String>(TextStrings.CreditSingleNameSubProducts);
		settlementType = new JComboBox<String> (TextStrings.SettlementTypes);
		assetChange.updateAssetClass();
		baseProductChange.updateBaseClass();
		Asset.setVisible(true);
		BaseClass.setVisible(true);
		SubClass.setVisible(true);;
		this.add(Asset);
		this.add(BaseClass);
		this.add(SubClass);
		this.add(settlementType);
	}

}
