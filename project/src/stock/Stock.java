package stock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author alfhj
 *
 */
public class Stock {

	Map<Item, Integer> items;
	
	public Stock() {
		items = new HashMap<Item, Integer>();
	}
	
	public Map<Item, Integer> getItems() {
		return Collections.unmodifiableMap(items);
	}

	public void addItems(Item item, int amount) throws StockException {
		if (amount < 0) throw new StockException("Cannot add a negative amount of items.");
		int oldAmount = 0;
		if (items.containsKey(item)) oldAmount = items.get(item);
		items.put(item, oldAmount + amount);
	}

	public void removeItems(Item item, int amount) throws StockException {
		if (! items.containsKey(item)) throw new StockException("Cannot remove an item that is not in stock.");
		if (amount < 0) throw new StockException("Cannot remove negative amounts.");
		int newAmount = items.get(item) - amount;
		if (newAmount < 0) throw new StockException("Cannot remove more items than is available in stock.");
		items.put(item, newAmount);
	}
	
	public Entry<Item, Integer> findItem(String str) throws StockException {
		for (Entry<Item, Integer> entry: items.entrySet()) {
			if (entry.getKey().getName().equals(str)) {
				return entry;
			}
		}
		throw new StockException("Item not found in stock");
	}
	
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
			return items.equals(((Stock) o).getItems());
		}
		return o.equals(this);
	}
	
	@Override
	public int hashCode() {
		return items.hashCode();
	}

}
