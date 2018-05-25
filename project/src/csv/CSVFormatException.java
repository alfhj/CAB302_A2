package csv;

/**
 * Throwable CSVFormatException class 
 * These CSVFormat exceptions are used for exceptional conditions that program should catch.
 * 
 * @author lara09
 *
 */
public class CSVFormatException extends Exception {
	
	private static final long serialVersionUID = 3007998190541244414L;

	/**
	 * 
	 */
	public CSVFormatException() {}
	
	/**
	 * A super class CSVFormatException
	 * 
	 * @param message
	 */
	public CSVFormatException(String message) {
		super(message);
	}

}
