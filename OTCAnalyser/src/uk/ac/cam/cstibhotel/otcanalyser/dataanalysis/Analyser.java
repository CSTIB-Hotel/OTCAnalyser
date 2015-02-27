package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.gui.DataViewer;
import uk.ac.cam.cstibhotel.otcanalyser.gui.GUI;
import uk.ac.cam.cstibhotel.otcanalyser.gui.StatusBar;
import uk.ac.cam.cstibhotel.otcanalyser.networklayer.NetworkLayer;

public class Analyser extends Thread {
	private boolean running;
	private Search s;
	private int numResults;
	
	public Analyser(Search s, int numResults) {
		this.s = s;
		this.numResults = numResults;
	}

	public void analyse() {
		Connection conn = Database.getDB().getConnection();

		//analysis variables
		List<AnalysisItem> maxWithCurrency = new ArrayList<>(); //overall max by currency
		List<AnalysisItem> minWithCurrency = new ArrayList<>(); //overall min by currency
		List<AnalysisItem> avgWithCurrency = new ArrayList<>(); //overall average by currency
		String mostTraded; //most traded underlying asset 1
		List<PriceTimePair> maxes; //max data points
		List<PriceTimePair> mins; //min data points
		List<PriceTimePair> avgs; //average data points

		try {
			//do basic non-currency-based analysis
			String mostLeastTraded[] = DBAnalysis.getMostAndLeastTradedUnderlyingAsset(s, conn);
			mostTraded = mostLeastTraded[0];

			//get currencies
			List<String> currencies = DBAnalysis.getCurrencies(s, conn);
			List<PerCurrencyData> perCurrencyDataList = new ArrayList<>();

			for (String curr : currencies) {
				if (curr!=null&&!curr.isEmpty()) {
					s.setCurrency(curr);
					//basic analysis by currency
					maxWithCurrency.add(DBAnalysis.getMaxPrice(s, conn));
					minWithCurrency.add(DBAnalysis.getMinPrice(s, conn));
					avgWithCurrency.add(new AnalysisItem(null, curr, DBAnalysis.getAvgPrice(s, conn), null));
					//should graph by month?
					boolean byMonth = DBAnalysis.graphByMonth(s, conn, DBAnalysis.EXECUTION_TIME);

					//make data points
					StatisticalDataSet<PriceTimePair> data;
					if (byMonth) {
						data = DBAnalysis.getPerMonthStats(s, conn, DBAnalysis.EXECUTION_TIME);
					} else {
						data = DBAnalysis.getPerDayStats(s, conn, DBAnalysis.EXECUTION_TIME);

					}

					maxes = data.getMax();
					mins = data.getMin();
					avgs = data.getAvg();

					//pass lists to graph:
					DataViewer.addGraphPoints(maxes, mins, avgs, curr, byMonth);

					//add to per currency data list
					perCurrencyDataList.add(new PerCurrencyData(avgs, curr, byMonth));
				}
			}

			//pass analysis to GUI
			GUI.getInstance().addAnalyses(
					mostTraded,
					numResults,
					maxWithCurrency,
					minWithCurrency,
					avgWithCurrency);

			//pass per currency data list to extended feeder
			ExtendedFeeder.update(perCurrencyDataList);
		} catch (SQLException e) {
			e.printStackTrace();
			//analysis could not be performed; do not pass anything
		}
	}
	
	@Override
	public void run() {
    	running = true;
    	StatusBar.setMessage("Plotting graph...", 0);
        analyse();
        StatusBar.setMessage("Done", 0);
    }
}
