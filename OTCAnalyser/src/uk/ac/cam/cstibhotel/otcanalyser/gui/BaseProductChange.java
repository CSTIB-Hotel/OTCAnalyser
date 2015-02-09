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

	
	public void actionPerformed(ActionEvent e) {
		String selectedBaseClass = (String) tax.BaseClass.getSelectedItem();
		String[] subClass = {""};
		switch (selectedBaseClass){
			case("Single Name") : subClass = TextStrings.CreditSingleNameSubProducts;
				break;
			case("Index Tranche") : subClass = TextStrings.CreditIndexTrancheSubProducts;
				break;
			case("Credit Index") : subClass = TextStrings.CreditIndexSubProducts;
				break;
			case("Credit Swaption") : subClass = TextStrings.CreditSwaptionsSubProducts;
				break;
			case("Credit Exotic") : subClass = TextStrings.CreditExoticSubProducts;
				break;
			case("IR Swap") : subClass = TextStrings.InterestIRSwapSubProducts;
				break;
			default:
				break;
		}
		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) tax.SubClass.getModel();
		((DefaultComboBoxModel<String>) model).removeAllElements();
		for (String i:subClass) {
			model.addElement(i);
		}
	}

}
