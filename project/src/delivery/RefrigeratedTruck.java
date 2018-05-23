package delivery;

import stock.Item;
import stock.Stock;

/**
 * 
 * @author lara09
 *
 */
public class RefrigeratedTruck extends Truck {
	
	private int temperature;
	
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
	
	public int getTemperature() {
		return temperature;
	}
	
	@Override
	public double getCost() {
		return 900.0 + 200.0 * Math.pow(0.7, (double) temperature / 5.0);
	}
}
