package delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import stock.Item;
import stock.Stock;

/**
 * 
 * @author alfhj
 *
 */
public class TruckTest {

	private Truck truck;
	private Stock stock;
	private Item item1 = new Item("corn", 1, 2, 3, 4);
	private Item item2 = new Item("milk", 5, 6, 7, 8, 1);
	private final double DELTA = 1e5;
	
	@Before
	public void setUp() {
		stock = new Stock();
		stock.addItems(item1, 20);
		stock.addItems(item2, 30);
	}

	@Test
	public void testMakeOrdinaryTruck() throws DeliveryException {
		truck = new OrdinaryTruck(stock);
	}
	
	@Test
	public void testMakeRefrigeratedTruck() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 0);
	}
	
	@Test
	public void testOTruckGetCapacity() throws DeliveryException {
		truck = new OrdinaryTruck(stock);
		assertEquals(1000, truck.getCapacity());
	}

	@Test
	public void testRTruckGetCapacity() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 0);
		assertEquals(800, truck.getCapacity());
	}
	
	@Test
	public void testOTruckGetCargo() throws DeliveryException {
		truck = new OrdinaryTruck(stock);
		assertEquals(stock.getItems(), truck.getCargo().getItems());
	}

	@Test
	public void testRTruckGetCargo() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 0);
		assertEquals(stock.getItems(), truck.getCargo().getItems());
	}
	
	@Test
	public void testOTruckUnderCapacity() throws DeliveryException {
		stock.addItems(item1, 950);
		truck = new OrdinaryTruck(stock);
		assertEquals(stock.getItems(), truck.getCargo().getItems());
	}
	
	@Test (expected = DeliveryException.class)
	public void testOTruckOverCapacity() throws DeliveryException {
		stock.addItems(item1, 951);
		truck = new OrdinaryTruck(stock);
	}
	
	@Test
	public void testRTruckUnderCapacity() throws DeliveryException {
		stock.addItems(item1, 750);
		truck = new RefrigeratedTruck(stock, 0);
		assertEquals(stock.getItems(), truck.getCargo().getItems());
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckOverCapacity() throws DeliveryException {
		stock.addItems(item1, 751);
		truck = new RefrigeratedTruck(stock, 0);
	}
	
	@Test
	public void testRTruckGetTemperature() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 0);
		assertEquals(0, truck.getTemperature());
	}
	
	@Test
	public void testRTruckHighTemperature() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 30);
		assertEquals(30, truck.getTemperature());
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckTooHighTemperature() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 31);
	}
	
	@Test
	public void testRTruckLowTemperature() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, -30);
		assertEquals(-30, truck.getTemperature());
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckTooLowTemperature() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, -31);
	}
	
	@Test (expected = DeliveryException.class)
	public void testRTruckContainsLowerTemperature() throws DeliveryException {
		truck = new RefrigeratedTruck(stock, 2);
	}

	@Test
	public void testOTruckGetCost() {
		truck = new OrdinaryTruck(stock);
		assertEquals(762.5, truck.getCost(), DELTA);
	}
	
	@Test
	public void testRTruckGetCost1() {
		truck = new RefrigeratedTruck(stock, 0);
		assertEquals(1100.0, truck.getCost(), DELTA);
	}

	@Test
	public void testRTruckGetCost2() {
		truck = new RefrigeratedTruck(stock, -7);
		assertEquals(1229.5283165091, truck.getCost(), DELTA);
	}
}
