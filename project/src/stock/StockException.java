package stock;

/**
 * Throwable StockException class 
 * These stock exceptions are used for exceptional item and stock conditions that program should catch.
 * 
 * @author lara09
 *
 */
public class StockException extends Exception {

	private static final long serialVersionUID = -9223045145821745107L;

	/**
	 * A StockException without a message
	 */
	public StockException() {}

	/**
	 * A StockException with a message
	 * 
	 * @param arg0 the message relating to the exception
	 */
	public StockException(String arg0) {
		super(arg0);
	}

}
