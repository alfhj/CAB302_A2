package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author alfhj
 *
 */
public final class CSVHandler {
	private CSVHandler() {}
	
	public static String readCSV(File file) throws IOException {
		FileReader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String output = "";
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			output += line + "\n";
		}
		bufferedReader.close();
		return output;
	}
	
	public static void writeCSV(File file, String output) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(output);
		writer.close();
	}
	
}
