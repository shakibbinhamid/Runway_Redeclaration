package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Core.Obstacle;
import CoreInterfaces.AirfieldInterface;
import Exceptions.UnrecognisedAirfieldIntifierException;

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
		
		nameLabel = new JLabel("Insert Name:");
		nameTextBox = new JTextField();
		radiusLabel = new JLabel("Insert Radius:");
		radiusTextBox = new JTextField();
		heigthLabel = new JLabel("Insert Height:");
		heigthTextBox = new JTextField();
		distFromLeftLabel = new JLabel("Insert Distance From Left:");
		distFromLeftTextBox = new JTextField();
		
		airfieldLabel = new JLabel("Pick Airfield:");
		
		airfieldComboBox = new JComboBox<String>();
		
		setPreferredSize(new Dimension(300,300));
		
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
		
		populateAirfieldComboBox();
		
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
					try {
					double radius = Double.parseDouble(radiusTextBox.getText());
					double heigth = Double.parseDouble(heigthTextBox.getText());
					if(radius < 0 || heigth < 0){
						throw new NumberFormatException();
					}
					Obstacle obstacle = new Obstacle(name, radius, heigth);
					String s = (String) airfieldComboBox.getSelectedItem();
					
						// I don't get this method signature
						AirfieldInterface field = topFrame.getAirport().getAirfield(s);
						//after adding the new obstacle, reload the airport to update the GUI
						topFrame.loadOrCreateObstacle(obstacle, field, Double.parseDouble(distFromLeftTextBox.getText()));
						dispose();
					} catch (UnrecognisedAirfieldIntifierException e1) {
						System.err.println("Invalid airfield identifier!");
						e1.printStackTrace();
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Insert valid inputs!", "Invalid input!", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Insert a name!", "Invalid input!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	}
	
