package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class StoreWindow implements ActionListener, Runnable {
	/*
 ____________________________________________________________
|          [store name] - Inventory management      [-][O][X]|
|____________________________________________________________|
|                                                            |
| Inventory:                           Capital: $100'000.00  |
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
	
	public StoreWindow() throws HeadlessException {}
	
	private void setUp() {
		// make the top level container
		JFrame.setDefaultLookAndFeelDecorated(true);
		//Store store = Store.getInstance();
		//frame = new JFrame(store.getName());
		JFrame frame;
		frame = new JFrame("[store name]" + " - Inventory management");
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
		JLabel labCap = new JLabel("Capital: $100'000.00", SwingConstants.RIGHT);
		labCap.setFont(font);
		JTable table = new JTable();
		JButton btn1 = new JButton("Load item properties");
		JButton btn2 = new JButton("Import sales log");
		JButton btn3 = new JButton("Export manifest");
		JButton btn4 = new JButton("Import manifest");

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
		addToPanel(panel, scrollPane, constraints, 0, 1, 2, 1);
		constraints.weighty = 1;
		addToPanel(panel, btn1, constraints, 0, 2, 1, 1);
		addToPanel(panel, btn2, constraints, 1, 2, 1, 1);
		addToPanel(panel, btn3, constraints, 0, 3, 1, 1);
		addToPanel(panel, btn4, constraints, 1, 3, 1, 1);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
	}

	@Override
	public void run() {
		setUp();
	}
}