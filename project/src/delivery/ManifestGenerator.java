package delivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;

import stock.Item;
import stock.Stock;
import stock.StockException;

public class ManifestGenerator {

	public static Manifest generateManifest(Stock stock) throws DeliveryException, StockException {
		// create list of entries
		ArrayList<Entry<Item, Integer>> reorderStock = new ArrayList<Entry<Item, Integer>>(stock.getItems().entrySet());
		
		// sort list by item temperature
		reorderStock.sort(new Comparator<Entry<Item, Integer>>() {
			@Override
			public int compare(Entry<Item, Integer> entry1, Entry<Item, Integer> entry2) {
				Integer temp1 = entry1.getKey().getTemperature();
				Integer temp2 = entry2.getKey().getTemperature();
				if (temp1 == null) return 1;
				if (temp2 == null) return -1;
				return temp1.compareTo(temp2);
			}
		});
		
		// makes manifest by successively filling up trucks with the lowest temperature items possible
		Manifest manifest = new Manifest();
		Stock currentStock = new Stock();
		Boolean isRefrigerated = null;
		int refrigeratedCapacity = new RefrigeratedTruck(new Stock()).getCapacity();
		int ordinaryCapacity = new OrdinaryTruck(new Stock()).getCapacity();
		for (Entry<Item, Integer> entry: reorderStock) {
			Item item = entry.getKey();
			int stockAmount = entry.getValue();
			if (stockAmount > item.getReorderPoint()) continue;
			int amount = item.getReorderAmount();
			
			if (isRefrigerated == null) isRefrigerated = item.getTemperature() != null;
			int truckSpace = isRefrigerated ? refrigeratedCapacity : ordinaryCapacity;
			int availSpace = truckSpace - currentStock.getNumItems();
			if (amount > availSpace) {
				currentStock.addItems(item, availSpace);
				manifest.addTruck(TruckFactory.getTruck(currentStock));
				
				currentStock = new Stock();
				currentStock.addItems(item, amount - availSpace);
				isRefrigerated = item.getTemperature() != null;
			} else {
				currentStock.addItems(item, amount);
			}
		}
		manifest.addTruck(TruckFactory.getTruck(currentStock));
		
		return manifest;
	}
	
}
