package store;

import java.util.Map.Entry;
import delivery.DeliveryException;
import delivery.Manifest;
import delivery.ManifestGenerator;
import delivery.Truck;
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
		return ManifestGenerator.generateManifest(inventory);
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
