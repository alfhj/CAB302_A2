package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Sets the rendering of the table's cells.
 * Renders the items' costs and prices with "$".
 * Left-aligns the strings and centre-aligns the numbers. 
 * Renders a cell with a light red background colour if that cell's item needs reordering.
 * 
 * @author alfhj
 *
 */
public class CellLabelRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 5267456837862656292L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		
		StoreTableModel tableModel = (StoreTableModel) table.getModel();
		String colName = tableModel.getColumnName(col);
		if (tableModel.getColumnClass(col) == String.class) {
			label.setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			label.setHorizontalAlignment(SwingConstants.CENTER);
		}
		if (colName.equals("Cost") || colName.equals("Price")) {
			label.setText("$" + getText());
		}
		if (colName.equals("Amount") && tableModel.getReorderNeed(row)) {
			label.setBackground(new Color(1.0f, 0.8f, 0.8f));
		} else {
			label.setBackground(table.getBackground());
		}
		return label;
	}
	
}
