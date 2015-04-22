package view.customComponents;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import mail.EmailSystem;
import view.TopFrame;
import view.panels.LogPanel;

/**
 * This is the Central Notificatin Panel (extends JPanel).
 * Primary method is notifyIt
 * @author Shakib-Bin Hamid
 *
 */
public class NotificationPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		notsTable.setDefaultRenderer(NotificationCellComponent.class, new NotificationRendererEditor());
		notsTable.setDefaultEditor(NotificationCellComponent.class, new NotificationRendererEditor());
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
	
	/**
	 * Creates a new Notification and updates the Notification panel.
	 * 
	 * @param title Headline of the Notification
	 * @param details Details of the Notification
	 * @param type Type can be <em> Notification.FILE, Notification.CALC, Notification.ERROR, Notification.DEFUALT </em>
	 */
	public static void notifyIt(String title, String details, String type){
		Notification not = new Notification(title, details, type);
		nots.add(new NotificationCellComponent (not));
		fire();
		if(TopFrame.hasEmail() && TopFrame.isEmailEnabled()){
			EmailSystem.sendEmail(TopFrame.getEmail(), title, details);
		}
		LogPanel.log(title+" @ "+not.getDetails()+"\n*********************************\n");
	}
	
	protected static void fire(){
		model.fireTableDataChanged();
	}
}
