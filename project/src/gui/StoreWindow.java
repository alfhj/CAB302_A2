package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

import csv.CSVFormatException;
import csv.CSVHandler;
import delivery.DeliveryException;
import stock.StockException;
import store.Store;
import store.StoreException;

/**
 * 
 * @author alfhj
 *
 */
public class StoreWindow implements Runnable, ActionListener {
	/*
 ____________________________________________________________
|          [store name] - Inventory management      [-][O][X]|
|____________________________________________________________|
|                                                            |
| Inventory:                           Capital: $100,000.00  |
|  ________________________________________________________  |
| |____Name___|Amount|Cost|Price|Re.point|Re.amount|Temp.|^| |
| |___rice____|__200_|__2_|__3__|___225__|___300___|_____|X| |
| |___beans___|__200_|__4_|__6__|___450__|___525___|_____|X| |
| |___pasta___|__200_|__3_|__4__|___125__|___250___|_____|X| |
| |_biscuits__|__200_|__2_|__5__|___350__|___575___|_____|X| |
| |___nuts____|__200_|__5_|__9__|___125__|___250___|_____|X| |
| |___chips___|__200_|__2_|__4__|___125__|___220___|_____| | |
| |_chocolate_|__200_|__5_|__5__|___250__|___375___|_____| | |
| |___bread___|__200_|__2_|__3__|___125__|___200___|_____| | |
| |_mushrooms_|__200_|__2_|__2__|___200__|___325___|__10_| | |
| |__tomatoes_|__200_|__1_|__2__|___325__|___400___|__10_|v| |
|  ______________________   __________________               |
| | Load item properties | | Import sales log |              |
| |______________________| |__________________|              |
|  _________________   _________________                     |
| | Export manifest | | Import manifest |                    |
| |_________________| |_________________|                    |
|____________________________________________________________|
	 */
	private Store store = Store.getInstance();
	JLabel labCap;
	JTable table;
	JButton btnProp;
	JButton btnSales;
	JButton btnExpMani;
	JButton btnImpMani;
	
	public StoreWindow() throws HeadlessException {}
	
	private void setUp() {
		
		// make the top level container
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame(store.getName() + " - Inventory management");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// make the intermediate components
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		JScrollPane scrollPane = new JScrollPane();
		
		// make the atomic components
		Font font = new Font("sans", Font.BOLD, 16);
		JLabel labInv = new JLabel("Inventory:");
		labInv.setFont(font);
		labCap = new JLabel();
		labCap.setFont(font);
		labCap.setHorizontalAlignment(SwingConstants.RIGHT);
		updateCapital();

		table = new JTable(new StoreTableModel());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(new ReorderLabelRenderer());
		table.getColumnModel().getColumn(0).setPreferredWidth(170);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(50);
		updateTable();
		
		btnProp = new JButton("Load item properties");
		btnSales = new JButton("Import sales log");
		btnExpMani = new JButton("Export manifest");
		btnImpMani = new JButton("Import manifest");
		btnProp.setActionCommand("loadprop");
		btnSales.setActionCommand("impsales");
		btnExpMani.setActionCommand("expmani");
		btnImpMani.setActionCommand("impmani");
		btnProp.addActionListener(new ButtonListener());
		btnSales.addActionListener(new ButtonListener());
		btnExpMani.addActionListener(new ButtonListener());
		btnImpMani.addActionListener(new ButtonListener());

		// make gridbag constraints and set defaults
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		 
		addToPanel(panel, labInv, constraints, 0, 0, 1, 1);
		addToPanel(panel, labCap, constraints, 1, 0, 1, 1);
		
		constraints.weighty = 20;
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		addToPanel(panel, scrollPane, constraints, 0, 1, 2, 1);
		
		constraints.weighty = 1;
		addToPanel(panel, btnProp, constraints, 0, 2, 1, 1);
		addToPanel(panel, btnSales, constraints, 1, 2, 1, 1);
		addToPanel(panel, btnExpMani, constraints, 0, 3, 1, 1);
		addToPanel(panel, btnImpMani, constraints, 1, 3, 1, 1);
		
		//panel.setBackground(new Color(new Random().nextInt()));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		frame.getContentPane().add(panel);
		frame.repaint();
		frame.setVisible(true);
	}
	
	private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		jp.add(c, constraints);
	}
	
	private void updateCapital() {
		String capitalFormatted = String.format("%,.2f", store.getCapital());
		labCap.setText("Capital: $" + capitalFormatted);
	}
	
	private void updateTable() {
		((StoreTableModel) table.getModel()).fireTableDataChanged();
	}
	/*
	private void processFile(File file, String action) {
		try {
			switch(action) {
			case "loadprop":
				store.loadInventory(CSVHandler.readItemProperties(file));
				updateTable();
				break;
			case "impsales":
				store.importSalesLog(CSVHandler.readSalesLog(file));
				updateCapital();
				updateTable();
				break;
			case "impmani":
				store.importManifest(CSVHandler.readManifest(file));
				updateCapital();
				updateTable();
				break;
			case "expmani":
				CSVHandler.writeManifest(file, store.exportManifest());
				break;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == btnProp || src == btnSales || src == btnImpMani || src == btnExpMani) {
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
		
	}
	*/
	public class ButtonListener implements ActionListener {
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
				case "loadprop":
					store.loadInventory(CSVHandler.readItemProperties(file));
					updateTable();
					break;
				case "impsales":
					store.importSalesLog(CSVHandler.readSalesLog(file));
					updateCapital();
					updateTable();
					break;
				case "impmani":
					store.importManifest(CSVHandler.readManifest(file));
					updateCapital();
					updateTable();
					break;
				case "expmani":
					CSVHandler.writeManifest(file, store.exportManifest());
					break;
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
	
	@Override
	public void run() {
		setUp();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
