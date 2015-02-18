package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;

public class TopFrame extends JFrame{
	
	private JPanel topPanel;
		private LogPanel logPanel;
		private JTabbedPane tabbedPanel;
	
	private AirportInterface airport;
	private AirfieldInterface airfield;

	public TopFrame(){
		init();
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopFrame frame = new TopFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void populateTables(){
		
	}
	
	public void init(){
		this.setJMenuBar(new TopMenu(this));
		
		topPanel = new JPanel();
		this.setContentPane(topPanel);
		topPanel.setLayout(new BorderLayout());
		
		logPanel = new LogPanel(null);
		topPanel.add(logPanel, BorderLayout.WEST);
		
		tabbedPanel = new TabbedPanel(null);
		topPanel.add(tabbedPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 350);
		setVisible(true);
	}
	
}
