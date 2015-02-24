package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class DateSelector extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public JComboBox<Integer> Year;
	public JComboBox<String> Months;
	public JComboBox<Integer> Day;
	
	public DateSelector() {
		setSize(300,50); // default size is 0,0
		setLocation(100,200); // default is 0,0 (top left corner)
		Year = new JComboBox<Integer>(TextStrings.Years);
		Months = new JComboBox<String>(TextStrings.Months);
		Months.addActionListener(this);
		Day = new JComboBox<Integer>(TextStrings.Days31);
		Year.setVisible(true);
		Months.setVisible(true);
		Day.setVisible(true);
		this.add(Year,BorderLayout.WEST);
		this.add(Months);
		this.add(Day,BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) { //when the month is changed, this method updates the box displaying the number of days
		String month = (String) Months.getSelectedItem();
		if (month=="February") {
			DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>) Day.getModel();
			model.removeAllElements();
			for (int i:TextStrings.Days28) {
				model.addElement(i);
			}
		}
		else if (month=="April"||month=="June"||month=="September"||month=="November") {
			DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>) Day.getModel();
			model.removeAllElements();
			for (int i:TextStrings.Days30) {
				model.addElement(i);
			}
		}
		else {
			DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>) Day.getModel();
			model.removeAllElements();
			for (int i:TextStrings.Days31) {
				model.addElement(i);
			}
		}
	}
	
	
}
