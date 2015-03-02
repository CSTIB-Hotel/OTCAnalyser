package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction;

import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.PerCurrencyData;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.PriceTimePair;

public class TrendPredictor {

	private List<PriceTimePair> points;
	
	private float productMomentCorrelationCoefficient;
	private float meanPrice;
	private float meanTime;
	private float standardDeviationPrice;
	private float standardDeviationTime;
	private float leastMeanSquaresA;
	private float leastMeanSquaresB;
	
	public TrendPredictor(PerCurrencyData d) {
		points = d.data;
		
		calculateStatistics();	
	}
	
	/*
	 * Calculates the appropriate trend statistics.
	 * 
	 * Product Moment Correlation Coefficient:
	 * This provides a measure of the correlation between two data sets:
	 *         - A value of 1 implies perfect positive correlation
	 *         - A value of 0 implies no correlation
	 *         - A value of -1 implies perfect negative correlation
	 * We use this to identify general trends between price and time - is the price generally rising
	 * or falling with time?
	 * 
	 * Least mean squares regression:
	 * This is a linear prediction of the trendline for the data set. It provides two coefficients,
	 * a and b, which can be used to construct the trendline y = a + bx.
	 */
	private void calculateStatistics() {
		// x is time, y is price
		float sxx, syy, sxy;
		
		float sumX = 0;
		float sumY = 0;
		float sumXSquare = 0;
		float sumYSquare = 0;
		float sumXY = 0;
		
		int n = points.size();
		
		for (int i = 0; i < n; i++) {
			float currentX = (float) points.get(i).getTime().getTime();
			float currentY = (float) points.get(i).getPrice();
			sumX += currentX;
			sumY += currentY;
			sumXSquare += (currentX * currentX);
			sumYSquare += (currentY * currentY);
			sumXY += (currentX * currentY);
		}
		
		sxx = (n * sumXSquare) - (sumX * sumX);
		syy = (n * sumYSquare) - (sumY * sumY);
		sxy = (n * sumXY) - (sumX * sumY);
		
		// Calculate means
		meanPrice = sumY / n;
		meanTime = sumX / n;
		
		// Calculate standard deviations
		standardDeviationPrice = (float) Math.sqrt(syy / n);
		standardDeviationTime = (float) Math.sqrt(sxx / n);
		
		// Calculate product moment correlation coefficient
		productMomentCorrelationCoefficient = (float) (sxy / (Math.sqrt(sxx * syy)));
		
		// Calculate regression coefficients
		if(sxx != 0.0){
			leastMeanSquaresB = sxy / sxx;
		}
		else{
			leastMeanSquaresB = (float)0.0;
		}
		leastMeanSquaresA = meanPrice - (leastMeanSquaresB * meanTime);
	}
	
	public PredictionResult createPredictionResult() {
		PredictionResult res = new PredictionResult(
					productMomentCorrelationCoefficient,
					leastMeanSquaresA,
					leastMeanSquaresB
					);
		return res;
	}

}
