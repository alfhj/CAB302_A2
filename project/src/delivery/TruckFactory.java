package delivery;

import stock.Item;
import stock.Stock;

/**
 * Makes a RefrigeratedTruck or OrdinaryTruck truck based on a supplied cargo.
 * 
 * @author alfhj
 * 
 */
public class TruckFactory {
	
	/**
	 * Returns a new Truck with the specified cargo.
	 * If an item in the cargo has a parameter of temperature, it gets allocated to a refrigerated truck.
	 * If there is no value for the temperature, it gets allocated to an ordinary truck.
	 * 
	 * @param cargo the cargo of the new truck
	 * @return OrdinaryTruck for the stock if stock doesn't contain item with temperature;
	 * @return RefrigiratedTruck for the stock if stock contains item with temperature;
	 * @throws DeliveryException if cargo is invalid
	 */
	public static Truck getTruck(Stock cargo) throws DeliveryException {
		for (Item item: cargo.getItems().keySet()) {
			if (item.getTemperature() != null) return new RefrigeratedTruck(cargo);	
		}
		return new OrdinaryTruck(cargo);
	}
	
}
