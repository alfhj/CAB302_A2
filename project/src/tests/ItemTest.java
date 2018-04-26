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
	// test

	@Before
	public void setUp() throws Exception {
		item = null;
	}

	@Test
	public void testGetName() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals("rice", item.getName());
	}
	
	@Test (expected = Exception.class)
	public void testEmptyName() throws Exception {
		item = new Item("", 2, 3, 225, 300);
	}
	
	@Test
	public void testCost() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(2, item.getCost());
	}
	
	@Test
	public void testGetPrice() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(3, item.getPrice());
	}
	
	@Test (expected = Exception.class)
	public void testNegativePrice() throws Exception {
		item = new Item("rice", -5, 3, 225, 300);
	}
	
	@Test
	public void testGetReorderPoint() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(225, item.getReorderPoint());
	}
	@Test
	public void testGetReorderAmount() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(300, item.getReorderAmount());
	}
	@Test
	public void testGetTemperature() {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(null, item.getTemperature());
	}

}
