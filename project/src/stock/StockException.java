package stock;

/**
 * Throwable StockException class 
 * These stock exceptions are used for exceptional conditions that program should catch.
 * 
 * @author lara09
 *
 */
public class StockException extends Exception {

	private static final long serialVersionUID = -9223045145821745107L;

	/**
	 * 
	 */
	public StockException() {}

	/**
	 * A super-class Stock Exception
	 * 
	 * @param arg0
	 */
	public StockException(String arg0) {
		super(arg0);
	}

}
