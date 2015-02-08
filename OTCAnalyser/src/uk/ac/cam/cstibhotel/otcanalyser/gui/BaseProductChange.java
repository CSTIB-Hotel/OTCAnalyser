package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseProductChange implements ActionListener {

TaxonomySelector tax;
	
	BaseProductChange() {
		this.tax = TaxonomySelector.getInstance();
	}

	
	public void actionPerformed(ActionEvent e) {
		String selectedBaseClass = (String) tax.BaseClass.getSelectedItem();
		String[] subClass = {""};
		switch (selectedBaseClass){
			case()
		}
	}

}
