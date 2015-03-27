package view;

import io.AirportFileFilter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import listeners.LoadListener;
/**
 * 
 * @author Stefan
 *
 */
public class WelcomePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JCheckBox helpingDevelopers;
	private TopFrame topframe;

//	public static void main(String[] args){
//
//		JFrame f = new JFrame("Test");
//		f.setContentPane(new WelcomePanel());
//
//		f.setVisible(true);
//		f.setSize(700, 300);
//		f.setLocationRelativeTo(null);
//	}
//	public WelcomePanel(){
//		this(null);
//	}

	public WelcomePanel(TopFrame top){
		this.helpingDevelopers = new JCheckBox("Give permission to send log files to help development?", true);
		this.topframe = top;

		this.setOpaque(true);
		
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 1));
		JLabel title1 = new JLabel("Runway Re-Declaration Tool");
		JLabel title2 = new JLabel("Welcome");
		title1.setHorizontalAlignment(SwingConstants.CENTER);
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		

		
		title1.setFont(new Font("Verdana",Font.BOLD,35));
		title2.setFont(new Font("Verdana",Font.BOLD,35));
		
		titlePanel.add(title1);
		titlePanel.add(title2);
		this.add(titlePanel, BorderLayout.NORTH);
		
		JLabel filler = new JLabel("    ");
		this.add(filler,BorderLayout.EAST);
		this.add(filler, BorderLayout.WEST);


		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(2,1));
		JLabel label = new JLabel("We noticed you have no airport loaded. Please choose an option from below");
		label.setFont(new Font("verdana", Font.PLAIN, 20));
		middle.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);

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
				topframe.switchToTabbedPanel();
			}
		});

		JButton loadAirport  = new JButton("Load Existing Airport");
		buttons.add(loadAirport);
		loadAirport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadListener(topframe, new AirportFileFilter(), "airports/");
				topframe.switchToTabbedPanel();
			}
		});
		middle.add(buttons);
		this.add(middle, BorderLayout.CENTER);


		this.helpingDevelopers.setBorder(null);
		this.add(this.helpingDevelopers, BorderLayout.SOUTH);
	}


	public boolean isHelpingDeveloper(){
		return this.helpingDevelopers.isSelected();
	}

}
