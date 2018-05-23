package delivery;

import static org.junit.Assert.*;
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
public class TruckTest {

	private Truck truck;
	Stock cargo;
	private Item item1;
	private Item item2;
	private final double DELTA = 1e-5;
	
	@Before
	public void setUp() throws StockException {
		item1 = new Item("corn", 1, 2, 3, 4);
		item2 = new Item("milk", 5, 6, 7, 8, 1);
		cargo = new Stock();
		cargo.addItems(item1, 20);
		cargo.addItems(item2, 30);
	}

	@Test
	public void testMakeOrdinaryTruck() throws DeliveryException {
		truck = new OrdinaryTruck(cargo);
	}
	
	@Test
	public void testMakeRefrigeratedTruck() throws DeliveryException {
		truck = new RefrigeratedTruck(cargo);
	}
	
	@Test
	public void testOTruckGetCapacity() throws DeliveryException {
		truck = new OrdinaryTruck(cargo);
		assertEquals(1000, truck.getCapacity());
	}

	@Test
	public void testRTruckGetCapacity() throws DeliveryException {
		truck = new RefrigeratedTruck(cargo);
		assertEquals(800, truck.getCapacity());
	}
	
	@Test
	public void testOTruckGetCargo() throws DeliveryException {
		truck = new OrdinaryTruck(cargo);
		assertEquals(cargo, truck.getCargo());
	}

	@Test
	public void testRTruckGetCargo() throws DeliveryException {
		truck = new RefrigeratedTruck(cargo);
		assertEquals(cargo, truck.getCargo());
	}
	
	// maximum capacity is 1000
	@Test
	public void testOTruckUnderCapacity() throws DeliveryException, StockException {
		cargo.addItems(item1, 950);
		truck = new OrdinaryTruck(cargo);
		assertEquals(cargo, truck.getCargo());
	}
	
	// 1000 items is too high
	@Test (expected = DeliveryException.class)
	public void testOTruckOverCapacity() throws DeliveryException, StockException {
		cargo.addItems(item1, 951);
		truck = new OrdinaryTruck(cargo);
	}
	
	// maximum capacity is 800
	@Test
	public void testRTruckUnderCapacity() throws DeliveryException, StockException {
		cargo.addItems(item1, 750);
		truck = new RefrigeratedTruck(cargo);
		assertEquals(cargo, truck.getCargo());
	}
	
	// 801 items is too high
	@Test (expected = DeliveryException.class)
	public void testRTruckOverCapacity() throws DeliveryException, StockException {
		cargo.addItems(item1, 751);
		truck = new RefrigeratedTruck(cargo);
	}
	
	// the temperature is equal to the cargo item with the lowest temperature
	@Test
	public void testRTruckGetTemperature() throws DeliveryException, StockException {
		RefrigeratedTruck truck = new RefrigeratedTruck(cargo);
		assertEquals(1, truck.getTemperature());
	}
	
	// highest possible temperature is 10
	@Test
	public void testRTruckHighTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, 10);
		cargo = new Stock();
		cargo.addItems(item3, 100);
		RefrigeratedTruck truck = new RefrigeratedTruck(cargo);
		assertEquals(10, truck.getTemperature());
	}
	
	// lowest possible temperature is -20
	@Test
	public void testRTruckLowTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, -20);
		cargo = new Stock();
		cargo.addItems(item3, 100);
		RefrigeratedTruck truck = new RefrigeratedTruck(cargo);
		assertEquals(-20, truck.getTemperature());
	}

	// if cargo contains no refrigerated items, set highest temperature
	@Test
	public void testROnlyUnrefrigeratedItems() throws DeliveryException, StockException {
		Item item3 = new Item("rice", 1, 2, 3, 4);
		cargo = new Stock();
		cargo.addItems(item3, 100);
		RefrigeratedTruck truck = new RefrigeratedTruck(cargo);
		assertEquals(10, truck.getTemperature());
	}
	
	@Test
	public void testOTruckGetCost() throws DeliveryException {
		truck = new OrdinaryTruck(cargo);
		assertEquals(762.5, truck.getCost(), DELTA);
	}
	
	@Test
	public void testRTruckGetCost1() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, 0);
		cargo.addItems(item3, 20);
		truck = new RefrigeratedTruck(cargo);
		assertEquals(1100.0, truck.getCost(), DELTA);
	}

	@Test
	public void testRTruckGetCost2() throws DeliveryException, StockException {
		Item item3 = new Item("ice cream", 1, 2, 3, 4, -7);
		cargo.addItems(item3, 20);
		truck = new RefrigeratedTruck(cargo);
		assertEquals(1229.5283165091, truck.getCost(), DELTA);
	}
	
	@Test
	public void testEquals() throws StockException, DeliveryException {
		truck = new OrdinaryTruck(cargo);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 20);
		stock1.addItems(item2, 30);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		assertTrue(truck.equals(truck1));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsFalse() throws StockException, DeliveryException {
		cargo.addItems(item1, 50);
		cargo.addItems(item2, 100);
		truck = new OrdinaryTruck(cargo);
		
		assertFalse(truck.equals("Something else"));
	}
	
	@Test
	public void testHashcode() throws StockException, DeliveryException {
		cargo.addItems(item1, 50);
		cargo.addItems(item2, 100);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		truck = new OrdinaryTruck(stock1);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		assertEquals(truck1.hashCode(), truck.hashCode());
	}
	
}
