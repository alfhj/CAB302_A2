package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManifestTest {

	private Manifest manifest;
	private Set<Truck> trucks;
	private final double DELTA = 1e-5;
	
	@Before @Test
	public void setUpManifest() throws Exception {
		Manifest = new Manifest();
		trucks = new Set<Truck>();
	}

	@Test
	public void testAddTruck() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("nuts", 5, 9, 125, 250);
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		manifest.addTruck(truck1);
		trucks.add(truck1);
		assertTrue(manifest.getFleet().equals(trucks));
	}
	
	@Test
	public void testAddTwoTrucks() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("nuts", 5, 9, 125, 250);
		Item item3 = new Item("tomatoes", 1, 2, 325, 400, 10);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		Stock stock2 = new Stock();
		stock2.addItems(item2, 10);
		stock2.addItems(item3, 20);
		Truck truck2 = new RefrigeratedTruck(stock1, 10);
		
		manifest.addTruck(truck1);
		manifest.addTruck(truck2);
		trucks.add(truck1);
		trucks.add(truck2);
		assertTrue(manifest.getFleet().equals(trucks));
	}
	
	@Test
	public void testGetCostSingle() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("nuts", 5, 9, 125, 250);
		Stock stock1 = new Stock();
		stock1.addItems(item1, 50);
		stock1.addItems(item2, 100);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		manifest.addTruck(truck1);
		assertEquals(1387.5, manifest.getTotalCost(), DELTA);
	}
	
	@Test
	public void testGetCostDouble() {
		Item item1 = new Item("rice", 2, 3, 225, 300);
		Item item2 = new Item("nuts", 5, 9, 125, 250);
		Item item3 = new Item("tomatoes", 1, 2, 325, 400, 10);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		Truck truck1 = new OrdinaryTruck(stock1);
		
		Stock stock2 = new Stock();
		stock2.addItems(item2, 10);
		stock2.addItems(item3, 20);
		Truck truck2 = new RefrigeratedTruck(stock1, 10);
		
		manifest.addTruck(truck1);
		manifest.addTruck(truck2);
		assertEquals(1945.5, manifest.getTotalCost(), DELTA);
	}
	
	@Test
	public void testGetCostEmptyOrdinary() {
		Stock stock1 = new Stock();
		Truck truck1 = new OrdinaryTruck(stock1);
		manifest.addTruck(truck1);
		assertEquals(750.0, manifest.getTotalCost(), DELTA);
	}
	
	@Test
	public void testGetCostEmptyRefrigerated() {
		Stock stock1 = new Stock();
		Truck truck1 = new RefrigeratedTruck(stock1, -5);
		manifest.addTruck(truck1);
		assertEquals(1185.7142857142, manifest.getTotalCost(), DELTA);
	}
}
