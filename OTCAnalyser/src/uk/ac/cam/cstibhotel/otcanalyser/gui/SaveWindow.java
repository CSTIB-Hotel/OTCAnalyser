package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.Component;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;

public class SaveWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private CenteredJLabel SaveLabel =  new CenteredJLabel("Save a search");
	private CenteredJLabel LoadLabel = new CenteredJLabel("Load a search");
	private CenteredJLabel SaveboxLabel= new CenteredJLabel("Name");
	public JTextField Savebox = new JTextField();
	public CenteredJLabel LoadboxLabel = new CenteredJLabel("Name");
	public JComboBox<String> Loadbox = new JComboBox<String>();
	public JButton SaveButton = new JButton("Save");
	public JButton LoadButton = new JButton("Load");
	private SaveListener saveListener = new SaveListener();
	private LoadListener loadListener = new LoadListener();
	
	private static SaveWindow instance;
	
	public static SaveWindow getInstance() {
		if (instance==null) instance = new SaveWindow();
		return instance;
	}
	
	private SaveWindow() {
			setSize(300,80);
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			this.add(LoadLabel);
			LoadLabel.setVisible(true);
			
			this.add(LoadboxLabel);
			LoadboxLabel.setVisible(true);
			
			this.add(Loadbox);
			updateLoad();
			Loadbox.setVisible(true);
			
			this.add(LoadButton);
			LoadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			LoadButton.setVisible(true);
			LoadButton.addActionListener(loadListener);
			
			this.add(SaveLabel);
			SaveLabel.setVisible(true);
			
			this.add(SaveboxLabel);
			SaveboxLabel.setVisible(true);
			
			this.add(Savebox);
			Savebox.setVisible(true);
			
			SaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(SaveButton);
			SaveButton.setVisible(true);
			SaveButton.addActionListener(saveListener);
			
			this.setVisible(true);
			
	}
	
	void updateLoad() {
		LinkedHashMap<String,Search> savedSearches = (LinkedHashMap<String,Search>) Database.getSavedSearches();
		HashSet<String> searchKeys = (HashSet<String>) savedSearches.keySet();
		Object[] strings = searchKeys.toArray();
		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) Loadbox.getModel();
		((DefaultComboBoxModel<String>) model).removeAllElements();
		for (Object i:strings) {
			model.addElement((String) i);
		}
	}
	
}