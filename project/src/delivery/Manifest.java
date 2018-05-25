package delivery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import stock.Item;

/**
 * Represents a set of Truck objects.
 * It uses a HashSet as a data structure to contain truck and a cargo inside these trucks.
 * The HashSet is of type Set&lt;Truck&gt;.
 * @author lara09
 *
 */
public class Manifest {
	
	Set<Truck> fleet;
	
	/**
	 * Constructs an empty fleet and initializes the HashSet.
	 */
	public Manifest() {
		fleet = new HashSet<Truck>() ;
	}

	/**
	 * Adds the specified truck to the fleet.
	 * 
	 * @param truck
	 */
	public void addTruck(Truck truck) {
		fleet.add(truck);
	}

	/**
	 * Returns an unmodifiable version of the Set representing the trucks.
	 * It is unmodifiable so that the contents will not be overwritten
	 * by direct access to the set (put, remove, replace).
	 * 
	 * @return an unmodifiable Set&lt;Truck&gt;
	 */
	public Set<Truck> getFleet() {
		return Collections.unmodifiableSet(fleet);
	}

	/**
	 * Calculates a total cost of each truck with cargo, writes item and amount into a Set for each truck 
	 * Returns the total number of items in the stock
	 * 
	 * @return total cost for the truck and the cargo
	 */
	public double getTotalCost() {
		double totalCost = 0.0;
		for (Truck truck: fleet) {
			totalCost += truck.getCost();
			for (Entry<Item, Integer> entry: truck.getCargo().getItems().entrySet()) {
				Item item = entry.getKey();
				Integer amount = entry.getValue();
				totalCost += amount * item.getCost();
			}
		}
		return totalCost;
	}
	
	/**
	 * 
	 * @return assigns cargo item from a Truck class to a Manifest class
	 */
	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			return fleet.equals(this.getClass().cast(o).getFleet());
		}
		return false;
	}
	
	/**
	 * 
	 * @return hashCode of the fleet items
	 */
	@Override
	public int hashCode() {
		return fleet.hashCode();
	}
}
