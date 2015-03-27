package notification;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class NotificationPanel extends JPanel{
	
	private static List<NotificationCellComponent> nots;
	private static JTable notsTable;
	private static NotificationModel model;
	
	public NotificationPanel(){
		super(new BorderLayout());
		
		nots = new ArrayList<>();
		model = new NotificationModel(nots);
		notsTable = new JTable(model);
		
		init();
	}
	
	private void init(){
		notsTable.setDefaultRenderer(NotificationCellComponent.class, new NotificationCellRenderer());
		notsTable.setDefaultEditor(NotificationCellComponent.class, new NotificationCellEditor());
		notsTable.setFocusable(false);
		notsTable.setRowSelectionAllowed(false);
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						for(int i=0; i<nots.size(); i++){
							notsTable.setRowHeight(i, nots.get(i).getPreferredSize().height);
						}
						notsTable.revalidate();
						revalidate();
					}
				});
			}
		});
		add(new JScrollPane(notsTable), BorderLayout.CENTER);
	}
	
	public static void notifyIt(String title, String details, String type){
		nots.add(new NotificationCellComponent (new Notification(title, details, type)));
		fire();
	}
	
	public static void fire(){
		model.fireTableDataChanged();
	}
}
