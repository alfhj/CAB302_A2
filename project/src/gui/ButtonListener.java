package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import csv.CSVFormatException;
import csv.CSVHandler;
import delivery.DeliveryException;
import stock.StockException;
import store.Store;
import store.StoreException;

public class ButtonListener1 implements ActionListener {
	private Store store = Store.getInstance();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//File currentDirectory = new File(System.getProperty("user.dir"));
		File currentDirectory = new File("C:\\Users\\alfer\\git\\CAB302_A2\\csv");
		JFileChooser fileChooser = new JFileChooser(currentDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(filter);
		
		int returnValue = -1;
		switch(e.getActionCommand()) {
		case "loadprop": returnValue = fileChooser.showOpenDialog(null); break;
		case "impsales": returnValue = fileChooser.showOpenDialog(null); break;
		case "impmani": returnValue = fileChooser.showOpenDialog(null); break;
		case "expmani": returnValue = fileChooser.showSaveDialog(null); break;
		}
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			processFile(file, e.getActionCommand());
		}
	}
	
	private void processFile(File file, String action) {
		try {
			switch(action) {
			case "loadprop": store.loadInventory(CSVHandler.readItemProperties(file)); break;
			case "impsales": store.importSalesLog(CSVHandler.readSalesLog(file)); break;
			case "impmani": store.importManifest(CSVHandler.readManifest(file)); break;
			case "expmani": CSVHandler.writeManifest(file, store.exportManifest()); break;
			}
		} catch (StockException e1) {
			e1.printStackTrace();
		} catch (StoreException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CSVFormatException e1) {
			e1.printStackTrace();
		} catch (DeliveryException e1) {
			e1.printStackTrace();
		}
	}
		
}
