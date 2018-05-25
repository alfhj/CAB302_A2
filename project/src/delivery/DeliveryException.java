package delivery;

/**
 * Throwable DeliveryException class 
 * These delivery exceptions are used for exceptional conditions that program should catch.
 *
 * @author lara09
 *
 */
public class DeliveryException extends Exception {

	private static final long serialVersionUID = -5855216767087298707L;

	/**
	 * 
	 */
	public DeliveryException() {}

	/**
	 * A super class DeliveryException
	 * 
	 * @param message
	 */
	public DeliveryException(String message) {
		super(message);
	}

}
