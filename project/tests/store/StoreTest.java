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
	public void setUpStore() throws StockException, StoreException {
		store = Store.reset();
		store = Store.getInstance();
		item1 = new Item("rice", 2, 3, 225, 300);
		item2 = new Item("nuts", 5, 9, 125, 250);
		item3 = new Item("tomatoes", 1, 2, 325, 400, 6);
		Stock inventory = new Stock();
		inventory.addItems(item1, 0);
		inventory.addItems(item2, 0);
		inventory.addItems(item3, 0);
		store.loadInventory(inventory);
	}

	@Test
	public void testGetName() {
		assertEquals("Norwegian Delight", store.getName());
	}
	
	@Test
	public void testGetCapital() {
		assertEquals(100000.0, store.getCapital(), DELTA);
	}
	
	@Test
	public void testGetInventory() throws StockException, StoreException {
		Stock stock = new Stock();
		stock.addItems(item1, 0);
		stock.addItems(item2, 0);
		store.loadInventory(stock);
		assertEquals(stock, store.getInventory());
	}
	
	@Test
	public void testLoadInventoryWithPositiveAmounts() throws StockException, StoreException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		store.loadInventory(stock1);
		
		Stock correctStock = new Stock();
		correctStock.addItems(item1, 0);
		correctStock.addItems(item2, 0);
		
		assertEquals(correctStock, store.getInventory());
	}
	
	@Test
	public void testExportManifest() throws StockException, DeliveryException, StoreException {
		// correct manifest
		Stock reorderStock = new Stock();
		reorderStock.addItems(item1, 300);
		reorderStock.addItems(item2, 250);
		OrdinaryTruck truck1 = new OrdinaryTruck(reorderStock);
		Manifest correctManifest = new Manifest();
		correctManifest.addTruck(truck1);
		
		assertEquals(correctManifest, store.exportManifest());
	}
	
	@Test
	public void testImportManifest() throws StockException, DeliveryException, StoreException
	{	
		Stock reorderStock1 = new Stock();
		reorderStock1.addItems(item1, 200);
		reorderStock1.addItems(item2, 50);
		OrdinaryTruck truck1 = new OrdinaryTruck(reorderStock1);
		
		Stock reorderStock2 = new Stock();
		reorderStock2.addItems(item1, 100);
		reorderStock2.addItems(item2, 200);
		RefrigeratedTruck truck2 = new RefrigeratedTruck(reorderStock2);
		
		Manifest manifest = new Manifest();
		manifest.addTruck(truck1);
		manifest.addTruck(truck2);
		store.importManifest(manifest);
		
		Stock correctInventory = new Stock();
		correctInventory.addItems(item1, 300);
		correctInventory.addItems(item2, 250);
		
		assertEquals(correctInventory, store.getInventory());
		assertEquals(94764.5, store.getCapital(), DELTA);
	}
	
	@Test (expected = StoreException.class)
	public void testImportManifestNotReorderAmount() throws StockException, DeliveryException, StoreException
	{	
		Stock reorderStock1 = new Stock();
		reorderStock1.addItems(item1, 200);
		reorderStock1.addItems(item2, 50);
		OrdinaryTruck truck1 = new OrdinaryTruck(reorderStock1);
		
		Stock reorderStock2 = new Stock();
		reorderStock2.addItems(item1, 99);
		reorderStock2.addItems(item2, 201);
		RefrigeratedTruck truck2 = new RefrigeratedTruck(reorderStock2);
		
		Manifest manifest = new Manifest();
		manifest.addTruck(truck1);
		manifest.addTruck(truck2);
		store.importManifest(manifest);
	}
	
	@Test (expected = StoreException.class)
	public void testImportManifestItemNotExist() throws StockException, DeliveryException, StoreException
	{		
		Stock reorderStock = new Stock();
		Item item4 = new Item("bread", 2, 3, 400, 500);
		reorderStock.addItems(item1, 300);
		reorderStock.addItems(item4, 500);
		OrdinaryTruck truck = new OrdinaryTruck(reorderStock);
		Manifest manifest = new Manifest();
		manifest.addTruck(truck);
		store.importManifest(manifest);
	}
	
	@Test (expected = StoreException.class)
	public void testImportManifestNoNeed() throws StockException, DeliveryException, StoreException
	{
		store.getInventory().addItems(item1, 226);
		
		Stock reorderStock = new Stock();
		reorderStock.addItems(item1, 300);
		OrdinaryTruck truck = new OrdinaryTruck(reorderStock);
		Manifest manifest = new Manifest();
		manifest.addTruck(truck);
		store.importManifest(manifest);		
	}
	
	@Test
	public void testImportSalesLog() throws StockException, StoreException
	{
		store.getInventory().addItems(item1, 100);
		store.getInventory().addItems(item2, 200);
		
		Stock sold = new Stock();
		sold.addItems(item1, 50);
		sold.addItems(item2, 50);
		store.importSalesLog(sold);
		
		Stock newStock = new Stock();
		newStock.addItems(item1, 50);
		newStock.addItems(item2, 150);
		newStock.addItems(item3, 0);

		assertEquals(newStock, store.getInventory());
		assertEquals(100600.0, store.getCapital(), DELTA);
		
	}
	
	@Test (expected = StoreException.class)
	public void testImportSalesLogNegative() throws StockException, StoreException
	{
		Stock sold = new Stock();
		sold.addItems(item1, 1);
		sold.addItems(item2, 1);
		store.importSalesLog(sold);
	}
	
	@Test (expected = StoreException.class)
	public void testImportSalesLogItemNotExist() throws StockException, StoreException
	{
		Stock sold = new Stock();
		Item item4 = new Item("bread", 2, 3, 4, 5);
		sold.addItems(item1, 1);
		sold.addItems(item2, 1);
		sold.addItems(item4, 1);
		store.importSalesLog(sold);
	}
	
	@Test (expected = StoreException.class)
	public void testOrderItemsCostNegative() throws StockException, DeliveryException, StoreException
	{
		Stock reorderStock = new Stock();
		reorderStock.addItems(item1, 300);
		reorderStock.addItems(item2, 250);
		OrdinaryTruck truck = new OrdinaryTruck(reorderStock);
		Manifest manifest = new Manifest();
		manifest.addTruck(truck);
		for (int i = 0; i < 55; i++) {
			store.importManifest(manifest);
		}	
	}
	
}
