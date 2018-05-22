package delivery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import stock.Item;

/**
 * 
 * @author lara09
 *
 */
public class Manifest {
	
	Set<Truck> fleet;
	
	public Manifest() {
		fleet = new HashSet<Truck>() ;
	}

	public void addTruck(Truck truck) {
		fleet.add(truck);
	}

	public Set<Truck> getFleet() {
		return Collections.unmodifiableSet(fleet);
	}

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
	
	public boolean equals(Manifest m) {
		return fleet.equals(m.getFleet());
	}
	
	@Override
	public int hashCode() {
		return fleet.hashCode();
	}
}
