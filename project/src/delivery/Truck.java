package delivery;

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
	
	public boolean equals(Truck t) {
		return cargo.equals(t.getCargo()) 
		&& this.getClass().equals(t.getClass());
	}
	
	@Override
	public int hashCode() {
		return cargo.hashCode();
	}
	
}
