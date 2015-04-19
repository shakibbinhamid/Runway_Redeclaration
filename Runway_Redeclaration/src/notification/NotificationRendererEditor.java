package notification;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class NotificationRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected NotificationRendererEditor(){}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		NotificationCellComponent feed = (NotificationCellComponent)value;
		return feed;
	}

	public Object getCellEditorValue() {
		return null;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		NotificationCellComponent feed = (NotificationCellComponent)value;
		return feed;
	}
}