package view.helpPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class GettingStartedPanel extends JFrame{

	//Help file directory
	private final String helpdir = "./help/gettingstarted/";
	//Maximum image size
	private final int scaler = 500;
	
	private SlideShowPane ss;
	private JButton prev, next;
	private JLabel text;
	
	public GettingStartedPanel(){
		ss = new SlideShowPane(new File(helpdir), scaler);
		init();
	}

	public static void main(String[] args) {
		GettingStartedPanel h = new GettingStartedPanel();
	}
	
	private void init() {
		this.setTitle("Getting Started");
		
		this.setLayout(new BorderLayout());
		this.add(ss, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		JPanel middle = new JPanel();
		prev = new JButton("Previous");
		next = new JButton("Next");
		text = new JLabel("hi");
		
		bottom.setLayout(new BorderLayout());
		middle.setLayout(new GridLayout(2,1));
		bottom.add(prev, BorderLayout.LINE_START);
		middle.add(text, BorderLayout.CENTER);
		text.setBorder(new EmptyBorder(0, 0, 10, 0));
		bottom.add(next, BorderLayout.LINE_END);
		middle.add(bottom);
		middle.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(middle, BorderLayout.PAGE_END);
		
		prev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				prev();
			}
			
		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
			
		});
		
		AbstractAction right = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				next();
				
			}
			
		};
		
		AbstractAction left = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prev();
				
			}
			
		};
		
		this.getRootPane().getInputMap().put( KeyStroke.getKeyStroke( "LEFT" ), "doLeftAction" );
		this.getRootPane().getActionMap().put("doLeftAction", left );
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke( "RIGHT" ), "doRightAction" );
		this.getRootPane().getActionMap().put("doRightAction", right );
		

		
		update();
		
		this.pack();
		this.setSize(600, 600);
		this.setVisible(true);
		
		
	}
	
	private void update(){
		next.setEnabled(ss.hasNext());
		prev.setEnabled(ss.hasPrev());
		//The html tags easily enable text wrapping
		text.setText("<html>"+ ss.getDescription() + "</html>");
		text.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	private void prev(){
		ss.prevSlide();
		update();
	}
	
	private void next(){
		ss.nextSlide();
		update();
	}
	
}
