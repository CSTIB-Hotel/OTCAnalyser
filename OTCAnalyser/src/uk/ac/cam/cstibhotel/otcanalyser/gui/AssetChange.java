package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;

public class AssetChange implements ActionListener {

	TaxonomySelector tax;
	
	AssetChange(TaxonomySelector tax) {
		this.tax = tax;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedAsset = (String) tax.Asset.getSelectedItem();
		String[] AssetBaseClasses={""};
		switch (selectedAsset) {
			case ("Credit"): AssetBaseClasses = TextStrings.CreditBaseProducts;
				break;
			case ("Interest"): AssetBaseClasses = TextStrings.InterestBaseProducts;
				break;
			case ("Commodity"): AssetBaseClasses = TextStrings.CommodityBaseProducts;
				break;
			case ("Foreign Exchange"): AssetBaseClasses = TextStrings.ForexBaseProducts;
				break;
			case ("Equity"): break;
				default:break;
		}
		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) tax.BaseClass.getModel();
		((DefaultComboBoxModel<String>) model).removeAllElements();
		for (String i:AssetBaseClasses) {
			model.addElement(i);
		}
	}

}
