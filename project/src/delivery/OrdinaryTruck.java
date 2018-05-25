package delivery;

import stock.Stock;

/**
 * Constructs an ordinary truck by using in abstract class Truck parameter cargo
 * 
 * @author lara09
 *
 */
public class OrdinaryTruck extends Truck {

	/**
	 * 
	 * @param cargo
	 * @throws DeliveryException
	 */
	public OrdinaryTruck(Stock cargo) throws DeliveryException {
		super(cargo, 1000);
	}
	
	@Override
	public double getCost() {
		return 750.0 + 0.25 * getCargo().getNumItems();
	}
}