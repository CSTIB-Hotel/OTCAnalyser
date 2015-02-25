package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JToolBar;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction.PredictionResult;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction.TrendPredictor;

public class ExtendedFeeder {
	private static List<PerCurrencyResult> results = new LinkedList<PerCurrencyResult>();
	private static boolean showToolbar = false; 
	private static JToolBar toolbar;
	 
	public static void update(List<PerCurrencyData> input) {
		for (PerCurrencyData pcd : input) {
			TrendPredictor tp = new TrendPredictor(pcd);
			PredictionResult result =  tp.createPredictionResult();
			PerCurrencyResult pcr = new PerCurrencyResult(result, pcd.currency);
			results.add(pcr);
		}
	}
}

