package stock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author alfhj
 *
 */
public class Stock {

	Map<Item, Integer> stock;
	
	public Stock() {
		stock = new HashMap<Item, Integer>();
	}
	
	public Map<Item, Integer> getStock() {
		return Collections.unmodifiableMap(stock);
	}

	public void addItems(Item item, int amount) throws StockException {
		if (amount < 0) throw new StockException("Cannot add a negative amount of items.");
		int oldAmount = 0;
		if (stock.containsKey(item)) oldAmount = stock.get(item);
		stock.put(item, oldAmount + amount);
	}

	public void removeItems(Item item, int amount) throws StockException {
		if (! stock.containsKey(item)) throw new StockException("Cannot remove an item that is not in stock.");
		if (amount < 0) throw new StockException("Cannot remove negative amounts.");
		int newAmount = stock.get(item) - amount;
		if (newAmount < 0) throw new StockException("Cannot remove more items than is available in stock.");
		stock.put(item, newAmount);
	}

}
