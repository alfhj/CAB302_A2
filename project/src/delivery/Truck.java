package delivery;

import stock.Stock;

/**
 * An abstract class Truck defines parameters for an Ordinary and
 * Refrigerated trucks using a Stock object.
 * 
 * @author lara09
 *
 */
public abstract class Truck {
	
	private int capacity;
	private Stock cargo;
	
	/**
	 * Constructs a Truck with the specified parameters of cargo and capacity.
	 * @throw Delivery exception if number of items in stock is large than a capacity of trucks 
	 */
	public Truck(Stock cargo, int capacity) throws DeliveryException {
		this.cargo = new Stock(cargo);
		this.capacity = capacity;
		
		if (cargo.getNumItems() > capacity) {
			throw new DeliveryException();
		}
	}

	/**
	 * 
	 * @return the cargo item from the Stock class 
	 */
	public Stock getCargo() {
		return new Stock(cargo);
	}

	/**
	 * 
	 * @return the capacity of the truck 
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Calculates the cost of the truck excluding its cargo
	 * 
	 * @return delivery cost of truck
	 */
	public abstract double getCost();

	/**
	 * 
	 * @return assign cargo item from a Stock class to a Truck class
	 */
	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			return cargo.equals(this.getClass().cast(o).getCargo());
		}
		return false;
	}
	
	/**
	 * 
	 * @return a hash code value of the cargo object
	 */
	@Override
	public int hashCode() {
		return cargo.hashCode();
	}
	
}
