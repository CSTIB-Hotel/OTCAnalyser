package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class TradeDisplayer extends JPanel {

	//need to fix size
	
	private static final long serialVersionUID = 1L;
	private JLabel Label;
	private JLabel Details;
	private JLabel Details2;
	
	public void update(AnalysisItem item) {
		double amount = item.getPrice();
		String asset = item.getUnderlyingAsset();
		Date time = item.getTime();
		String currency = item.getCurrency();
		Details.setText("Traded amount: "+amount+" "+currency+" Underlying Asset: "+asset+" Execution Date: "+time.toString());
	}
	
	public void update(double Average,String MostTraded,String LeastTraded,String Currency,double NumTrades,double changeInCost){
		Details.setText("Most traded asset: "+MostTraded+" Least traded asset: "+LeastTraded+" Average Trade Cost: "+Average+" "+Currency);
		Details2.setText("Number of Trades: "+NumTrades+" Change in average cost "+changeInCost+" "+Currency);
	}
	
	TradeDisplayer(String label) {
		Label = new JLabel(label);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(200,50);
		add(Label);
		Label.setVisible(true);
		Details = new JLabel("Traded amount:              Underlying Asset:              Execution Date:             ");
		add(Details);
		Details.setVisible(true);
		setVisible(true);
	}
	
	TradeDisplayer() {
		Label = new JLabel("Other info");
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(200,50);
		add(Label);
		Label.setVisible(true);
		Details = new JLabel("Most traded asset:              Least traded asset:              Average trade cost:             ");
		Details2 = new JLabel("Number of trades:              Change in average cost:          ");
		add(Details);
		Details.setVisible(true);
		add(Details2);
		Details2.setVisible(true);
		setVisible(true);
	}
	
}
