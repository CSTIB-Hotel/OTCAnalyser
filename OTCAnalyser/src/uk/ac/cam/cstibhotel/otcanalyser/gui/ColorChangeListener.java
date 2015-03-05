package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ColorChangeListener implements ActionListener {

	JComboBox<String> color;
	
	ColorChangeListener(JComboBox<String> c) {
		color = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Color chartColor;
		Color plotColor;
		String selectedColor = (String) color.getSelectedItem();
		
		switch(selectedColor) {
		case("Default"): {
			chartColor = new Color(100,100,100);
			plotColor = Color.DARK_GRAY;
			GraphWindow.chartColor = chartColor;
			GraphWindow.plotColor = plotColor;
			break;
		}
		case("Light"): {
			chartColor = Color.WHITE;
			plotColor = Color.LIGHT_GRAY;
			GraphWindow.chartColor = chartColor;
			GraphWindow.plotColor = plotColor;
			break;
		}
		case("Hotdog"): {
			chartColor = Color.RED;
			plotColor = Color.YELLOW;
			GraphWindow.chartColor = chartColor;
			GraphWindow.plotColor = plotColor;
			break;
		}
		default: break;
		}
		
	}

}
