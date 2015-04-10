package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import core.Obstacle;
import coreInterfaces.AirfieldInterface;
import exceptions.UnrecognisedAirfieldIntifierException;

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
		super(topFrame, title);
		this.topFrame = topFrame;
		
		nameLabel = new JLabel("Edit Name:");
		nameTextBox = new JTextField();
		radiusLabel = new JLabel("Edit Radius:");
		radiusTextBox = new JTextField();
		heigthLabel = new JLabel("Edit Height:");
		heigthTextBox = new JTextField();
		distFromLeftLabel = new JLabel("Edit Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getIdentifier());
		distFromLeftTextBox = new JTextField();
		distFromRightLabel = new JLabel("Edit Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getIdentifier());
		distFromRightTextBox = new JTextField();
		
		airfieldLabel = new JLabel("Change Airfield:");
		
		airfieldComboBox = new JComboBox<String>();
		populateTextfields();
		
		airfieldComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {

				String item = (String) e.getItem();
				System.out.println("CRAZY");

				distFromLeftLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[0]);
				distFromRightLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[1]);

				repaint();
			}

		});
		
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
		super(topFrame, "Create Obstacle");
		this.topFrame = topFrame;
		
		nameLabel = new JLabel("Insert Name:");
		nameTextBox = new JTextField();
		radiusLabel = new JLabel("Insert Radius:");
		radiusTextBox = new JTextField();
		heigthLabel = new JLabel("Insert Height:");
		heigthTextBox = new JTextField();
		distFromLeftLabel = new JLabel("Insert Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getIdentifier());
		distFromLeftTextBox = new JTextField();
		distFromRightLabel = new JLabel("Insert Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getIdentifier());
		distFromRightTextBox = new JTextField();
		
		airfieldLabel = new JLabel("Pick Airfield:");	
		airfieldComboBox = new JComboBox<String>();
		
		airfieldComboBox.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				distFromLeftLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[0]);
				distFromRightLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[1]);
				repaint();
			}
		});
		
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
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		centerPanel.add(nameLabel);
		centerPanel.add(nameTextBox);
		centerPanel.add(radiusLabel);
		centerPanel.add(radiusTextBox);
		centerPanel.add(heigthLabel);
		centerPanel.add(heigthTextBox);	
		centerPanel.add(distFromLeftLabel);
		centerPanel.add(distFromLeftTextBox);
		centerPanel.add(distFromRightLabel);
		centerPanel.add(distFromRightTextBox);
		
		populateAirfieldComboBox();
		
		centerPanel.add(airfieldLabel);
		centerPanel.add(airfieldComboBox);
		
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
	
