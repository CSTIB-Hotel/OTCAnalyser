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
		PreparedStatement ps = conn.prepareStatement("SELECT " + select + " FROM data WHERE "
				+"tradeType = ? AND "
				+"assetClass = ? AND "
				+"underlyingAsset1 LIKE ? AND "
				+"optionStrikePrice >= ? AND "
				+"optionStrikePrice <= ? AND "
				+"settlementCurrency LIKE ? AND "
				+"executionTime >= ? AND "
				+"executionTime <= ?"
				+ group);
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
	
	//for now, this gets the max option strike price; will later change to Rounded Notional Amount 1
	public static double getMaxPrice(Search s, Connection conn) throws SQLException {
	  PreparedStatement ps = statementPreparer(s, "max(optionStrikePrice)", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
	    return rs.getDouble(1);
	  }
	  else throw new SQLException();
	}
	
	//gets the max option strike price per month grouped by Notional Currency; will later change to Rounded Notional Amount 1
	public static List<AnalysisItem> getMaxPricePerMonth(Search s, Connection conn, String date) throws SQLException {
      PreparedStatement ps = statementPreparer
          (s, "max(optionStrikePrice) AS maxOSP, MONTH(" + date + ") AS month, YEAR("
          + date + ") AS year, notionalCurrency1 AS curr", "GROUP BY month, year, curr", conn);
	  ResultSet rs = ps.executeQuery();
	  //info about what's getting printed
	  System.out.println("Month/Year: Currency: Max Option Strike Price");
	  ArrayList<AnalysisItem> list = new ArrayList<>();
	  while (rs.next()) {
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date(0));
		  c.set(Calendar.MONTH, rs.getInt("month"));
		  c.set(Calendar.YEAR, rs.getInt("year"));
		  //for now,  print it:
		  System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
		      + rs.getString("curr") + ": " + rs.getDouble("maxOSP"));
		  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getDouble("maxOSP")));
	  }
	  return list;
	}
	
	//gets the population stdev of option strike price per month grouped by Notional Currency; will later change to Rounded Notional Amount 1
		public static List<AnalysisItem> getPriceStdDevPerMonth(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "STDDEV_POP(optionStrikePrice) AS stddev, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "GROUP BY month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  System.out.println("Month/Year: Currency: Std Dev of Option Strike Price");
		  ArrayList<AnalysisItem> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.MONTH, rs.getInt("month"));
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now, print it:
			  System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("stddev"));
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getDouble("stddev")));
		  }
		  return list;
		}
}
