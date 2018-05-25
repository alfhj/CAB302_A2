package delivery;

import stock.Item;
import stock.Stock;

/**
 * Constructs a refrigerated truck by using in abstract class Truck parameter cargo
 * 
 * @author lara09
 *
 */
public class RefrigeratedTruck extends Truck {
	
	private int temperature;
	
	/**
	 * A constructor calculates a temperature of the truck based on the minimum temperature of the cargo
	 * 
	 * @param cargo
	 * @throws DeliveryException if the minimum temperature for truck cannot be assigned to the truck
	 */
	public RefrigeratedTruck(Stock cargo) throws DeliveryException {
		super(cargo, 800);
		
		int minTemp = 10;
		for (Item item: cargo.getItems().keySet()) {
			Integer itemTemp = item.getTemperature();
			if (itemTemp != null && itemTemp < minTemp) {
				minTemp = itemTemp;
			}
		}
		
		this.temperature = minTemp;
	}
	
	/**
	 * 
	 * @return temperature of the truck
	 */
	public int getTemperature() {
		return temperature;
	}
	
	@Override
	public double getCost() {
		return 900.0 + 200.0 * Math.pow(0.7, (double) temperature / 5.0);
	}
}
