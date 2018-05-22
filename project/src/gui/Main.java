package gui;

import stock.*;

import java.util.Set;

import javax.swing.SwingUtilities;

import java.util.HashSet;

import delivery.*;


public class Main {

	public static void main(String[] args) throws StockException, DeliveryException {
		Item item1 = new Item("rice", 2, 3, 4, 5);
		Item item2 = new Item("milk", 2, 3, 4, 5, 3);
		
		Stock stock1 = new Stock();
		stock1.addItems(item1, 10);
		stock1.addItems(item2, 20);
		
		Stock stock2 = new Stock();
		stock2.addItems(item1, 10);
		stock2.addItems(item2, 20);
		
		Stock stock3 = new Stock();
		stock3.addItems(item1, 10);
		stock3.addItems(item2, 21);

		Truck truck1 = new OrdinaryTruck(stock1);
		Truck truck2 = new OrdinaryTruck(stock2);
		Truck truck3 = new OrdinaryTruck(stock3);
		
		Manifest manifest1 = new Manifest();
		manifest1.addTruck(truck1);
		manifest1.addTruck(truck3);
		
		Manifest manifest2 = new Manifest();
		manifest2.addTruck(truck1);
		manifest2.addTruck(truck3);
		
		Manifest manifest3 = new Manifest();
		manifest3.addTruck(truck1); 
		
		Set<Truck> set1 = new HashSet<Truck>();
		set1.add(truck1);
		set1.add(truck3);
		Set<Truck> set2 = new HashSet<Truck>();
		set2.add(truck1);
		set2.add(truck3);
		
		SwingUtilities.invokeLater(new StoreWindow());
	}
	
	

}
