package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;

//When the selected asset is changed, this class updates the base class selector

public class AssetChange implements ActionListener {

	TaxonomySelector tax;
	
	AssetChange(TaxonomySelector tax) {
		this.tax = tax;
	}

	public void updateAssetClass() {
		tax.settlementType.setVisible(false);
		String selectedAsset = (String) tax.Asset.getSelectedItem();
		String[] AssetBaseClasses={""};
		switch (selectedAsset) {
			case ("Credit"): 
				AssetBaseClasses = TextStrings.CreditBaseProducts;
				break;
			case ("Interest"):
				AssetBaseClasses = TextStrings.InterestBaseProducts;
				break;
			case ("Commodity"):
				AssetBaseClasses = TextStrings.CommodityBaseProducts;
				tax.settlementType.setVisible(true);
				break;
			case ("Foreign Exchange"):
			AssetBaseClasses = TextStrings.ForexBaseProducts;
				break;
			case ("Equity"):
				AssetBaseClasses = TextStrings.EquityBaseProducts;
				break;
			default:break;
		}
		TaxonomySelector.baseClassflag = false;
		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) tax.BaseClass.getModel();
		((DefaultComboBoxModel<String>) model).removeAllElements();
		for (String i:AssetBaseClasses) {
			model.addElement(i);
		}
		TaxonomySelector.baseClassflag = true;
		tax.baseProductChange.updateBaseClass();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateAssetClass();
	}

}
