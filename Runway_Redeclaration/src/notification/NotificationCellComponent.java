package notification;
import io.Print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * This is the panel (extends JPanel) of a Single Notification.
 * @author Shakib-Bin Hamid
 *
 */
public class NotificationCellComponent extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color FILE_COLOR = new Color(128, 0, 0); 
	private static final Color CALC_COLOR = new Color(75, 0, 130); 
	private static final Color DEFAULT_COLOR = Color.BLACK; 
	private static final Color ERROR_COLOR = Color.RED; 

	private Notification notification;

	private boolean isExpanded;

	private JPanel header;
	private JPanel options;
	private JPanel details;

	private JLabel title;
	private JButton expand;
	private JButton print;
	private JTextPane all;

	protected NotificationCellComponent(Notification n) {
		this.notification = n;
		init();
	}

	private void init(){
		
		isExpanded = false;

		this.setLayout(new BorderLayout());
		header = new JPanel(new BorderLayout());
		details = new JPanel(new BorderLayout());
		options = new JPanel(new GridLayout(1,2));

		title = new JLabel();
		expand = new JButton("More");
		print = new JButton("Print");
		all = new JTextPane();

		header.add(title, BorderLayout.CENTER);
		header.add(options, BorderLayout.EAST);
		
		options.add(print);
		options.add(expand);

		details.add(new JScrollPane(all));
		all.setBackground(new Color(191, 239, 255)); 
		all.setEditable(false); 
		
		this.add(header, BorderLayout.NORTH);
		title.setText(notification.getTitle());
		notify(notification.getDetails(), notification.getType());
		expand.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (isExpanded){
					expand.setText("More");
					removeAll();
					add(header, BorderLayout.NORTH);
					isExpanded = false;
				}else{
					expand.setText("Less");
					add(details, BorderLayout.CENTER);
					isExpanded = true;
					notification.read();
				}
				
				NotificationPanel.fire();
				
			}	
		});
		print.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Print.print(all.getText());
			}
		});
	}
	
	public void paint(Graphics g){
		if(!notification.hasRead()){
			title.setForeground(Color.BLUE);
			title.setText("*"+notification.getTitle()+"*");
			title.setFont(new Font("verdana", Font.ITALIC, 11));
		}else{
			title.setForeground(Color.BLACK);
			title.setText(notification.getTitle());
			title.setFont(new Font("verdana", Font.PLAIN, 11));
		}
		super.paint(g);
	}
	
	/**
	 * The primary method of notification
	 * @param s the details to notify
	 * @param c the color to view it.
	 */
	public void notify(String s, String c){ 
		switch(c){ 
		case Notification.FILE: 
			notifyFile(s);
			break; 
		case Notification.CALC: 
			notifyCalc(s); 
			break;
		case Notification.ERROR:
			notifyError(s);
			break;
		default: 
			notifyDefault(s);; 
		} 
	} 
	
	private void notifyError(String s){
		addLog(s, ERROR_COLOR);
	}
	
	private void notifyDefault(String s){ 
		addLog(s, DEFAULT_COLOR); 
	} 

	private void notifyFile(String s){ 
		addLog(s, FILE_COLOR); 
	} 

	private void notifyCalc(String s){ 
		addLog(s, CALC_COLOR); 
	} 
	
	private void addLog(String s, Color c){ 
		all.setEditable(true); 
		appendToPane(s, c);
		all.setEditable(false); 
	} 

	private void appendToPane(String msg, Color c){ 
		StyleContext sc = StyleContext.getDefaultStyleContext(); 
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c); 

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console"); 
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED); 

		int len = all.getDocument().getLength(); 
		all.setFont(new Font("verdana", Font.PLAIN, 12)); 
		all.setCaretPosition(len); 
		all.setCharacterAttributes(aset, false); 
		all.setText(msg);
	} 
}