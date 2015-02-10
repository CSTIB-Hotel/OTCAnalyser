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

	
	public void updateBaseClass() {
		String selectedBaseClass = (String) tax.BaseClass.getSelectedItem();
		String[] subClasses={"n/a"};
		switch (selectedBaseClass) {
			case ("Single Name"): subClasses = TextStrings.CreditSingleNameSubProducts;
				break;
			case ("Index Tranche"): subClasses = TextStrings.CreditIndexTrancheSubProducts;
				break;
			case ("Index"): subClasses = TextStrings.CreditIndexSubProducts;
				break;
			case ("Swaptions"): subClasses = TextStrings.CreditSwaptionsSubProducts;
				break;
			case ("Exotic"):
				if (tax.Asset.getSelectedItem() == "Credit") subClasses = TextStrings.CreditExoticSubProducts;
				break;
			case ("IR Swap"): subClasses = TextStrings.InterestIRSwapSubProducts;
				break;
			case ("Cross Currency"): subClasses = TextStrings.InterestCrossCurrencySubProducts;
				break;
			case ("Option"):
				if (tax.Asset.getSelectedItem() == "Interest") subClasses =  TextStrings.InterestOptionSubProducts;
				else subClasses = TextStrings.EquityOptionSubProducts;
				break;
			case ("Metals"): subClasses = TextStrings.CommodityMetalsSubProducts;
				break;
			case ("Energy"): subClasses = TextStrings.CommodityEnergySubProducts;
				break;
			case ("Agriculture"): subClasses = TextStrings.CommodityAgricultureSubProducts;
				break;
			case ("Environmental"): subClasses = TextStrings.CommodityEnvironmentalSubProducts;
				break;
			case ("Simple Exotic"): subClasses = TextStrings.ForexSimpleExoticSubProducts;
				break;
			case ("Swap"): subClasses = TextStrings.EquitySwapSubProducts;
				break;
			case ("Portfolio swap"): subClasses = TextStrings.EquityPortfolioSwapSubProducts;
				break;
			case ("Contract for difference"): subClasses = TextStrings.EquityContractForDifferenceSubProducts;
				break;
			case ("Forward"): subClasses = TextStrings.EquityForwardSubProducts;
				break;
			default:break;
		}
		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) tax.SubClass.getModel();
		((DefaultComboBoxModel<String>) model).removeAllElements();
		for (String i:subClasses) {
			model.addElement(i);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (TaxonomySelector.baseClassflag) updateBaseClass();
	}

}