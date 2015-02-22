package uk.ac.cam.cstibhotel.otcanalyser.dataanalysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.dataanalysis.DBAnalysis;
import uk.ac.cam.cstibhotel.otcanalyser.database.Database;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DBAnalysisTest {
	
	private Database db;
	private Connection connection;
  
  //fill database with fake trades
  public void fakeDB() {
  	if (db == null) {
	    db = Database.getDB();
	    connection = db.getConnection();
	    ArrayList<Trade> trades = new ArrayList<>();
	    
	    Calendar c = Calendar.getInstance();
	    c.set(2323, 1, 1, 11, 13);
	    for (int i = 0; i < 10; i ++) {
	  	  Trade t = new Trade();
	  	  t.setDisseminationID(i);
	  	  t.setNotionalCurrency1("USD");
	  	  t.setSettlementCurrency("USD");
	  	  t.setRoundedNotionalAmount1(i);
	  	  c.set(Calendar.DATE, i + 1);
	  	  t.setEffectiveDate(c.getTime());
	  	  t.setEndDate(c.getTime());
	  	  t.setExecutionTimestamp(c.getTime());
	  	  t.setTradeType(TradeType.OPTION);
	  	  t.setAssetClass(AssetClass.COMMODITY);
	  	  trades.add(t);
	    }
	    for (int i = 10; i < 20; i ++) {
	  	  Trade t = new Trade();
	  	  t.setDisseminationID(i);
	  	  t.setNotionalCurrency1("GBP");
	  	  t.setSettlementCurrency("GBP");
	  	  t.setRoundedNotionalAmount1(i);
	  	  c.set(Calendar.DATE, i - 2);
	  	  t.setEffectiveDate(c.getTime());
	  	  t.setEndDate(c.getTime());
	  	  t.setExecutionTimestamp(c.getTime());
	  	  t.setTradeType(TradeType.OPTION);
	  	  t.setAssetClass(AssetClass.COMMODITY);
	  	  trades.add(t);
	    }
	    db.addTrade(trades);
  	}
  }
  
  @Test
  public void testMaxRNA1PerMonth() throws SQLException{
  	fakeDB();
  	Search s = new Search();
  	s.setTradeType(TradeType.OPTION);
  	s.setAssetClass(AssetClass.COMMODITY);
  	s.setMinPrice(0);
  	s.setMaxPrice(1000000000);
  	Calendar c = Calendar.getInstance();
	  c.set(2100, 1, 1, 1, 13);
  	s.setStartTime(c.getTime());
  	c.set(2400, 1, 1, 1, 1);
  	s.setEndTime(c.getTime());
  	s.setAsset("");
  	s.setCurrency("");
  	List<AnalysisItem> list;
  	try {
  		SearchResult sr = Database.getDB().search(s);
  		if (sr.getNumResults() > 0) {
  			list = DBAnalysis.getMaxPricePerMonth(s, connection, DBAnalysis.EXECUTION_TIME);
    	  assertEquals((list.get(0).getPrice()), 9, 0);
    	  assertEquals((list.get(1).getPrice()), 19, 0);
  		} else {
  			System.out.println("Empty");
  		}
  	} catch (SQLException e){
  		System.err.println("problem with getMaxPricePerMonth");
  	}
  }
  @Test
  public void testAvgRNA1PerMonth() throws SQLException{
  	fakeDB();
  	Search s = new Search();
  	s.setTradeType(TradeType.OPTION);
  	s.setAssetClass(AssetClass.COMMODITY);
  	s.setMinPrice(0);
  	s.setMaxPrice(1000000000);
  	Calendar c = Calendar.getInstance();
	  c.set(2100, 1, 1, 1, 13);
  	s.setStartTime(c.getTime());
  	c.set(2400, 1, 1, 1, 1);
  	s.setEndTime(c.getTime());
  	s.setAsset("");
  	s.setCurrency("");
  	List<AnalysisItem> list;
  	try {
  		SearchResult sr = Database.getDB().search(s);
  		if (sr.getNumResults() > 0) {
  		  list = DBAnalysis.getAvgPricePerMonth(s, connection, DBAnalysis.EXECUTION_TIME);
    	  assertEquals((list.get(0).getPrice()), 4.5, 0);
  		} else {
  			System.out.println("Empty");
  		}
  	} catch (SQLException e){
  		System.err.println("problem with getAvgPricePerMonth");
  	}
  }
  
  @Test
  public void testGetCurrency() throws SQLException {
  	fakeDB();
  	Search s = new Search();
  	s.setTradeType(TradeType.OPTION);
  	s.setAssetClass(AssetClass.COMMODITY);
  	s.setMinPrice(0);
  	s.setMaxPrice(1000000000);
  	Calendar c = Calendar.getInstance();
	  c.set(2100, 1, 1, 1, 13);
  	s.setStartTime(c.getTime());
  	c.set(2400, 1, 1, 1, 1);
  	s.setEndTime(c.getTime());
  	s.setAsset("");
  	s.setCurrency("");
  	List<String> list;
  	try {
  		SearchResult sr = Database.getDB().search(s);
  		if (sr.getNumResults() > 0) {
  		  list = DBAnalysis.getCurrencies(s, connection);
    	  assertEquals(list.get(0), "GBP");
    	  assertEquals(list.get(1), "USD");
  		} else {
  			System.out.println("Empty");
  		}
  	} catch (SQLException e){
  		System.err.println("problem with getCurrency");
  	}
  }
  
}
