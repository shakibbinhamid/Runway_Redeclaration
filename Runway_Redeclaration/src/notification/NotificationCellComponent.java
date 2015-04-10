package notification;
import io.Print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
 * This is the panel of a Single Notification.
 * @author shakib-bin hamid
 *
 */
public class NotificationCellComponent extends JPanel {
	
	private static final Color FILE = new Color(128, 0, 0); 
	private static final Color CALC = new Color(75, 0, 130); 
	private static final Color DEFAULT = Color.BLACK; 
	private static final Color ERROR = Color.RED; 

	private Notification notification;

	private boolean isExpanded;

	private JPanel header;
	private JPanel options;
	private JPanel details;

	private JLabel title;
	private JButton expand;
	private JButton print;
	private JTextPane all;

	public NotificationCellComponent(Notification n) {
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
	
	public void notify(String s, String c){ 
		switch(c){ 
		case "file": 
			notifyFile(s);
			break; 
		case "calc": 
			notifyCalc(s); 
			break; 
		default: 
			notifyDefault(s);; 
		} 
	} 
	
	private void notifyDefault(String s){ 
		addLog(s, DEFAULT); 
	} 

	private void notifyFile(String s){ 
		addLog(s, FILE); 
	} 

	private void notifyCalc(String s){ 
		addLog(s, CALC); 
	} 
	
	private void addLog(String s, Color c){ 
		all.setEditable(true); 
		appendToPane(s, c); //TODO: Word wrap 
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