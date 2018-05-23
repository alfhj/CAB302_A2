package delivery;

import stock.Stock;

public class TruckFactory {
	
	public static Truck getTruck(Integer temperature) throws DeliveryException {
		if (temperature == null) {
			return new OrdinaryTruck(new Stock());
		} else {
			return new RefrigeratedTruck(new Stock());				
		}
	}
	
}
