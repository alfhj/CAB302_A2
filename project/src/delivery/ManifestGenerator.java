package delivery;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import stock.Item;
import stock.Stock;
import stock.StockException;

public class ManifestGenerator {

	public static Manifest generateManifest(Stock stock) throws DeliveryException, StockException {
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
		for (Entry<Item, Integer> entry: stock.getItems().entrySet()) {
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
}