package stock;

/**
 * Represents each individual item objects of the products in the store.
 * It includes product's name, cost of the product, sale price of the product, reorder quantity of the product, 
 * reorder amount of the product, and temperature for the items requiring refrigeration.
 * 
 * @author lara09
 *
 */
public class Item {
	
	private String name;
	private int cost;
	private int price;
	private int reorderPoint;
	private int reorderAmount;
	private Integer temperature;

	/**
	 * Constructs an item with all the Item parameters
	 * 
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param temperature
	 * @throws StockException if a temperature is less than -20 or more than 10 degrees.
	*/
	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount, Integer temperature) throws StockException {
		this(name, cost, price, reorderPoint, reorderAmount);

		if (temperature < -20 || temperature > 10 ) {
			throw new StockException();
		} else {
			this.temperature = temperature;
		}
	}	
	
	/**
	 * Constructs an item with the Item parameters, excluding a temperature.
	 * 
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderAmount
	 * @throws StockException if name does not exist
	 * @throws StockException if cost of the item is less than zero
	 * @throws StockException if item price is less or equal to zero
	 * @throws StockException if name does not exist
	 * @throws StockException if reorder point value is less than zero
	 * @throws StockException if reorder amount is less or equal of zero
	 * 
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount) throws StockException {
		if (name == "") {
			throw new StockException();
		} else {
			this.name = name;
		}
		
		if (cost < 0) {
			throw new StockException();
		} else {
			this.cost = cost;
		}
		
		if (price < 0) {
			throw new StockException();
		} else {
			this.price = price;
		}
		
		if (reorderPoint < 0) {
			throw new StockException();
		} else {
			this.reorderPoint = reorderPoint;
		}
		
		if (reorderAmount <= 0) {
			throw new StockException();
		} else {
			this.reorderAmount = reorderAmount;			
		}
		
	}

	/**
	 * Returns name of the item.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns cost of the item.
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Returns price of the item.
	 * 
	 * @return price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Returns reorder point of the item.
	 * 
	 * @return reorderPoint
	 */
	public int getReorderPoint() {
		return reorderPoint;
	}
	
	/**
	 * Returns reorder amount of the item.
	 * 
	 * @return reorderAmount
	 */
	public int getReorderAmount() {
		return reorderAmount;
	}
	
	/**
	 * Returns temperature of the item.
	 * 
	 * @return temperature
	 */
	public Integer getTemperature() {
		return temperature;
	}
	
}
