package delivery;

import stock.Item;
import stock.Stock;

public class TruckFactory {
	
	public static Truck getTruck(Stock cargo) throws DeliveryException {
		for (Item item: cargo.getItems().keySet()) {
			if (item.getTemperature() != null) return new RefrigeratedTruck(cargo);	
		}
		return new OrdinaryTruck(cargo);
	}
	
}
