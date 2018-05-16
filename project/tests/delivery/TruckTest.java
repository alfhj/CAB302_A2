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
	private Stock cargo;
	private Item item1;
	private Item item2;
	private final double DELTA = 1e5;
	
	@Before
	public void setUp() throws StockException {
		item1 = new Item("corn", 1, 2, 3, 4);
		item2 = new Item("milk", 5, 6, 7, 8, new Integer(1));
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
		truck = new RefrigeratedTruck(cargo, 0);
	}
	
	@Test
	public void testOTruckGetCapacity() throws DeliveryException {
		truck = new OrdinaryTruck(cargo);
		assertEquals(1000, truck.getCapacity());
	}

	@Test
	public void testRTruckGetCapacity() throws DeliveryException {
		truck = new RefrigeratedTruck(cargo, 0);
		assertEquals(800, truck.getCapacity());
	}
	
	@Test
	public void testOTruckGetCargo() throws DeliveryException {
		truck = new OrdinaryTruck(cargo);
		assertEquals(cargo.getItems(), truck.getCargo().getItems());
	}

	@Test
	public void testRTruckGetCargo() throws DeliveryException {
		truck = new RefrigeratedTruck(cargo, 0);
		assertEquals(cargo.getItems(), truck.getCargo().getItems());
	}
	
	@Test
	public void testOTruckUnderCapacity() throws DeliveryException {
		cargo.addItems(item1, 950);
		truck = new OrdinaryTruck(cargo);
		assertEquals(cargo.getItems(), truck.getCargo().getItems());
	}
	
	@Test (expected = DeliveryException.class)
	public void testOTruckOverCapacity() throws DeliveryException {
		cargo.addItems(item1, 951);
		truck = new OrdinaryTruck(cargo);
	}
	
	@Test
	public void testRTruckUnderCapacity() throws DeliveryException {
		cargo.addItems(item1, 750);
		truck = new RefrigeratedTruck(cargo, 0);
		assertEquals(cargo.getItems(), truck.getCargo().getItems());
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckOverCapacity() throws DeliveryException {
		cargo.addItems(item1, 751);
		truck = new RefrigeratedTruck(cargo, 0);
	}
	
	@Test
	public void testRTruckGetTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, 0);
		cargo.addItems(item3, 20);
		RefrigeratedTruck truck = new RefrigeratedTruck(cargo);
		assertEquals(0, truck.getTemperature());
	}
	
	@Test
	public void testRTruckHighTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, 10);
		cargo.addItems(item3, 20);
		RefrigeratedTruck truck = new RefrigeratedTruck(cargo);
		assertEquals(30, truck.getTemperature());
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckTooHighTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, 11);
		cargo.addItems(item3, 20);
		truck = new RefrigeratedTruck(cargo);
	}
	
	@Test
	public void testRTruckLowTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, -20);
		cargo.addItems(item3, 20);
		truck = new RefrigeratedTruck(cargo);
		assertEquals(-30, truck.getTemperature());
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckTooLowTemperature() throws DeliveryException, StockException {
		Item item3 = new Item("milk", 1, 2, 3, 4, -21);
		cargo.addItems(item3, 20);
		truck = new RefrigeratedTruck(cargo);
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
		Item item3 = new Item("milk", 1, 2, 3, 4, -7);
		cargo.addItems(item3, 20);
		truck = new RefrigeratedTruck(cargo);
		assertEquals(1229.5283165091, truck.getCost(), DELTA);
	}
}
