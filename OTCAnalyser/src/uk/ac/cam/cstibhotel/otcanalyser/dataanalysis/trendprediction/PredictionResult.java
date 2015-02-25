package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.trendprediction;

public class PredictionResult {
	
	public float pmcc;
	public float regA;
	public float regB;
	
	public PredictionResult(float pmcc, float regA, float regB) {
		this.pmcc = pmcc;
		this.regA = regA;
		this.regB = regB;
	}
}
