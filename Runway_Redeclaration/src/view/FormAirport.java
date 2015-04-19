package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import core.Airport;

public class FormAirport extends FormGeneral {
	protected JLabel nameLabel;
	protected JTextField name;
	protected TopFrame topFrame;

	public FormAirport(TopFrame topFrame) {
		super(topFrame, "Create Airport");
		this.topFrame = topFrame;
		nameLabel = new JLabel("Insert Airport Name:");
		name = new JTextField();
		setPreferredSize(new Dimension(180,110));
		init();
	}
	
	public void init(){
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.PAGE_AXIS));
		centerPanel.add(nameLabel);
		centerPanel.add(name);
		
		setListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setListener(){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(name.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please insert an airport name!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else{
					Airport airport = new Airport(name.getText());
					dispose();	
					FormCreateAirfield fa = new FormCreateAirfield(topFrame);
					topFrame.loadOrCreateAirport(airport);					
				}	
			}
		});
	}
}
