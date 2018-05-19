package store;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import delivery.*;
import stock.*;

/**
 * 
 * @author lara09
 *
 */
public class StoreTest {

	Store store;
	Item item1;
	Item item2;
	Item item3;
	private int capitalcost;
	private final double DELTA = 1e-5;
	
	@Before @Test
	public void setUpStore() throws Exception {
		store = Store.reset();
		store = Store.getInstance();
		item1 = new Item("rice", 2, 3, 225, 300);
		item2 = new Item("nuts", 5, 9, 125, 250);
		item3 = new Item("tomatoes", 1, 2, 325, 400, 6);
		
	}

	@Test
	public void testGetName() {
		store.setName("Norwegian Delight");
		assertEquals("Norwegian Delight", store.getName());
	}
	
	@Test
	public void testGetCapital() {
		assertEquals(100000.00, store.getCapital(), DELTA);
	}
	
	@Test
	public void testGetInventory() {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 0);
		stock1.addItems(item2, 0);
		store.loadInventory(stock1);
		assertEquals(stock1.getItems(), store.getInventory().getItems());
	}
	
	@Test
	public void testLoadInventoryWithPositiveAmounts() {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		store.loadInventory(stock1);
		
		Stock correctStock = new Stock();
		correctStock.addItems(item1, 0);
		correctStock.addItems(item2, 0);
		
		assertEquals(correctStock.getItems(), store.getInventory().getItems());
	}
	
	@Test
	public void testExportManifest() {
		// load inventory
		Stock stock1 = new Stock();
		stock1.addItems(item1, 0);
		stock1.addItems(item2, 0);
		store.loadInventory(stock1);
		
		// correct manifest
		Stock reorderStock = new Stock();
		reorderStock.addItems(item1, 300);
		reorderStock.addItems(item2, 250);
		OrdinaryTruck truck1 = new OrdinaryTruck(reorderStock);
		Manifest correctManifest = new Manifest();
		correctManifest.addTruck(truck1);
		
		assertEquals(correctManifest, store.exportManifest());
	}

	
	/*
	@Test
	public void testTotalCostWithCapitalCost() throws StoreException {
		
		manifest.getTotalCost();
		if (manifest.getTotalCost() - capitalcost) < 0
				

	}
	*/
}
