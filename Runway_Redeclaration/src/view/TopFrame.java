package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class TopFrame extends JFrame{

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
		this.setJMenuBar(new TopMenu());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 350);
		setVisible(true);
	}
	
}
