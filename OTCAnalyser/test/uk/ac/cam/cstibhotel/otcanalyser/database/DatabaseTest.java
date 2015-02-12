package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.Search;
import uk.ac.cam.cstibhotel.otcanalyser.communicationlayer.SearchResult;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;

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
	 */
	@Test
	public void testAddTrade() throws SQLException {
		System.out.println("addTradeInsert");
		
		Trade trade = new Trade();
		trade.setCleared(Boolean.TRUE);
		trade.setExecutionTimestamp(d);
		trade.setDisseminationID(128);
				
		Database instance = Database.getDB();
		boolean result = instance.addTrade(trade);
		assertTrue(result);
		
		Connection c = instance.getConnection();
		ResultSet rs = c.createStatement().executeQuery("SELECT cleared FROM data WHERE id = 128");
		assertTrue(rs.next());
		assertEquals(true, rs.getBoolean(1));
	}
	
	public void specialAdder() throws SQLException {
		System.out.println("addTradeInsert");
		
		Trade trade = new Trade();
		trade.setCleared(Boolean.TRUE);
		trade.setExecutionTimestamp(d);
		trade.setDisseminationID(127);
				
		Database instance = Database.getDB();
		boolean result = instance.addTrade(trade);
		assertTrue(result);
	}
	
	/**
	 * Test of addTrade method, of class Database. This tests the update of an existing row.
	 */
	@Test
	public void testUpdateTrade() throws SQLException {
		System.out.println("addTradeUpdate");
		specialAdder();
		
		Trade trade = new Trade();
		trade.setCleared(Boolean.FALSE);
		trade.setBespoke(Boolean.TRUE);
		trade.setAction(Action.CORRECT);
		trade.setDisseminationID(127);
		
		Database instance = Database.getDB();
		boolean result = instance.addTrade(trade);
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
	 */
	@Test
	public void testDeleteTrade() throws SQLException {
		System.out.println("addTradeDelete");
		specialAdder();
		
		Trade trade = new Trade();
		trade.setAction(Action.CANCEL);
		trade.setDisseminationID(127);
		
		Database instance = Database.getDB();
		boolean result = instance.addTrade(trade);
		assertTrue(result);
		
		Connection c = instance.getConnection();
		ResultSet rs = c.createStatement().executeQuery("SELECT * FROM data WHERE id = 127");
		assertFalse(rs.next());
	}

	/**
	 * Test of getLastUpdateTime method, of class Database.
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

	/**
	 * Test of search method, of class Database.
	 */
	@Ignore @Test
	public void testSearch() {
		System.out.println("search");
		Search s = null;
		Database instance = null;
		SearchResult expResult = null;
		SearchResult result = instance.search(s);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of saveSearch method, of class Database.
	 */
	@Ignore @Test
	public void testSaveSearch() {
		System.out.println("saveSearch");
		Search s = null;
		Database instance = null;
		boolean expResult = false;
		boolean result = instance.saveSearch(s);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getSavedSearches method, of class Database.
	 */
	@Ignore @Test
	public void testGetSavedSearches() {
		System.out.println("getSavedSearches");
		Database instance = null;
		List<Search> expResult = null;
		List<Search> result = instance.getSavedSearches();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getMatchingUPI method, of class Database.
	 */
	@Ignore @Test
	public void testGetMatchingUPI() {
		System.out.println("getMatchingUPI");
		String s = "";
		Database instance = null;
		List<UPI> expResult = null;
		List<UPI> result = instance.getMatchingUPI(s);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}
