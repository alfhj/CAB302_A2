package stock;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lara09
 *
 */
public class StockTest {
	
	Stock stock;
	Map<Item, Integer> result;

	@Before @Test
	public void setUpStock() {	
		stock = new Stock();	
		result = new HashMap<Item, Integer>();
	}

	@Test
	public void testAddItems() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		result.put(item1, 15);
		assertEquals(result, stock.getItems());
	}
	
	@Test
	public void testAddZero() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 0);
		result.put(item1, 0);
		assertEquals(result, stock.getItems());
	}
	
	@Test (expected = StockException.class)
	public void testNegativeValue() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, -1);
	}
	
	@Test (expected = StockException.class)
	public void testNotInStock() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.removeItems(item1, 10);
	}
	
	@Test
	public void testRemoveOneByOne() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		for (int i = 0; i < 10; i++) {
			stock.removeItems(item1, 1);
		}
		result.put(item1, 5);
		assertEquals(result, stock.getItems());
	}
	
	@Test (expected = StockException.class)
	public void testRemoveOneByOneOver() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 5);
		for (int i = 0; i < 10; i++) {
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
	public void testRemoveItem() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 2);
		result.put(item1, 13);
		assertEquals(result, stock.getItems());
	}
	
	@Test
	public void testRemoveAll() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 15);
		result.put(item1, 0);
		assertEquals(result, stock.getItems());
	}
	
	@Test 
	public void testAddSameItem() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.addItems(item1, 20);
		result.put(item1, 35);
		assertEquals(result, stock.getItems());
	}
	
	@Test 
	public void testAddThree() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		Item item3 = new Item("pasta", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.addItems(item2, 2);
		stock.addItems(item3, 8);
		result.put(item1, 15);
		result.put(item2, 2);
		result.put(item3, 8);
		assertEquals(result, stock.getItems());
	}
	
	//removing a negative amount of an item not acceptable
	@Test (expected = StockException.class)
	public void testRemoveNegativeValue() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, -1);
	}
	
	//check that removing a zero amount of items does not change the amount of the product
	@Test 
	public void testRemoveZeroValue() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		stock.addItems(item1, 15);
		stock.removeItems(item1, 0);
		result.put(item1, 15);
		assertEquals(result, stock.getItems());
	}

	//check that it calculates the sum amount of product correctly
	@Test
	public void testSumItems() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		stock.addItems(item1, 50);
		stock.addItems(item2, 100);

		assertEquals(150, stock.getNumItems());
	}
	
	//search for an item returns correct key and value
	@Test
	public void testSearchItem() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		Item item3 = new Item("cookies", 2, 3, 225, 300);
		stock.addItems(item1, 50);
		stock.addItems(item2, 100);
		stock.addItems(item3, 200);
		Entry<Item, Integer> searchResult = stock.searchItem("cookies");

		assertEquals(item3, searchResult.getKey());
		assertEquals(new Integer(200), searchResult.getValue());
	}
	
	//search for non existing item
	@Test (expected = StockException.class)
	public void testSearchItemNotExist() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		Item item3 = new Item("cookies", 2, 3, 225, 300);
		stock.addItems(item1, 50);
		stock.addItems(item2, 100);
		stock.addItems(item3, 200);
		stock.searchItem("milk");
	}
	
	// tests the equals() method
	// two different instances of Stock with the same contests should be equal
	@Test
	public void testEquals() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		stock.addItems(item1, 50);
		stock.addItems(item2, 100);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		
		assertTrue(stock.equals(stock1));
	}
	
	// equals() should fail if items and/or quantity differ
	@Test
	public void testEqualsFalse() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		stock.addItems(item1, 50);
		stock.addItems(item2, 100);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 51);
		stock1.addItems(item2, 100);

		assertFalse(stock.equals(stock1));
	}
	
	// should check hashcode by passing Map hashcode
	@Test
	public void testHashcode() throws StockException {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("flour", 2, 3, 225, 300);
		stock.addItems(item1, 50);
		stock.addItems(item2, 100);

		result.put(item1, 50);
		result.put(item2, 100);
		
		assertEquals(result.hashCode(), stock.hashCode());
	}
	
}
