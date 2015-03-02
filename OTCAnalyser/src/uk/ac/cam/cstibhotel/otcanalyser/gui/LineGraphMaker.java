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
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.PriceTimePair;

public class LineGraphMaker {
	
	public static final int MAX = 0;
	public static final int MIN = 1;
	public static final int AVG = 2;
	public static final int TREND_LINE = 3;

	public static JFreeChart makeChart(String name, String currency, XYDataset dataset, boolean isMonth) {
		String timePeriod = "Day";
		if (isMonth) {
			timePeriod = "Month";
		}
		JFreeChart chart = ChartFactory.createTimeSeriesChart(name, timePeriod, "Price in " + currency, dataset);
		XYPlot plot = (XYPlot) chart.getPlot();
		
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		if (isMonth) {
      axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		} else {
			axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));
		}

    XYItemRenderer r = plot.getRenderer();
    if (r instanceof XYLineAndShapeRenderer) {
      XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
      renderer.setDrawSeriesLineAsPath(true);
      renderer.setBaseShapesVisible(true);
    }
    
		return chart;
	}
	
	public static TimeSeriesCollection makeDataset() {
		TimeSeries max = new TimeSeries("Maximum");
		TimeSeries min = new TimeSeries("Minimum");
		TimeSeries avg = new TimeSeries("Average");
		TimeSeries trendLine = new TimeSeries("Trend Line");
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(max);
		dataset.addSeries(min);
		dataset.addSeries(avg);
		dataset.addSeries(trendLine);
		return dataset;
	}
	
	public static void addToSeries(List<PriceTimePair> item, TimeSeriesCollection dataset, int series, boolean isMonth) {
		for (PriceTimePair itm : item) {
			Calendar c = Calendar.getInstance();
			c.setTime(itm.getTime());
			int day = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);
			if (isMonth) {
			  dataset.getSeries(series).addOrUpdate(new Month(month + 1, year), itm.getPrice());
			} else {
				dataset.getSeries(series).addOrUpdate(new Day(day, month + 1, year), itm.getPrice());
			}
		}
	}
}
