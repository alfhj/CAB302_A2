package delivery;

import stock.Item;
import stock.Stock;

/**
 * 
 * @author lara09
 *
 */
public abstract class Truck {
	private int capacity;
	private Stock cargo;
	
	public Truck(Stock cargo, int capacity) throws DeliveryException {
		this.cargo = cargo;
		this.capacity = capacity;
		
		if (cargo.getNumItems() > capacity)
		{
			throw new DeliveryException();
		}
	}

	public Stock getCargo() {
		return cargo;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public abstract double getCost();
		
}
