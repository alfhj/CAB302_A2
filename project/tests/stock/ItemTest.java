package stock;

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

	// reset item before each test
	@Before @Test
	public void setUpItem() {
		item = null;
	}
	
	@Test
	public void testConstructor() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
	}

	@Test
	public void testGetName1() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals("rice", item.getName());
	}

	@Test
	public void testGetName2() throws StockException {
		item = new Item("cookies", 2, 3, 225, 300);
		assertEquals("cookies", item.getName());
	}
	
	// an item should not have a empty name
	@Test (expected = StockException.class)
	public void testEmptyName() throws StockException {
		item = new Item("", 2, 3, 225, 300);
	}
	
	@Test
	public void testGetCost1() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(2, item.getCost());
	}

	@Test
	public void testGetCost2() throws StockException {
		item = new Item("rice", 6, 3, 225, 300);
		assertEquals(6, item.getCost());
	}
	
	// an item's cost can not be negative
	@Test (expected = StockException.class)
	public void testNegativeCost() throws StockException {
		item = new Item("rice", -5, 3, 225, 300);
	}

	// an item's cost can be zero
	@Test
	public void testZeroCost() throws StockException {
		item = new Item("rice", 0, 3, 225, 300);
		assertEquals(0, item.getCost());
	}
	
	@Test
	public void testGetPrice1() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(3, item.getPrice());
	}

	@Test
	public void testGetPrice2() throws StockException {
		item = new Item("rice", 2, 10, 225, 300);
		assertEquals(10, item.getPrice());
	}
	
	// an item's price can not be negative
	@Test (expected = StockException.class)
	public void testNegativePrice() throws StockException {
		item = new Item("rice", 2, -3, 225, 300);
	}

	// an item's price can be zero
	@Test
	public void testZeroPrice() throws StockException {
		item = new Item("rice", 2, 0, 225, 300);
		assertEquals(0, item.getPrice());
	}
	
	@Test
	public void testGetReorderPoint1() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(225, item.getReorderPoint());
	}
	
	@Test
	public void testGetReorderPoint2() throws StockException {
		item = new Item("rice", 2, 3, 250, 300);
		assertEquals(250, item.getReorderPoint());
	}

	// an item's reorder point can not be negative
	@Test (expected = StockException.class)
	public void testNegativeReorderPoint() throws StockException {
		item = new Item("rice", 2, 3, -225, 300);
	}

	// an item's reorder point can be zero
	@Test
	public void testZeroReorderPoint() throws StockException {
		item = new Item("rice", 2, 3, 0, 300);
		assertEquals(0, item.getReorderPoint());
	}

	@Test
	public void testGetReorderAmount1() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
		assertEquals(300, item.getReorderAmount());
	}

	@Test
	public void testGetReorderAmount2() throws StockException {
		item = new Item("rice", 2, 3, 225, 400);
		assertEquals(400, item.getReorderAmount());
	}

	// an item's reorder amount can not be negative
	@Test (expected = StockException.class)
	public void testNegativeReorderAmount() throws StockException {
		item = new Item("rice", 2, 3, 225, -200);
	}

	// an item's reorder amount should not be zero
	@Test (expected = StockException.class)
	public void testZeroReorderAmount() throws StockException {
		item = new Item("rice", 2, 3, 225, 0);
	}
	
	@Test
	public void testGetTemperaturePositive() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, 5);
		// Integer because temperature must be nullable
		assertEquals(new Integer(5), item.getTemperature());
	}
	
	@Test
	public void testGetTemperatureNegative() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, -5);
		assertEquals(new Integer(-5), item.getTemperature());
	}
	
	// no temperature means null
	@Test
	public void testNoTemperature() throws StockException {
		item = new Item("rice", 2, 3, 225, 300);
		assertNull(item.getTemperature());
	}
	
	// max temperature is 10
	@Test
	public void testHighTemperature() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, 10);
		assertEquals(new Integer(10), item.getTemperature());
	}
	
	@Test (expected = StockException.class)
	public void testTooHighTemperature() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, 11);
	}

	// min temperature is -20
	@Test
	public void testLowTemperature() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, -20);
		assertEquals(new Integer(-20), item.getTemperature());
	}
	
	@Test (expected = StockException.class)
	public void testTooLowTemperature() throws StockException {
		item = new Item("rice", 2, 3, 225, 300, -21);
	}
	
}
