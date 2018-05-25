package delivery;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * 
 * @author alfhj
 *
 */
public class ManifestTest {

	// the manifest to be tested
	private Manifest manifest;
	// items which are part of the manifest
	private Item item1;
	private Item item2;
	private Item item3;
	// the correct set of trucks
	private Set<Truck> trucks;
	// 0.00001 allowed difference between doubles
	private final double DELTA = 1e-5;
	
	// set up manifest and items before each test
	@Before @Test
	public void setUpManifest() throws StockException {
		item1 = new Item("rice", 2, 3, 225, 300);
		item2 = new Item("nuts", 5, 9, 125, 250);
		item3 = new Item("tomatoes", 1, 2, 325, 400, 6);
		manifest = new Manifest();
		trucks = new HashSet<Truck>();
	}

	// add items to truck
	// checks that manifest's fleet is equal to the set of trucks
	@Test
	public void testAddTruck() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		manifest.addTruck(truck1);
		trucks.add(truck1);
		assertEquals(trucks, manifest.getFleet());
	}
	
	@Test
	public void testAddTwoTrucks() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		Stock stock2 = new Stock();
		stock2.addItems(item2, 10);
		stock2.addItems(item3, 20);
		Truck truck2 = new RefrigeratedTruck(stock1);
		
		manifest.addTruck(truck1);
		manifest.addTruck(truck2);
		trucks.add(truck1);
		trucks.add(truck2);
		assertEquals(trucks, manifest.getFleet());
	}
	
	// Set from getFleet() should not be modifiable
	@Test (expected = UnsupportedOperationException.class)
	public void testGetFleetModify() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		Stock stock2 = new Stock();
		stock2.addItems(item2, 10);
		stock2.addItems(item3, 20);
		Truck truck2 = new RefrigeratedTruck(stock1);
		
		manifest.addTruck(truck1);
		manifest.getFleet().add(truck2);
	}
	
	@Test
	public void testGetCostSingle() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		manifest.addTruck(truck1);
		// the total cost is the combined cost of each truck in the manifest
		// including the combined costs of all items in each truck
		assertEquals(1387.5, manifest.getTotalCost(), DELTA);
	}
	
	@Test
	public void testGetCostTwoTrucks() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		Stock stock2 = new Stock();
		stock2.addItems(item2, 10);
		stock2.addItems(item3, 20);
		Truck truck2 = new RefrigeratedTruck(stock2);
		
		manifest.addTruck(truck1);
		manifest.addTruck(truck2);
		assertEquals(1977.8609881132, manifest.getTotalCost(), DELTA);
	}
	
	@Test
	public void testGetCostEmptyOrdinary() throws DeliveryException {
		Stock stock1 = new Stock();
		Truck truck1 = new OrdinaryTruck(stock1);
		manifest.addTruck(truck1);
		assertEquals(750.0, manifest.getTotalCost(), DELTA);
	}
	
	@Test
	public void testGetCostEmptyRefrigerated() throws DeliveryException {
		Stock stock1 = new Stock();
		Truck truck1 = new RefrigeratedTruck(stock1);
		manifest.addTruck(truck1);
		assertEquals(998.0, manifest.getTotalCost(), DELTA);
	}
	
	// tests the equals() method
	// two different instances of manifests with the same contests should be equal 
	@Test
	public void testEquals() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		manifest.addTruck(truck1);

		Stock stock2 = new Stock();
		stock2.addItems(item1, 50);
		stock2.addItems(item2, 100);
		Truck truck2 = new OrdinaryTruck(stock2);
		Manifest correctManifest = new Manifest();
		correctManifest.addTruck(truck2);
		
		assertTrue(manifest.equals(correctManifest));
	}
	
	// equals() should fail if manifests differ
	@Test
	public void testEqualsFalse() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		manifest.addTruck(truck1);
		
		Stock stock2 = new Stock();
		stock2.addItems(item1, 51);
		stock2.addItems(item2, 100);
		Truck truck2 = new OrdinaryTruck(stock2);
		Manifest wrongManifest = new Manifest();
		wrongManifest.addTruck(truck2);
		
		assertFalse(manifest.equals(wrongManifest));
	}
	
	// should check hashcode by passing Map's hashcode
	@Test
	public void testHashcode() throws StockException, DeliveryException {
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		trucks.add(truck1);
		manifest.addTruck(truck1);
		Manifest correctManifest = new Manifest();
		correctManifest.addTruck(truck1);
		assertEquals(trucks.hashCode(), manifest.hashCode());
	}
	
}
