package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ReorderLabelRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 5267456837862656292L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		StoreTableModel tableModel = (StoreTableModel) table.getModel();
		if (tableModel.getReorderNeed(row)) {
			label.setBackground(new Color(1.0f, 0.8f, 0.8f));
		} else {
			label.setBackground(table.getBackground());
		}
		return label;
	}
	
}
