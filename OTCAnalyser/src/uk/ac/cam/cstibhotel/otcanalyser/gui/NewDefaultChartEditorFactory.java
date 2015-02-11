package uk.ac.cam.cstibhotel.otcanalyser.gui;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditor;
import org.jfree.chart.editor.DefaultChartEditorFactory;
import java.awt.Dimension;
import javax.swing.JPanel;

//a fix for the "growing box problem" in library's default chart editor
public class NewDefaultChartEditorFactory extends DefaultChartEditorFactory{
	public NewDefaultChartEditorFactory() {
		super();
	}
	
	@Override
	public ChartEditor createEditor (JFreeChart chart) {
		JPanel editor = ((JPanel)super.createEditor(chart));
		editor.setPreferredSize(new Dimension(500,500));
		return (ChartEditor) editor;
	}
}
