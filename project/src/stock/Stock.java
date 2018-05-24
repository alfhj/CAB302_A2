package stock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Stock class represents a collection of Item objects.
 * It uses a HashMap as a data structure to contain the amounts of each Item.
 * The HashMap is of type Map&lt;Item, Integer&gt;.
 * 
 * @author alfhj
 *
 */
public class Stock {

	Map<Item, Integer> items;
	
	/**
	 * Constructs an empty Stock and initializes the HashMap.
	 */
	public Stock() {
		items = new HashMap<Item, Integer>();
	}

	/**
	 * Constructs a copy of an existing Stock.
	 * This is to protect stocks that should not be modifiable directly
	 */
	public Stock(Stock stock) {
		items = new HashMap<Item, Integer>(stock.getItems());
	}
	
	/**
	 * Returns an unmodifiable version of the Map representing the items.
	 * It is unmodifiable so that the contents will not be overwritten
	 * by direct access to the map (put, remove, replace).
	 * 
	 * @return an unmodifiable Map&lt;Item, Integer&gt;
	 */
	public Map<Item, Integer> getItems() {
		return Collections.unmodifiableMap(items);
	}

	/**
	 * Adds the specified item to the stock so that the amount is updated.
	 * 
	 * @param item the item to be added
	 * @param amount the amount of items to be added, must be 0 or greater
	 * @throws StockException if amount is negative
	 */
	public void addItems(Item item, int amount) throws StockException {
		if (amount < 0) throw new StockException("Cannot add a negative amount of items.");
		int oldAmount = 0;
		if (items.containsKey(item)) oldAmount = items.get(item);
		items.put(item, oldAmount + amount);
	}

	/**
	 * Removes the specified item from the stock so that the amount updated.
	 * 
	 * @param item the item to be removed
	 * @param amount the amount of items to be removed, must be 0 or greater
	 * @throws StockException if the item is not found in the stock, if the amount is negative, or if the updated amount is negative
	 */
	public void removeItems(Item item, int amount) throws StockException {
		if (! items.containsKey(item)) throw new StockException("Cannot remove an item that is not in stock.");
		if (amount < 0) throw new StockException("Cannot remove negative amounts.");
		int newAmount = items.get(item) - amount;
		if (newAmount < 0) throw new StockException("Cannot remove more items than is available in stock.");
		items.put(item, newAmount);
	}
	
	/**
	 * Searches for an item in the stock by an item's name.
	 * 
	 * @param str the string to be search representing the item's name
	 * @return the Entry&lt;Item, Integer&gt; for the searched item
	 * @throws StockException if the item is not found
	 */
	public Entry<Item, Integer> searchItem(String str) throws StockException {
		for (Entry<Item, Integer> entry: items.entrySet()) {
			if (entry.getKey().getName().equals(str)) {
				return entry;
			}
		}
		throw new StockException("Item not found in stock");
	}
	
	/**
	 * Returns the total number of items in the stock
	 * 
	 * @return total item amount
	 */
	public int getNumItems() {
		int sum = 0;
		for (int amount: items.values()) {
			sum += amount;
		}
		return sum;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			return items.equals(this.getClass().cast(o).getItems());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return items.hashCode();
	}

}
