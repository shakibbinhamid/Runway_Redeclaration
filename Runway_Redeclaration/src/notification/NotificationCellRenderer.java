package notification;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class NotificationCellRenderer implements TableCellRenderer{

	public NotificationCellRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		NotificationCellComponent feed = (NotificationCellComponent)value;
		return feed;
	}
}