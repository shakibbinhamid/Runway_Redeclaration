package view;

import io.AirportFileFilter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JCheckBox helpingDevelopers;
	private TopFrame topframe;

	public static void main(String[] args){

		JFrame f = new JFrame("Test");
		f.setContentPane(new WelcomePanel());

		f.setVisible(true);
		f.setSize(700, 300);
		f.setLocationRelativeTo(null);
	}
	public WelcomePanel(){
		this(null);
	}

	public WelcomePanel(TopFrame top){
		this.helpingDevelopers = new JCheckBox("Give permission to send log files to help development?", true);
		this.topframe = top;

		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());

		JLabel title = new JLabel("Welcome to the Runway Re-Declaration Tool");
		title.setFont(new Font("Verdana",Font.BOLD,24));
		this.add(title, BorderLayout.NORTH);

		JLabel filler = new JLabel("    ");
		this.add(filler,BorderLayout.EAST);
		this.add(filler, BorderLayout.WEST);


		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(2,1));
		middle.add(new JLabel("We noticed you have no airport loaded, choose an option from bellow:"));

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		int gap = 50;
		buttons.setBorder(BorderFactory.createEmptyBorder(0, gap, gap, gap*2));


		JButton createAirport = new JButton("Create New Airport");
		buttons.add(createAirport);
		createAirport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FormAirport(topframe);
			}
		});

		JButton loadAirport  = new JButton("Load Existing Airport");
		buttons.add(loadAirport);
		loadAirport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadListener(topframe, new AirportFileFilter(), "airports/");
			}
		});
		middle.add(buttons);
		this.add(middle, BorderLayout.CENTER);


		this.helpingDevelopers.setBorder(null);
		this.add(this.helpingDevelopers, BorderLayout.SOUTH);
	}


	public boolean helpDevelopers(){
		return this.helpingDevelopers.isSelected();
	}






}
