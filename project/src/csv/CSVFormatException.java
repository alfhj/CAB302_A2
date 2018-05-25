package csv;

/**
 * Throwable CSVFormatException class 
 * These CSVFormat exceptions, related to reading and writing CSV files, are used for exceptional CSV handler conditions that program should catch.
 * 
 * @author lara09
 *
 */
public class CSVFormatException extends Exception {
	
	private static final long serialVersionUID = 3007998190541244414L;

	/**
	 * A CSVFormatException without a message
	 */
	public CSVFormatException() {}
	
	/**
	 * A CSVFormatException with a message
	 * 
	 * @param message- the message relating to the exception
	 */
	public CSVFormatException(String message) {
		super(message);
	}

}
