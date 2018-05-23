package store;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import delivery.DeliveryException;
import delivery.Manifest;
import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import delivery.TruckFactory;
import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 *
 * author: alfhj
 *
 */
public class Store {
	private static final String NAME = "Norwegian Delight";
	private static final double STARTING_CAPITAL = 100000.0;
	
	private double capital = STARTING_CAPITAL;
	private Stock inventory = new Stock();
	
	private Store() {}

	private static class StoreHelper {
		private static final Store INSTANCE = new Store();
	}
	
	public static Store getInstance() {
		return StoreHelper.INSTANCE;
	}
	
	public void reset() {
		capital = STARTING_CAPITAL;
		inventory = new Stock();
	}

	public void loadInventory(Stock stock) throws StockException {
		inventory = new Stock();
		for (Item item: stock.getItems().keySet()) {
			inventory.addItems(item, 0);
		}
	}

	public String getName() {
		return NAME;
	}

	public double getCapital() {
		return capital;
	}

	public Stock getInventory() {
		return inventory;
	}

	public Manifest exportManifest() throws StockException, DeliveryException {
		// creates treemap which sorts by temperature
		SortedMap<Item, Integer> reorderStock = new TreeMap<Item, Integer>(new Comparator<Item>() {
			@Override
			public int compare(Item item1, Item item2) {
				Integer temp1 = item1.getTemperature();
				Integer temp2 = item2.getTemperature();
				if (temp1 == null) return 1;
				if (temp2 == null) return -1;
				return Integer.compare(temp1, temp2);
			}
		});
		
		// puts items that need reordering into treemap
		for (Entry<Item, Integer> entry: inventory.getItems().entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			if (amount <= item.getReorderPoint()) {
				reorderStock.put(item, item.getReorderAmount());
			}
		}
		
		// makes manifest
		Manifest manifest = new Manifest();
		Truck currentTruck = null;
		for (Entry<Item, Integer> entry: reorderStock.entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			
			if (currentTruck == null) {
				currentTruck = TruckFactory.getTruck(item.getTemperature());
			}
			
			int availSpace = currentTruck.getCapacity() - currentTruck.getCargo().getNumItems();
			if (amount > availSpace) {
				currentTruck.getCargo().addItems(item, availSpace);
				manifest.addTruck(currentTruck);
				
				currentTruck = TruckFactory.getTruck(item.getTemperature());
				currentTruck.getCargo().addItems(item, amount - availSpace);
			} else {
				currentTruck.getCargo().addItems(item, amount);
			}
		}
		manifest.addTruck(currentTruck);
		
		return manifest;
	}

	public void importManifest(Manifest manifest) throws StockException, StoreException {
		Stock totalCargo = new Stock();
		
		for (Truck truck: manifest.getFleet()) {
			for (Entry<Item, Integer> entry: truck.getCargo().getItems().entrySet()) {
				Item item = entry.getKey();
				int amount = entry.getValue();
				
				if (! inventory.getItems().containsKey(item)) {
					throw new StoreException("Item is in manifest but not in store");
				}
				if (inventory.getItems().get(item) > item.getReorderPoint()) {
					throw new StoreException("Item quantity is already above reorder point");
				}
				
				totalCargo.addItems(item, amount);
			}
		}
		
		for (Entry<Item, Integer> entry: totalCargo.getItems().entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			
			if (amount != item.getReorderAmount()) { 
				throw new StoreException("Amount in cargo is not reorder-amount");
			}
			
			inventory.addItems(item, amount);
		}
		
		capital -= manifest.getTotalCost();
		if (capital < 0) throw new StoreException("Congratulations, you are bankrupt");
	}

	public void importSalesLog(Stock sold) throws StoreException {
		for (Entry<Item, Integer> entry: sold.getItems().entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			
			capital += item.getPrice() * amount;
			
			try {
				inventory.removeItems(item, amount);
			} catch (StockException e) {
				throw new StoreException();
			}
		}
	}


}
