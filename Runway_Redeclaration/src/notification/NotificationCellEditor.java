package notification;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class NotificationCellEditor extends AbstractCellEditor implements TableCellEditor {

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		NotificationCellComponent feed = (NotificationCellComponent)value;
		return feed;
	}

	public Object getCellEditorValue() {
		return null;
	}
}