package view;

import java.awt.Dimension;
import java.awt.Point;
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
	protected TopFrame topFrame;
	private ArrayList<JTextField> textFields;
	
	protected JLabel nameLabel;
	protected JTextField nameTextBox;
	protected JLabel radiusLabel;
	protected JTextField radiusTextBox;
	protected JLabel heigthLabel;
	protected JTextField heigthTextBox;
	protected JLabel distFromLeftLabel;
	protected JTextField distFromLeftTextBox;
	protected JLabel distFromRightLabel;
	protected JTextField distFromRightTextBox;
	protected JLabel airfieldLabel;
	protected JComboBox<String> airfieldComboBox;
	
	// Constructor used for obstacle editing 
	protected FormObstacle(TopFrame topFrame, String title) {
		super(topFrame, title);
		this.topFrame = topFrame;
		
		initialiseStuff();
		populateTextfields();
		setToolTips();
		
		airfieldComboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				distFromLeftLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[0]);
				distFromRightLabel.setText("Edit Distance From "+ ((String)airfieldComboBox.getSelectedItem()).split("/")[1]);
				repaint();
			}
		});
		Dimension parentSize = topFrame.getSize(); 
	    Point p = topFrame.getLocation(); 
	    setLocation(p.x + parentSize.width / 4, p.y + (parentSize.height / 2 - parentSize.height / 10 ));
		setPreferredSize(new Dimension(300,350));
	}
	
	private void initialiseStuff(){
		nameLabel = new JLabel("Edit Name:");
		nameTextBox = new SelfCheckingField(Obstacle.NAME_REGEX);
		radiusLabel = new JLabel("Edit Radius (m):");
		radiusTextBox = new SelfCheckingField(Obstacle.RADIUS_REGEX);
		heigthLabel = new JLabel("Edit Height (m):");
		heigthTextBox = new SelfCheckingField(Obstacle.HEIGHT_REGEX);
		distFromLeftLabel = new JLabel("Edit Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getIdentifier() + "(m)");
		distFromLeftTextBox = new SelfCheckingField(Obstacle.DIST_REGEX);
		distFromRightLabel = new JLabel("Edit Distance From "+ topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getIdentifier()+ "(m)");
		distFromRightTextBox = new SelfCheckingField(Obstacle.DIST_REGEX);
		airfieldLabel = new JLabel("Pick Airfield:");
		airfieldComboBox = new JComboBox<String>();
	}
	
	private void setToolTips(){
		nameLabel.setToolTipText("A name must start with a letter.");
		radiusLabel.setToolTipText("The value must be a positive whole or fractionary number.");
		heigthLabel.setToolTipText("The value must be a positive whole or fractionary number.");
		distFromLeftLabel.setToolTipText("The value must be a positive whole or fractionary number.");
		distFromRightLabel.setToolTipText("The value must be a positive whole or fractionary number.");
	}
	
	// Contructor used for obstacle creation
	public FormObstacle(TopFrame topFrame) {
		this(topFrame, "Create Obstacle");
		init();
	}
	
	private void populateTextfields(){		
		setTextFields(new ArrayList<JTextField>());
		getTextFields().add(nameTextBox);
		getTextFields().add(radiusTextBox);
		getTextFields().add(heigthTextBox);
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
					try {
						double radius = Double.parseDouble(radiusTextBox.getText());
						double height = Double.parseDouble(heigthTextBox.getText());
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