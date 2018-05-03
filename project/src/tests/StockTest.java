package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class StockTest {
	
	Stock stock;
	Map<Item, Integer> result;

	@Before @Test
	public void setUpStock() throws Exception {	
		stock = new StockTest();
		result = new HashMap<Item, Integer>();
	}

	@Test
	public void testAddItems() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItem(item1, 15);
		result.put(item1, 15);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test
	public void testAddZero() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItem(item1, 0);
		result.put(item1, 0);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test (expected = StockException.class)
	public void testNegativeValue() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItem(item1, -1);
	}
	
	@Test (expected = StockException.class)
	public void testNotInStock() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.removeItems(item1, 10);
	}
	
	@Test
	public void testRemoveOneByOne() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		for (int i = 0, i < 10, i++)
		{
		stock.removeItems(item1, 1);
		}
		result.put(item1, 5);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test (expected = StockException.class)
	public void testRemoveOneByOneOver() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 5);
		for (int i = 0; i < 10; i++)
		{
		stock.removeItems(item1, 1);
		}
	}
	
	@Test (expected = StockException.class)
	public void testRemoveTooMany() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 5);
		stock.removeItems(item1, 10);
	}
		
	@Test
	public void testRemoveItem() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 2);
		result.put(item1, 13);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test
	public void testRemoveAll() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 15);
		result.put(item1, 0);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test 
	public void testAddSameItem() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.addItems(item1, 20);
		result.put(item1, 35);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test 
	public void testAddThree() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		Item item3 = new Item("pasta", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.addItems(item2, 2);
		stock.addItems(item3, 8);
		result.put(item1, 15);
		result.put(item2, 2);
		result.put(item3, 8);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test (expected = StockException.class)
	public void testRemoveNegativeValue() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, -1);
	}
	
	@Test 
	public void testRemoveZeroValue() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 0);
		result.put(item1, 15);
		assertTrue(result.equals(stock.getStock()));
	}
	
	@Test 
	public void testValue() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 0);
	}
	
}
