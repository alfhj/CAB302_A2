package delivery;

import stock.Stock;

/**
 * 
 * @author lara09
 *
 */
public class OrdinaryTruck extends Truck {

	public OrdinaryTruck(Stock cargo) throws DeliveryException {
		super(cargo, 1000);
	}
	
	@Override
	public double getCost() {
		return 750.0 + 0.25 * getCargo().getNumItems();
	}
}