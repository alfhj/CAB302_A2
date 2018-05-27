package store;

import java.util.Map.Entry;
import delivery.DeliveryException;
import delivery.Manifest;
import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * A class for the store.
 * The store's name and starting capital must be set in the source code.
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
	
	/**
	 * Returns the instance of the store by the singleton pattern
	 * 
	 * @return the instance of the store
	 */
	public static Store getInstance() {
		return StoreHelper.INSTANCE;
	}
	
	/**
	 * Resets the capital and inventory to their default values.
	 * Used for unit testing purposes.
	 */
	public void reset() {
		capital = STARTING_CAPITAL;
		inventory = new Stock();
	}

	/**
	 * Returns the store's name
	 * 
	 * @return name
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Returns the store's current capital in dollars.
	 * 
	 * @return current capital
	 */
	public double getCapital() {
		return capital;
	}

	/**
	 * Returns the store's current inventory.
	 *  
	 * @return current inventory
	 */
	public Stock getInventory() {
		return new Stock(inventory);
	}

	/**
	 * Initialises the store's inventory to the specified Stock.
	 * The amounts in the stock is ignored and set to zero.
	 * 
	 * @param stock the initial stock
	 * @throws StockException via Stock
	 */
	public void loadInventory(Stock stock) throws StockException {
		inventory = new Stock();
		for (Item item: stock.getItems().keySet()) {
			inventory.addItems(item, 0);
		}
	}
	
	/**
	 * Generates a shipping manifest for the store's items that needs reordering.
	 * 
	 * @return the generated manifest
	 * @throws StockException via ManifestGenerator
	 * @throws DeliveryException via ManifestGenerator
	 * @throws StoreException if inventory is empty
	 * @throws StoreException if no items needs reordering
	 */
	public Manifest exportManifest() throws StockException, DeliveryException, StoreException {
		if (inventory.getItems().isEmpty()) throw new StoreException("No items to reorder");
		
		Stock reorderStock = new Stock();
		for (Entry<Item, Integer> entry: inventory.getItems().entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			if (amount <= item.getReorderPoint()) {
				reorderStock.addItems(item, item.getReorderAmount());
			}
		}
		if (reorderStock.getItems().isEmpty()) throw new StoreException("No items needs reordering");
		return new Manifest(reorderStock);
	}

	/**
	 * Imports a manifest into the store.
	 * This is imported when the store receives a delivery from a distribution centre.
	 * Results in increased stock and reduced capital.
	 * 
	 * @param manifest the manifest to import
	 * @throws StockException via Stock
	 * @throws StoreException if manifest is empty
	 * @throws StoreException if manifest contains item not found in store's inventory
	 */
	public void importManifest(Manifest manifest) throws StockException, StoreException {
		Stock totalCargo = manifest.getTotalCargo();
		
		if (totalCargo.getItems().isEmpty()) throw new StoreException("Manifest is empty");
		
		for (Entry<Item, Integer> entry: totalCargo.getItems().entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			
			if (! inventory.getItems().containsKey(item)) {
				throw new StoreException("Item \"" + item.getName() + "\" is in manifest but not in store");
			}
			
			inventory.addItems(item, amount);
		}
		
		capital -= manifest.getTotalCost();
	}

	/**
	 * Imports a sales log in a Stock format.
	 * This is imported at the end of each week.
	 * Results in decreased inventory and increased capital.
	 * 
	 * @param sold the total Stock of sold items 
	 * @throws StoreException if an item's amount goes below 0.
	 */
	public void importSalesLog(Stock sold) throws StoreException {
		for (Entry<Item, Integer> entry: sold.getItems().entrySet()) {
			Item item = entry.getKey();
			int amount = entry.getValue();
			
			capital += item.getPrice() * amount;
			
			try {
				inventory.removeItems(item, amount);
			} catch (StockException e) {
				throw new StoreException("Not enough of item \"" + item.getName() + "\"");
			}
		}
	}

}
