package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

/**
 *
 * @author Wai-Wai Ng
 */
public class DatabaseTest {
	
	static Date d;
	
	/**
	 * 
	 */
	@org.junit.BeforeClass
	public static void setUp(){
		d = new Date();
	}
		
	/**
	 * Test of getDB method, of class Database.
	 */
	@Test
	public void testGetDB() {
		System.out.println("getDB");
		Database result = Database.getDB();
		assertNotNull(result);
	}


	/**
	 * Test of addTrade method, of class Database. This tests an insertion.
	 * @throws java.sql.SQLException
	 */
	@Test
	public void testAddTrade() throws SQLException {
		System.out.println("addTradeInsert");
		
		List<Trade> tradeList = new LinkedList<>();
		
		Trade trade = new Trade();
		trade.setCleared(Boolean.TRUE);
		trade.setExecutionTimestamp(d);
		trade.setDisseminationID(128);
				
		tradeList.add(trade);
		
		Database instance = Database.getDB();
		boolean result = instance.addTrade(tradeList);
		assertTrue(result);
		
		Connection c = instance.getConnection();
		ResultSet rs = c.createStatement().executeQuery("SELECT cleared FROM data WHERE id = 128");
		assertTrue(rs.next());
		assertEquals(true, rs.getBoolean(1));
	}
	
	private void specialAdder() throws SQLException {	
		List<Trade> tradeList = new LinkedList<>();
		
		Trade trade = new Trade();
		trade.setCleared(Boolean.TRUE);
		trade.setExecutionTimestamp(d);
		trade.setDisseminationID(127);
		
		tradeList.add(trade);
				
		Database instance = Database.getDB();
		boolean result = instance.addTrade(tradeList);
		assertTrue(result);
	}
	
	private void specialAdder(Trade t) throws SQLException {
		List<Trade> tradeList = new LinkedList<>();
		tradeList.add(t);	
		Database instance = Database.getDB();
		boolean result = instance.addTrade(tradeList);
		assertTrue(result);
	}
	
	/**
	 * Test of addTrade method, of class Database. This tests the update of an existing row.
	 * @throws java.sql.SQLException
	 */
	@Test
	public void testUpdateTrade() throws SQLException {
		System.out.println("addTradeUpdate");
		specialAdder();
		
		List<Trade> tradeList = new LinkedList<>();
		
		Trade trade = new Trade();
		trade.setCleared(Boolean.FALSE);
		trade.setBespoke(Boolean.TRUE);
		trade.setAction(Action.CORRECT);
		trade.setDisseminationID(127);
		
		tradeList.add(trade);
		
		Database instance = Database.getDB();
		boolean result = instance.addTrade(tradeList);
		assertTrue(result);
		
		Connection c = instance.getConnection();
		ResultSet rs = c.createStatement().executeQuery("SELECT cleared FROM data WHERE id = 127");
		assertTrue(rs.next());
		assertEquals(false, rs.getBoolean(1));
		
		c = instance.getConnection();
		rs = c.createStatement().executeQuery("SELECT bespoke FROM data WHERE id = 127");
		assertTrue(rs.next());
		assertEquals(true, rs.getBoolean(1));
	}
	
	/**
	 * Test of addTrade method, of class Database. This tests a deletion of an existing row.
	 * @throws java.sql.SQLException
	 */
	@Test
	public void testDeleteTrade() throws SQLException {
		System.out.println("addTradeDelete");
		specialAdder();
		
		List<Trade> tradeList = new LinkedList<>();
		
		Trade trade = new Trade();
		trade.setAction(Action.CANCEL);
		trade.setDisseminationID(127);
		
		tradeList.add(trade);
		
		Database instance = Database.getDB();
		boolean result = instance.addTrade(tradeList);
		assertTrue(result);
		
		Connection c = instance.getConnection();
		ResultSet rs = c.createStatement().executeQuery("SELECT * FROM data WHERE id = 127");
		assertFalse(rs.next());
	}

	/**
	 * Test of getLastUpdateTime method, of class Database.
	 * @throws java.sql.SQLException
	 */
	@Test
	public void testGetLastUpdateTime() throws SQLException {
		System.out.println("getLastUpdateTime");
		specialAdder();
		
		Database instance = Database.getDB();
		Date expResult = d;
		Date result = instance.getLastUpdateTime();
		assertTrue((expResult.getTime() - result.getTime()) < 1000); // accept a second of tolerance
	}
	
}
