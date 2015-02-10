package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;

public class BaseProductChange implements ActionListener {

TaxonomySelector tax;
	
	BaseProductChange(TaxonomySelector tax) {
		this.tax = tax;
	}

	
	public void actionPerformed(ActionEvent arg0) {
		String selectedBaseClass = (String) tax.BaseClass.getSelectedItem();
		String[] subClasses={""};
		switch (selectedBaseClass) {
			case ("SingleName"): subClasses = TextStrings.CreditSingleNameSubProducts;
				break;
			case ("Index Tranche"): subClasses = TextStrings.CreditIndexTrancheSubProducts;
				break;
			case ("Index"): subClasses = TextStrings.CreditIndexSubProducts;
				break;
			case ("Swaptions"): subClasses = TextStrings.CreditSwaptionsSubProducts;
				break;
			case ("Exotic"): subClasses = TextStrings.CreditExoticSubProducts;
			default:break;
		}
		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) tax.SubClass.getModel();
		((DefaultComboBoxModel<String>) model).removeAllElements();
		for (String i:subClasses) {
			model.addElement(i);
		}
	}

}