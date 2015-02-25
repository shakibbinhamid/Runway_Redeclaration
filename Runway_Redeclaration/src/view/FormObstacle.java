package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Core.Obstacle;
import CoreInterfaces.AirfieldInterface;
import Exceptions.InvalidIdentifierException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.UnusableRunwayException;

// TODO Sanitize listeners when pressing menu buttons
//   	should not be able to create an obstacle if there
//		are no airfields.

public class FormObstacle extends FormGeneral {
	TopFrame topFrame;
	
	JLabel nameLabel;
	JTextField nameTextBox;
	JLabel radiusLabel;
	JTextField radiusTextBox;
	JLabel heigthLabel;
	JTextField heigthTextBox;
	JLabel distFromLeftLabel;
	JTextField distFromLeftTextBox;
	
	JLabel airfieldLabel;
	JComboBox<String> airfieldComboBox;

	public FormObstacle(TopFrame topFrame) {
		super(topFrame, "Create Obstacle", true);
		this.topFrame = topFrame;
		
		nameLabel = new JLabel("Insert name:");
		nameTextBox = new JTextField();
		radiusLabel = new JLabel("Insert radius:");
		radiusTextBox = new JTextField();
		heigthLabel = new JLabel("Insert heigth:");
		heigthTextBox = new JTextField();
		distFromLeftLabel = new JLabel("Insert distance from left:");
		distFromLeftTextBox = new JTextField();
		
		airfieldLabel = new JLabel("Pick airfield:");
		
		List<String> airports = topFrame.getAirport().getAirfieldNames();
		String[] str = new String[airports.size()];
		str = airports.toArray(str);
		airfieldComboBox = new JComboBox<String>(str);
		
		setPreferredSize(new Dimension(230,250));
		
		init();
	}
	
	private void populateAirfieldComboBox(){
		List<AirfieldInterface> temp = topFrame.getAirport().getAirfields();
		for(AirfieldInterface s : temp){
			airfieldComboBox.addItem(s.getName());
		}
	}
	
	public void init(){
		textFieldsPanel.setLayout(new BoxLayout(textFieldsPanel, BoxLayout.Y_AXIS));
		
		textFieldsPanel.add(nameLabel);
		textFieldsPanel.add(nameTextBox);
		textFieldsPanel.add(radiusLabel);
		textFieldsPanel.add(radiusTextBox);
		textFieldsPanel.add(heigthLabel);
		textFieldsPanel.add(heigthTextBox);	
		textFieldsPanel.add(distFromLeftLabel);
		textFieldsPanel.add(distFromLeftTextBox);
		
		//populateAirfieldComboBox();
		
		textFieldsPanel.add(airfieldLabel);
		textFieldsPanel.add(airfieldComboBox);
		
		setListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setListener(){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String name = nameTextBox.getText();
				
				if(!name.equals("")){					
					double radius = Double.parseDouble(radiusTextBox.getText());
					double heigth = Double.parseDouble(heigthTextBox.getText());
					Obstacle obstacle = new Obstacle(name, radius, heigth);
					String s = (String) airfieldComboBox.getSelectedItem();
					System.out.println(airfieldComboBox.getSelectedItem().equals(topFrame.getAirport().getAirfieldNames().get(0)));
					
					
					try {
						// I don't get this method signature
						try {
							topFrame.getAirport().getAirfield(s).addObstacle(obstacle, s.split("/")[0], Double.parseDouble(distFromLeftTextBox.getText()));
						} catch (UnusableRunwayException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						dispose();
						
						//after adding the new obstacle, reload the airport to update the GUI
						topFrame.loadOrCreateAirport(topFrame.getAirport());
					} catch (UnrecognisedAirfieldIntifierException e1) {
						System.err.println("Invalid airfield identifier!");
						e1.printStackTrace();
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidIdentifierException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	

}
