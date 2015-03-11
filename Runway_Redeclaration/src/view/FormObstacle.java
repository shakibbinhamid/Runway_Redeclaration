package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Core.Obstacle;
import Core.PositionedObstacle;
import CoreInterfaces.AirfieldInterface;
import Exceptions.UnrecognisedAirfieldIntifierException;

public class FormObstacle extends FormGeneral {
	TopFrame topFrame;
	ArrayList<JTextField> textFields;
	
	JLabel nameLabel;
	JTextField nameTextBox;
	JLabel radiusLabel;
	JTextField radiusTextBox;
	JLabel heigthLabel;
	JTextField heigthTextBox;
	JLabel distFromLeftLabel;
	JTextField distFromLeftTextBox;
	JLabel distFromRightLabel;
	JTextField distFromRightTextBox;
	
	JLabel airfieldLabel;
	JComboBox<String> airfieldComboBox;
	
	// constructor used for editing 
	public FormObstacle(TopFrame topFrame, String title) {
		super(topFrame, title, true);
		this.topFrame = topFrame;
		
		nameLabel = new JLabel("Edit Name:");
		nameTextBox = new JTextField();
		radiusLabel = new JLabel("Edit Radius:");
		radiusTextBox = new JTextField();
		heigthLabel = new JLabel("Edit Height:");
		heigthTextBox = new JTextField();
		distFromLeftLabel = new JLabel("Edit Distance From Left:");
		distFromLeftTextBox = new JTextField();
		distFromRightLabel = new JLabel("Edit Distance From Right:");
		distFromRightTextBox = new JTextField();
		
		airfieldLabel = new JLabel("Change Airfield:");
		
		airfieldComboBox = new JComboBox<String>();
		populateTextfields();
		
		setPreferredSize(new Dimension(230,300));
		
	}
	
	private void populateTextfields(){		
		textFields = new ArrayList<JTextField>();
		textFields.add(nameTextBox);
		textFields.add(radiusTextBox);
		textFields.add(heigthTextBox);
		textFields.add(distFromLeftTextBox);
		textFields.add(distFromRightTextBox);
	}

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
		distFromRightLabel = new JLabel("Insert Distance From Right:");
		distFromRightTextBox = new JTextField();
		
		airfieldLabel = new JLabel("Pick Airfield:");	
		airfieldComboBox = new JComboBox<String>();
		setPreferredSize(new Dimension(300,350));
		
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
		textFieldsPanel.add(distFromRightLabel);
		textFieldsPanel.add(distFromRightTextBox);
		
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
					if(radius < 0 ){
						throw new NumberFormatException();
					}
					if(heigth < 0){
						heigth = 0;
					}
					Obstacle obstacle = new Obstacle(name, radius, heigth);
					String s = (String) airfieldComboBox.getSelectedItem();
					
						AirfieldInterface field = topFrame.getAirport().getAirfield(s);
						//after adding the new obstacle, reload the airport to update the GUI
						double distLeft = Double.parseDouble(distFromLeftTextBox.getText());
						double distRight = Double.parseDouble(distFromRightTextBox.getText());
						topFrame.loadOrCreateObstacle(obstacle, field, distLeft, distRight);
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
	
