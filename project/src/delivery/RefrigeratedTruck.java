package delivery;

import java.util.Iterator;

import stock.Item;
import stock.Stock;

public class RefrigeratedTruck extends Truck {
	private Integer temperature;
	
	public RefrigeratedTruck(Stock cargo) throws DeliveryException {
		super(cargo, 800);
		
		Integer minTemp;
		Iterator<Item> iterator = cargo.getItems().keySet().iterator();
		minTemp = iterator.next().getTemperature();
		while (iterator.hasNext()) {
			Integer temp = iterator.next().getTemperature();
			if (temp != null) {
				if (minTemp == null) minTemp = temp;
				else if (temp < minTemp) minTemp = temp;
			}
		}
		
		if (minTemp == null) {
			this.temperature = 10;
		} else if (minTemp < -20 || minTemp > 10) {
			throw new DeliveryException();
		} else {
			this.temperature = minTemp;
		}

		
	}
	
	public int getTemperature()
	{
		return temperature;
	}
	
	@Override
	public double getCost()
	{
		return 900 + 200 * Math.pow(0.7, (double) temperature / 5);
	}
}
