package uk.ac.cam.cstibhotel.otcanalyser.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.AnalysisItem;

public class LineGraphMaker {
	
	public static final int MAX = 0;
	public static final int MIN = 1;
	public static final int AVG = 2;

	public static JFreeChart makeMonthChart(String name, String currency, XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(name, "Month", "Price in " + currency, dataset);
		XYPlot plot = (XYPlot) chart.getPlot();
		
		DateAxis axis = (DateAxis) plot.getDomainAxis();
    axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

    XYItemRenderer r = plot.getRenderer();
    if (r instanceof XYLineAndShapeRenderer) {
      XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
      renderer.setDrawSeriesLineAsPath(true);
    }
    
		return chart;
	}
	
	public static XYDataset makeDataset() {
		TimeSeries max = new TimeSeries("Maximum");
		TimeSeries min = new TimeSeries("Minimum");
		TimeSeries avg = new TimeSeries("Average");
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(max);
		dataset.addSeries(min);
		dataset.addSeries(avg);
		return dataset;
	}
	
	public static void addToSeries(List<AnalysisItem> item, XYDataset dataset, int series) {
		for (AnalysisItem itm : item) {
			Calendar c = Calendar.getInstance();
			c.setTime(itm.getTime());
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);
			((TimeSeries) dataset.getSeriesKey(series)).add(new Month(month, year), itm.getPrice());
		}
	}
}
