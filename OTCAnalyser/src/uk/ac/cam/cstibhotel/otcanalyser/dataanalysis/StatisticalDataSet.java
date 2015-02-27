package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.util.ArrayList;

public class StatisticalDataSet<E> {
	
	private final ArrayList<E> min;
	private final ArrayList<E> max;
	private final ArrayList<E> avg;
	private final ArrayList<E> stddev;

	public StatisticalDataSet() {
		min = new ArrayList<>();
		max = new ArrayList<>();
		avg = new ArrayList<>();
		stddev = new ArrayList<>();
	}

	public ArrayList<E> getMin() {
		return min;
	}

	public ArrayList<E> getMax() {
		return max;
	}

	public ArrayList<E> getAvg() {
		return avg;
	}

	public ArrayList<E> getStddev() {
		return stddev;
	}	
	
}
