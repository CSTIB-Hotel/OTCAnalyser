package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

public class DateSelector extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> Year;
	private JComboBox<String> Months;
	private JComboBox<Integer> Day;
	
	public DateSelector(String title) {
		setTitle(title);
		setSize(300,50); // default size is 0,0
		setLocation(100,200); // default is 0,0 (top left corner)
		Year = new JComboBox<String>(TextStrings.Years);
		Months = new JComboBox<String>(TextStrings.Months);
		Months.addActionListener(this);
		Day = new JComboBox<Integer>(TextStrings.Days31);
		Year.setVisible(true);
		Months.setVisible(true);
		Day.setVisible(true);
		this.getContentPane().add(Year,BorderLayout.WEST);
		this.getContentPane().add(Months);
		this.getContentPane().add(Day,BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String month = (String) Months.getSelectedItem();
		if (month=="February") {
			Day = new JComboBox<Integer>(TextStrings.Days28);
			System.out.println("Executing");
		}
		else if (month=="April"||month=="June"||month=="September"||month=="November") {
			
		}
		else {
			
		}
	}
	
	
}
