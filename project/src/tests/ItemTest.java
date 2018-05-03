package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author alfhj
 *
 */

public class ItemTest {
	
	Item item;

	@Before @Test
	public void setUpItem() throws StockException {
		item = null;
	}

	@Test
	public void testGetName() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals("rice", item.getName());
	}
	
	@Test (expected = StockException.class)
	public void testEmptyName() throws StockException {
		item = new Item("", 2, 3, 225, 300);
	}
	
	@Test
	public void testGetCost() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(2, item.getCost());
	}
	
	@Test (expected = StockException.class)
	public void testNegativeCost() throws StockException {
		item = new Item("rice", -5, 3, 225, 300);
	}

	@Test
	public void testZeroCost() throws StockException {
		item = new Item("rice", 0, 3, 225, 300);
		assertEquals(0, item.getCost());
	}
	
	@Test
	public void testGetPrice() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(3, item.getPrice());
	}
	
	@Test (expected = StockException.class)
	public void testNegativePrice() throws StockException {
		item = new Item("rice", 2, -3, 225, 300);
	}
	
	@Test (expected = StockException.class)
	public void testZeroPrice() throws StockException {
		item = new Item("rice", 2, 0, 225, 300);
	}
	
	@Test
	public void testGetReorderPoint() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(225, item.getReorderPoint());
	}
	
	@Test (expected = StockException.class)
	public void testNegativeReorderPoint() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
	}
	
	@Test
	public void testZeroReorderPoint() {
		item = new Item("rice", 2, 3, 0, 300);
		assertEquals(0, item.getReorderPoint());
	}

	@Test
	public void testGetReorderAmount() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(300, item.getReorderAmount());
	}
	
	@Test (expected = StockException.class)
	public void testNegativeReorderAmount() throws StockException {
		item = new Item("rice", 2, 3, 225, -200);
	}
	
	@Test (expected = StockException.class)
	public void testZeroReorderAmount() throws StockException {
		item = new Item("rice", 2, 3, 225, 0);
	}
	
	@Test
	public void testGetTemperaturePositive() {
		item = new Item("rice", 2, 3, 225, 300, 20);
		assertEquals(-20, item.getTemperature());
	}
	
	@Test
	public void testGetTemperatureNegative() {
		item = new Item("rice", 2, 3, 225, 300, -20);
		assertEquals(-20, item.getTemperature());
	}
	
	@Test
	public void testNoTemperature() {
		item = new Item("rice", 2, 3, 225, 300);
		assertNull(item.getTemperature());
	}
	
	@Test (expected = StockException.class)
	public void testCrazyTemperature1() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, 150);
	}
	
	@Test (expected = StockException.class)
	public void testCrazyTemperature2() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, -150);
	}
}
