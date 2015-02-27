package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToolBar;

public abstract class CurrencyToolbarWindow extends CBLPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	protected JToolBar toolbar;
  protected boolean showToolbar = false;
  protected List<String> currencies = new ArrayList<>();
  
public synchronized void makeToolBar() {
  	if (showToolbar) {
  		remove(toolbar);
  	}
  	toolbar = new JToolBar();
    toolbar.setOpaque(true);
    toolbar.setRollover(true);
    toolbar.setFloatable(false);

    JButton button;
    for (String curr : currencies) {
    	button = new JButton(curr);
    	if (curr.isEmpty()) {
    		button = new JButton("Unknown Currency");
    	}
    	toolbar.add(button);
    	button.setActionCommand(curr);
    	button.addActionListener(this);
    }
    this.add(toolbar, BorderLayout.SOUTH);
    showToolbar = true;
  }
	
}
