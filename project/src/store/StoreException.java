package store;

/**
 * Throwable StoreException class 
 * These store exceptions are used for exceptional store conditions that program should catch.
 * 
 * @author lara09
 *
 */
public class StoreException extends Exception {

	private static final long serialVersionUID = -5757121501928985012L;

	/**
	 * 
	 * A Store Exception without a message
	 */
	 
	public StoreException() {
		
	}

	/**
	 * A Store Exception with a message
	 * @param  arg0 the message relating to the exception
	 */
	
	public StoreException(String arg0) {
		super(arg0);
	}
	
}


