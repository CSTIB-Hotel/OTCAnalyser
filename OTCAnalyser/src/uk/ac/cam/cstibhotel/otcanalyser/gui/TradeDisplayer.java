package uk.ac.cam.cstibhotel.otcanalyser.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class TradeDisplayer extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel Label;
	private JLabel Details = new JLabel("Traded amount:              Underlying Asset:              Execution Date:             ");
	private String underLyingAsset;
	private String Date;
	private long Amount;
	
	
	TradeDisplayer(String label) {
		Label = new JLabel(label);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(200,50);
		this.add(Label);
		Label.setVisible(true);
		this.add(Details);
		Details.setVisible(true);
		this.setVisible(true);
	}
}
