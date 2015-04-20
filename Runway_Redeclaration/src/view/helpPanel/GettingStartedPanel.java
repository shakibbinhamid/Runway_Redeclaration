package view.helpPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		prev = new JButton("Previous");
		next = new JButton("Next");
		text = new JLabel("hi");
		
		bottom.setLayout(new BorderLayout());
		bottom.add(prev, BorderLayout.LINE_START);
		bottom.add(text, BorderLayout.CENTER);
		bottom.add(next, BorderLayout.LINE_END);
		this.add(bottom, BorderLayout.PAGE_END);
		
		prev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ss.prevSlide();
				update();
			}
			
		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ss.nextSlide();
				update();
			}
			
		});
		
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
	
	
}
