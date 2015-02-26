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
	private static PreparedStatement statementPreparer(Search s, String select, String where,
	    String grouporder, Connection conn) throws SQLException {
		String query = "SELECT " + select + " FROM data WHERE "
		    +"tradeType = ? AND "
		    +"assetClass = ? AND ";
		if (!(s.getAsset() == null || s.getAsset().equals(""))) {
			query += " (underlyingAsset1 LIKE ? OR underlyingAsset2 LIKE ?) AND ";
		}
		if(!(s.getMinPrice() == s.getMaxPrice())){
			query += " roundedNotionalAmount1 >= ? AND "
					+" roundedNotionalAmount1 <= ? AND ";
		}
		if (!(s.getCurrency() == null || s.getCurrency().equals(""))) {
			query += " (notionalCurrency1 LIKE ?) AND ";
		}
		if (!(s.getUPI() == null || s.getUPI().equals(""))){
			query += " taxonomy LIKE ? AND ";
		}
		query += " executionTime >= ? AND "
				+" executionTime <= ?";
		if (!where.isEmpty()) {
		  query += " AND " + where;
		}
		query += " " + grouporder;
		
		PreparedStatement ps = conn.prepareStatement(query);
		int i = 1;

		ps.setShort(i, s.getTradeType().getValue()); i++;
		ps.setShort(i, s.getAssetClass().getValue()); i++;

		if (!(s.getAsset() == null || s.getAsset().equals(""))) {
			ps.setString(i, "%"+s.getAsset()+"%"); i++;
			ps.setString(i, "%"+s.getAsset()+"%"); i++;
		}

		if(!(s.getMinPrice() == s.getMaxPrice())){
			ps.setFloat(i, s.getMinPrice()); i++;
			ps.setFloat(i, s.getMaxPrice()); i++;
		}
		
		if (!(s.getCurrency()==null || s.getCurrency().equals(""))) {
			ps.setString(i, s.getCurrency()); i++;
		}
		
		if (!(s.getUPI() == null || s.getUPI().equals(""))){		
			ps.setString(i, "%" + s.getUPI() + "%"); i++; 
		}
		
		ps.setTimestamp(i, new Timestamp(s.getStartTime().getTime())); i++;
		ps.setTimestamp(i, new Timestamp(s.getEndTime().getTime())); i++;
		
		return ps;
	}
	
	//gets currencies
	public static List<String> getCurrencies(Search s, Connection conn) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "DISTINCT notionalCurrency1", "", "", conn);
		ResultSet rs = ps.executeQuery();
		List<String> list = new ArrayList<>();
		while (rs.next()) {
			list.add(rs.getString("notionalCurrency1"));
		}
		return list;
	}
	
	//gets most and least traded underlying assets
	public static String[] getMostAndLeastTradedUnderlyingAsset(Search s, Connection conn) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "underlyingAsset1, count(underlyingAsset1) AS cntTrade", "", "GROUP BY underlyingAsset1", conn);
		ResultSet rs = ps.executeQuery();
		
		long maxCount = 0;
		String mostTraded = "";
		long minCount =  Integer.MAX_VALUE;
		String leastTraded = "";
		
		while (rs.next()) {
			//System.out.println(rs.getString("underlyingAsset1"));
			if (rs.getLong("cntTrade") > maxCount) {
				maxCount = rs.getLong("cntTrade");
				mostTraded = rs.getString("underlyingAsset1");
			}
			if (rs.getLong("cntTrade") < minCount) {
				minCount = rs.getLong("cntTrade");
				leastTraded = rs.getString("underlyingAsset1");
			}
		}
		
		return new String[] {mostTraded, leastTraded};
	}
	
	@Deprecated
	public static String[] getMostAndLeastTradedUnderlyingAssetButAlsoHangForAtLeastOneMinute(Search s, Connection conn) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "DISTINCT underlyingAsset1", "", "", conn);
		ResultSet rs = ps.executeQuery();
		long maxCount = 0;
		String mostTraded = "";
		long minCount =  Integer.MAX_VALUE;
		String leastTraded = "";
		while (rs.next()) { //cycle through each underlying asset to find the maximum
			ps = statementPreparer(s, "count(underlyingAsset1) AS num", "underlyingAsset1 = ?", "", conn);
			//set the last parameter, which is the underlyingAsset1 to match
			ps.setString(ps.getParameterMetaData().getParameterCount(), rs.getString("underlyingAsset1"));
			ResultSet gs = ps.executeQuery();
			if (gs.next()) {
				if (gs.getLong("num") > maxCount) { //number of trades with this underlying asset > maximum so far
					maxCount = gs.getLong("num");
					mostTraded = rs.getString("underlyingAsset1");
				}
				if (gs.getLong("num") < minCount) {
					minCount = gs.getLong("num");
					leastTraded = rs.getString("underlyingAsset1");
				}
			}
		}
		return new String[] {mostTraded, leastTraded};
	}
	
	//returns true if difference between months is large enough and false otherwise
	public static boolean graphByMonth(Search s, Connection conn, String date) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "max(" + date + ") as lastDate, min(" + date + ") as firstDate", "", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
	  	Calendar first = Calendar.getInstance();
	  	first.setTime(rs.getDate("firstDate"));
	  	Calendar last = Calendar.getInstance();
	  	last.setTime(rs.getDate("lastDate"));
	  	if (last.get(Calendar.YEAR) - first.get(Calendar.YEAR) > 0 && first.get(Calendar.MONTH) - last.get(Calendar.MONTH) < 9) {
	  		return true;
	  		
	  	} else if (last.get(Calendar.MONTH) - first.get(Calendar.MONTH) > 3) {
	  		return true;
	  	} else {
	  		return false;
	  	}
	  } else {
	  	throw new SQLException();
	  }
	}
	
	//gets max Rounded Notional Amount 1
	public static AnalysisItem getMaxPrice(Search s, Connection conn) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "max(roundedNotionalAmount1) AS maxRNA", "", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
		  ps = statementPreparer(s,
	  	    "roundedNotionalAmount1 as maxRNA, notionalCurrency1 AS curr, underlyingAsset1 as asset, " + EXECUTION_TIME,
	  	    "roundedNotionalAmount1 = " + rs.getLong("maxRNA"), "ORDER BY " + EXECUTION_TIME + " DESC", conn);
	  } else return null;
    rs = ps.executeQuery();
	  if (rs.next()) {
	    return new AnalysisItem(new Date(rs.getTimestamp(EXECUTION_TIME).getTime()), rs.getString("curr"), rs.getLong("maxRNA"), rs.getString("asset"));
	  }
	  else return null;
	}
	
	//gets min Rounded Notional Amount 1
	public static AnalysisItem getMinPrice(Search s, Connection conn) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "min(roundedNotionalAmount1) AS minRNA", "", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
		  ps = statementPreparer(s,
	  	    "roundedNotionalAmount1 as minRNA, notionalCurrency1 AS curr, underlyingAsset1 as asset, " + EXECUTION_TIME,
	  	    "roundedNotionalAmount1 = " + rs.getLong("minRNA"), "ORDER BY " + EXECUTION_TIME + " DESC", conn);
	  } else return null;
    rs = ps.executeQuery();
	  if (rs.next()) {
	    return new AnalysisItem(new Date(rs.getTimestamp(EXECUTION_TIME).getTime()), rs.getString("curr"), rs.getLong("minRNA"), rs.getString("asset"));
	  }
	  else return null;
	}
	
	//gets avg Rounded Notional Amount 1
	public static double getAvgPrice(Search s, Connection conn) throws SQLException {
		PreparedStatement ps = statementPreparer(s, "avg(CAST(roundedNotionalAmount1 AS DOUBLE)) AS avgRNA", "", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
	  	return rs.getDouble("avgRNA");
	  }
	  else return 0;
	}
	
	//gets population std dev of Rounded Notional Amount 1
	public static double getStdDevPrice(Search s, Connection conn) throws SQLException {
	  PreparedStatement ps = statementPreparer(s,	"STDDEV_POP(CAST(roundedNotionalAmount1 AS DOUBLE)) AS stddev", "", "", conn);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
	  	return rs.getDouble("stddev");
	  }
	  else return 0;
	}
	
	//gets max Rounded Notional Amount 1 per month
	public static List<PriceTimePair> getMaxPricePerMonth(Search s, Connection conn, String date) throws SQLException {
      PreparedStatement ps = statementPreparer
          (s, "max(roundedNotionalAmount1) AS maxRNA, MONTH(" + date + ") AS month, YEAR("
          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY month, year, curr", conn);
	  ResultSet rs = ps.executeQuery();
	  //info about what's getting printed
	  //System.out.println("Month/Year: Currency: Max Rounded Notional Amount 1");
	  ArrayList<PriceTimePair> list = new ArrayList<>();
	  while (rs.next()) {
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date(0));
		  c.set(Calendar.MONTH, rs.getInt("month") - 1);
		  c.set(Calendar.YEAR, rs.getInt("year"));
		  //for now,  print it:
		  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
		      + rs.getString("curr") + ": " + rs.getDouble("maxRNA"));*/
		  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("maxRNA"), null));
	  }
	  return list;
	}
	
	//gets min Rounded Notional Amount 1 per month
		public static List<PriceTimePair> getMinPricePerMonth(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "min(roundedNotionalAmount1) AS minRNA, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  //System.out.println("Month/Year: Currency: Min Rounded Notional Amount 1");
		  ArrayList<PriceTimePair> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.MONTH, rs.getInt("month") - 1);
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now,  print it:
			  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("minRNA"));*/
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("minRNA"), null));
		  }
		  return list;
		}
	
	//gets avg Rounded Notional Amount 1 per month
		public static List<PriceTimePair> getAvgPricePerMonth(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "avg(CAST(roundedNotionalAmount1 AS DOUBLE)) AS avgRNA, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  //System.out.println("Month/Year: Currency: Avg Rounded Notional Amount 1");
		  ArrayList<PriceTimePair> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.MONTH, rs.getInt("month") - 1);
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now,  print it:
			  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("avgRNA"));*/
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getDouble("avgRNA"), null));
		  }
		  return list;
		}
	
	//gets the population stdev of Rounded Notional Amount1 per month grouped by Notional Currency
		public static List<PriceTimePair> getPriceStdDevPerMonth(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "STDDEV_POP(CAST(roundedNotionalAmount1 AS DOUBLE)) AS stddev, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  //System.out.println("Month/Year: Currency: Std Dev of Rounded Notional Amount 1");
		  ArrayList<PriceTimePair> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.MONTH, rs.getInt("month") - 1);
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now, print it:
			  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("stddev"));*/
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("stddev"), null));
		  }
		  return list;
		}
	
		//gets max Rounded Notional Amount 1 per day
		public static List<PriceTimePair> getMaxPricePerDay(Search s, Connection conn, String date) throws SQLException {
	      PreparedStatement ps = statementPreparer
	          (s, "max(roundedNotionalAmount1) AS maxRNA, DAY(" + date + ") as day, MONTH(" + date + ") AS month, YEAR("
	          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY day, month, year, curr", conn);
		  ResultSet rs = ps.executeQuery();
		  //info about what's getting printed
		  //System.out.println("Month/Year: Currency: Max Rounded Notional Amount 1");
		  ArrayList<PriceTimePair> list = new ArrayList<>();
		  while (rs.next()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date(0));
			  c.set(Calendar.DAY_OF_MONTH, rs.getInt("day"));
			  c.set(Calendar.MONTH, rs.getInt("month") - 1);
			  c.set(Calendar.YEAR, rs.getInt("year"));
			  //for now,  print it:
			  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
			      + rs.getString("curr") + ": " + rs.getDouble("maxRNA"));*/
			  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("maxRNA"), null));
		  }
		  return list;
		}
		
	//gets min Rounded Notional Amount 1 per day
			public static List<PriceTimePair> getMinPricePerDay(Search s, Connection conn, String date) throws SQLException {
		      PreparedStatement ps = statementPreparer
		          (s, "min(roundedNotionalAmount1) AS minRNA, DAY(" + date + ") as day, MONTH(" + date + ") AS month, YEAR("
		          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY day, month, year, curr", conn);
			  ResultSet rs = ps.executeQuery();
			  //info about what's getting printed
			  //System.out.println("Month/Year: Currency: Max Rounded Notional Amount 1");
			  ArrayList<PriceTimePair> list = new ArrayList<>();
			  while (rs.next()) {
				  Calendar c = Calendar.getInstance();
				  c.setTime(new Date(0));
				  c.set(Calendar.DAY_OF_MONTH, rs.getInt("day"));
				  c.set(Calendar.MONTH, rs.getInt("month") - 1);
				  c.set(Calendar.YEAR, rs.getInt("year"));
				  //for now,  print it:
				  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
				      + rs.getString("curr") + ": " + rs.getDouble("maxRNA"));*/
				  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("minRNA"), null));
			  }
			  return list;
			}
			
		//gets avg Rounded Notional Amount 1 per day
			public static List<PriceTimePair> getAvgPricePerDay(Search s, Connection conn, String date) throws SQLException {
		      PreparedStatement ps = statementPreparer
		          (s, "avg(roundedNotionalAmount1) AS avgRNA, DAY(" + date + ") as day, MONTH(" + date + ") AS month, YEAR("
		          + date + ") AS year, notionalCurrency1 AS curr", "", "GROUP BY day, month, year, curr", conn);
			  ResultSet rs = ps.executeQuery();
			  //info about what's getting printed
			  //System.out.println("Month/Year: Currency: Max Rounded Notional Amount 1");
			  ArrayList<PriceTimePair> list = new ArrayList<>();
			  while (rs.next()) {
				  Calendar c = Calendar.getInstance();
				  c.setTime(new Date(0));
				  c.set(Calendar.DAY_OF_MONTH, rs.getInt("day"));
				  c.set(Calendar.MONTH, rs.getInt("month") - 1);
				  c.set(Calendar.YEAR, rs.getInt("year"));
				  //for now,  print it:
				  /*System.out.println((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ": "
				      + rs.getString("curr") + ": " + rs.getDouble("maxRNA"));*/
				  list.add(new AnalysisItem(c.getTime(), rs.getString("curr"), rs.getLong("avgRNA"), null));
			  }
			  return list;
			}
		
}
