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

/**
 * 
 * @author Vlad Catrici
 * @editor Shakib-Bin Hamid
 *
 */
public class FormObstacle extends FormGeneral {
	TopFrame topFrame;
	private ArrayList<JTextField> textFields;
	
	JLabel nameLabel;
	JTextField nameTextBox;
	JLabel radiusLabel;
	JTextField radiusTextBox;
	JLabel heigthLabel;
	JTextField heightTextBox;
	JLabel distFromLeftLabel;
	JTextField distFromLeftTextBox;
	JLabel distFromRightLabel;
	JTextField distFromRightTextBox;
	JLabel airfieldLabel;
	JComboBox<String> airfieldComboBox;
	
	// constructor used for editing 
	protected FormObstacle(TopFrame topFrame, String title) {
		super(topFrame, title);
		this.topFrame = topFrame;
		
		nameLabel = new JLabel("Edit Name:");
		nameTextBox = new SelfCheckingField(Obstacle.NAME_REGEX);
		radiusLabel = new JLabel("Edit Radius:");
		radiusTextBox = new SelfCheckingField(Obstacle.RADIUS_REGEX);
		heigthLabel = new JLabel("Edit Height:");
		heightTextBox = new SelfCheckingField(Obstacle.HEIGHT_REGEX);
		distFromLeftLabel = new JLabel("Edit Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getIdentifier());
		distFromLeftTextBox = new SelfCheckingField(Obstacle.DIST_REGEX);
		distFromRightLabel = new JLabel("Edit Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getIdentifier());
		distFromRightTextBox = new SelfCheckingField(Obstacle.DIST_REGEX);
		
		airfieldLabel = new JLabel("Pick Airfield:");
		
		airfieldComboBox = new JComboBox<String>();
		populateTextfields();
		
		airfieldComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				distFromLeftLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[0]);
				distFromRightLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[1]);
				repaint();
			}

		});
		
		setPreferredSize(new Dimension(300,350));
		
	}
	
	public FormObstacle(TopFrame topFrame) {
		this(topFrame, "Create Obstacle");
		init();
	}
	
	private void populateTextfields(){		
		setTextFields(new ArrayList<JTextField>());
		getTextFields().add(nameTextBox);
		getTextFields().add(radiusTextBox);
		getTextFields().add(heightTextBox);
		getTextFields().add(distFromLeftTextBox);
		getTextFields().add(distFromRightTextBox);
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
		centerPanel.add(heightTextBox);	
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
					try {
						double radius = Double.parseDouble(radiusTextBox.getText());
						double height = Double.parseDouble(heightTextBox.getText());
						if(radius < 0 || height <0 ){
							throw new NumberFormatException();
						}
						Obstacle obstacle = new Obstacle(name, radius, height);
						
						String s = (String) airfieldComboBox.getSelectedItem();
						AirfieldInterface field = topFrame.getAirport().getAirfield(s);
						//after adding the new obstacle, reload the airport to update the GUI
						double distLeft = Double.parseDouble(distFromLeftTextBox.getText());
						double distRight = Double.parseDouble(distFromRightTextBox.getText());
						
						topFrame.loadOrCreateObstacle(obstacle, field, distLeft, distRight);
						dispose();
					} catch (UnrecognisedAirfieldIntifierException e1) {
						System.err.println("Invalid airfield identifier!");
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Insert valid inputs!", "Invalid input!", JOptionPane.ERROR_MESSAGE);
					}
				}
		});
	}

	public ArrayList<JTextField> getTextFields() {
		return textFields;
	}

	public void setTextFields(ArrayList<JTextField> textFields) {
		this.textFields = textFields;
	}
	
}