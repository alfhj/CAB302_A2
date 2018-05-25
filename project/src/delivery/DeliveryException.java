package delivery;

/**
 * Throwable DeliveryException class 
 * These delivery exceptions are used for exceptional truck, manifest and manifest generator conditions that program should catch.
 *
 * @author lara09
 *
 */
public class DeliveryException extends Exception {

	private static final long serialVersionUID = -5855216767087298707L;

	/**
	 * A DeliveryException without a message
	 */
	public DeliveryException() {}

	/**
	 * A DeliveryException with a message
	 * 
	 * @param message- the message relating to the exception
	 */
	public DeliveryException(String message) {
		super(message);
	}

}
