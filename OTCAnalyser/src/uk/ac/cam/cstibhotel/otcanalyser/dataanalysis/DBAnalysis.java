package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;

//gets analysis data by querying the database
public class DBAnalysis {
	
	public static final String END_DATE = "endDate";
	public static final String EFFECTIVE_DATE = "effectiveDate";
	public static final String EXECUTION_TIME = "executionTime";
	
	//if the database scheme changes, this should ideally be the only method to change
	private static PreparedStatement statementPreparer(Search s, String select, String group, Connection conn) throws SQLException {
		String query = "SELECT " + select + " FROM data WHERE "
		    +"tradeType = ? AND "
		    +"assetClass = ? AND ";
				if (s.getAsset().equals("")||s.getAsset()==null) {
					query += " (underlyingAsset1 LIKE ? OR underlyingAsset2 LIKE ?) AND ";
				}
				if(s.getMinPrice() == s.getMaxPrice()){
					query += " roundedNotionalAmount1 >= ? AND "
							+" roundedNotionalAmount1 <= ? AND ";
				}
				if (s.getCurrency().equals("")||s.getCurrency()==null) {
					query += " (notionalCurrency1 LIKE ? OR notionalCurrency2 LIKE ? ) AND ";
				}
				query += " executionTime >= ? AND "
						+" executionTime <= ?";
				query += group;
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setShort(1, s.getTradeType().getValue());
		ps.setShort(2, s.getAssetClass().getValue());
		ps.setString(3, "%" + s.getAsset() + "%");
		ps.setFloat(4, s.getMinPrice());
		ps.setFloat(5, s.getMaxPrice());
		ps.setString(6, "%" + s.getCurrency() + "%");
		ps.setTimestamp(7, new Timestamp(s.getStartTime().getTime()));
		ps.setTimestamp(8, new Timestamp(s.getEndTime().getTime()));
		return ps;
	}
	
	//gets max Rounded Notional Amount 1
	public static double getMaxPrice(Search s, Connection conn) throws SQLException {
	  PreparedStatement ps = statementPreparer(s, "max(roundedNotionalAmount1)", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
	    return rs.getDouble(1);
	  }
	  else throw new SQLException();
	}
	
	//gets max Rounded Notional Amount 1
	public static List<AnalysisItem> getMaxPricePerMonth(Search s, Connection conn, String date) throws SQLException {
      PreparedStatement ps = statementPreparer
          (s, "max(roundedNotionalAmount1) AS maxRNA, MONTH(" + date + ") AS month, YEAR("
          + date + ") AS year, notionalCurrency1 AS curr", "GROUP BY month, year, curr", conn);
	  ResultSet rs = ps.executeQuery();
	  //info about what's getting printed
	  System.out.println("Month/Year: Currency: Max Rounded Notional Amount 1");
	  ArrayList<AnalysisItem> list = new ArrayList<>();
	  while (rs.next()) {
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date(0));
		  c.set(Calendar.MONTH, rs.getInt("month"));
		  c.set(Calendar.YEAR, rs.getInt("year"));
		  //for now,  print it:
		  System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
		      + rs.getString("curr") + ": " + rs.getDouble("maxRNA"));
		  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("maxRNA")));
	  }
	  return list;
	}
	
	//gets avg Rounded Notional Amount 1 per month
		public static List<AnalysisItem> getAvgPricePerMonth(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "avg(CAST(roundedNotionalAmount1 AS DOUBLE)) AS avgRNA, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "GROUP BY month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  System.out.println("Month/Year: Currency: Avg Rounded Notional Amount 1");
		  ArrayList<AnalysisItem> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.MONTH, rs.getInt("month"));
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now,  print it:
			  System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("avgRNA"));
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getDouble("avgRNA")));
		  }
		  return list;
		}
	
	//gets the population stdev of Rounded Notional Amount1 per month grouped by Notional Currency
		public static List<AnalysisItem> getPriceStdDevPerMonth(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "STDDEV_POP(roundedNotionalAmount1) AS stddev, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "GROUP BY month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  System.out.println("Month/Year: Currency: Std Dev of Rounded Notional Amount 1");
		  ArrayList<AnalysisItem> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.MONTH, rs.getInt("month"));
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now, print it:
			  System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("stddev"));
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("stddev")));
		  }
		  return list;
		}
}
