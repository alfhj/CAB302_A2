package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import stock.*;
import delivery.*;
import store.*;

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
	
	public static Stock readItemProperties(File file) throws IOException, CSVFormatException, StockException {
		String input = readCSV(file);
		Stock stock = new Stock();
		for (String line: input.split("\n")) {
			String[] fields = line.split(",");
			String name = fields[0];
			int cost = Integer.parseInt(fields[1]);
			int price = Integer.parseInt(fields[2]);
			int repoint = Integer.parseInt(fields[3]);
			int reamount = Integer.parseInt(fields[4]);
			
			if (fields.length == 5) {
				Item item = new Item(name, cost, price, repoint, reamount);
				stock.addItems(item, 0);
			} else if (fields.length == 6) {
				int temp = Integer.parseInt(fields[5]);
				Item item = new Item(name, cost, price, repoint, reamount, temp);
				stock.addItems(item, 0);
			} else {
				throw new CSVFormatException("Item property is of invalid length");
			}
		}
		return stock;
	}
	
	public static Stock readSalesLog(File file) throws IOException, CSVFormatException, StockException {
		String input = readCSV(file);
		Stock stock = new Stock();
		for (String line: input.split("\n")) {
			String[] fields = line.split(",");
			String name = fields[0];
			int amount = Integer.parseInt(fields[1]);
			Item item = Store.getInstance().getInventory().searchItem(name).getKey();
			stock.addItems(item, amount);
		}
		return stock;
	}
	
	public static Manifest readManifest(File file) throws IOException, CSVFormatException, DeliveryException, StockException {
		String input = readCSV(file);
		Manifest manifest = new Manifest();
		Truck currentTruck = null;
		for (String line: input.split("\n")) {
			if (line.charAt(0) == '>') {
				if (currentTruck != null) manifest.addTruck(currentTruck);
				if (line.substring(1).equals("Ordinary")) {
					currentTruck = new OrdinaryTruck(new Stock());
				} else if (line.substring(1).equals("Refrigerated")) {
					currentTruck = new RefrigeratedTruck(new Stock());
				} else {
					throw new CSVFormatException("Expected Ordinary or Refrigerated");
				}
				continue;
			} else {
				if (currentTruck == null) {
					throw new CSVFormatException("Expected truck type at top of manifest");
				}
				String[] fields = line.split(",");
				String name = fields[0];
				int amount = Integer.parseInt(fields[1]);
				Item item = Store.getInstance().getInventory().searchItem(name).getKey();
				currentTruck.getCargo().addItems(item, amount);
			}
		}
		if (currentTruck != null) manifest.addTruck(currentTruck);
		
		return manifest;
	}
	
	public static void writeManifest(File file, Manifest manifest) throws IOException {
		String output = "";
		for (Truck truck: manifest.getFleet()) {
			if (truck instanceof OrdinaryTruck) {
				output += ">Ordinary\r\n";
			} else if (truck instanceof RefrigeratedTruck) {
				output += ">Refrigerated\r\n";
			}
			for (Entry<Item, Integer> entry: truck.getCargo().getItems().entrySet()) {
				output += entry.getKey().getName() + "," + entry.getValue() + "\r\n";
			}
		}
		writeCSV(file, output);
	}
	
}
