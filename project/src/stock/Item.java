package stock;

public class Item {

		
	private String name;
	private int cost;
	private int price;
	private int reorderPoint;
	private int reorderAmount;
	private Integer temperature;

	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount, Integer temperature) throws StockException {
		this(name, cost, price, reorderPoint, reorderAmount);

		if (temperature < -30 || temperature > 30 ) {
			throw new StockException();
		} 
		else 
		{
			this.temperature = temperature;
		}
	}	
	
	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount) throws StockException {
		if (name == "") {
			throw new StockException();
		} 
		else 
		{
			this.name = name;
		}
		if (cost < 0) {
			throw new StockException();
		} 
		else 
		{
			this.cost = cost;
		}
		if (price <= 0) {
			throw new StockException();
		} 
		else 
		{
			this.price = price;
		}
		
		if (reorderPoint < 0) {
			throw new StockException();
		} 
		else 
		{
			this.reorderPoint = reorderPoint;
		}
		
		//System.out.println(name + ", " + cost + ", " + price + ", " + reorderPoint + ", " + reorderAmount);
		if (reorderAmount <= 0) {
			throw new StockException();
		} 
		else 
		{
		this.reorderAmount = reorderAmount;			
		}
		
	}
	

	
	public String getName() {
	
		return name;
	}

	public int getCost() {
		
		return cost;
	}
	
	public int getPrice() {
		
		return price;
	}
	
	public int getReorderPoint() {
		
		return reorderPoint;
	}
	
	public int getReorderAmount() {
		
		return reorderAmount;
	}
	
	public Integer getTemperature() {
		
		return temperature;
	}
	
	
}
