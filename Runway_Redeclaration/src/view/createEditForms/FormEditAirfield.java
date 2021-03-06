package view.createEditForms;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import view.TopFrame;
import view.customComponents.Notification;
import view.customComponents.NotificationPanel;
import core.concrete.Airfield;
import core.interfaces.PositionedObstacleInterface;
import exceptions.UnrecognisedAirfieldIntifierException;
import exceptions.VariableDeclarationException;

public class FormEditAirfield extends FormAirfield{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Airfield currentAirfield;
	protected JLabel airfieldLabel, airfieldNameLabel, smallVLabel, largeVLabel;
	
	public FormEditAirfield(TopFrame topFrame){
		super(topFrame, "Edit airfield");
		currentAirfield = (Airfield) topFrame.getTabbePanel().getActiveField();
		setUpEditingForm();
		init();
	}
	
	public void setUpEditingForm(){
		button.setText("Apply");
		airfieldLabel = new JLabel("Airfield ");
		airfieldLabel.setFont(FormAirfield.VER_PL);
		airfieldLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		airfieldNameLabel = new JLabel(currentAirfield.getName());
		airfieldNameLabel.setFont(FormAirfield.VER_BD);
		
		String smallTora = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getTORA());
		String smallToda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getTODA());
		String smallAsda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getASDA());
		String smallLda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getLDA());
		String bigTora = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getTORA());
		String bigToda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getTODA());
		String bigAsda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getASDA());
		String bigLda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getLDA());
		
		bigTORATextBox.setText(bigTora);
		bigTODATextBox.setText(bigToda);
		bigASDATextBox.setText(bigAsda);
		bigLDATextBox.setText(bigLda);
		smallTORATextBox.setText(smallTora);
		smallTODATextBox.setText(smallToda);
		smallASDATextBox.setText(smallAsda);
		smallLDATextBox.setText(smallLda);
		
		setPreferredSize(new Dimension(400,250));
	}
	
	public void init(){
		upperPanel.setLayout(new GridLayout(3,2));
		upperPanel.add(airfieldLabel);
		upperPanel.add(airfieldNameLabel);
		upperPanel.add(new JLabel());
		upperPanel.add(new JLabel());
		
		String dan = topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getIdentifier();
		smallValuesLabel.setText(dan + " Runway Parameters(m)");
		smallValuesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		upperPanel.add(smallValuesLabel);
		String charlie = topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getIdentifier();
		largeValuesLabel.setText(charlie + " Runway Parameters(m)");
		largeValuesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		upperPanel.add(largeValuesLabel);
		
		setButtonListener();
		setButtonDeactivated();
		button.setEnabled(true);
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setButtonListener(){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean okToAdd = false;
				double[] smallInputs = new double[4];
				double[] bigInputs = new double[4];
				int angle;
					for (int i = 0; i < 4; i++) {
						try {
							if (Double.parseDouble(smallValueTextFields.get(i).getText()) < 0
									|| Double.parseDouble(bigValueTextFields.get(i).getText()) < 0
									|| smallValueTextFields.get(i).getText().equals("")
									|| bigValueTextFields.get(i).getText().equals("")) {
								System.err.println("Invalid inputs!");
								JOptionPane.showMessageDialog(null,
										"Please input only integers!",
										"Invalid Input!",
										JOptionPane.ERROR_MESSAGE);
								break;
							} else {
								okToAdd = true;
								smallInputs[i] = Double.parseDouble(smallValueTextFields.get(i)
												.getText());
								bigInputs[i] = Double.parseDouble(bigValueTextFields
										.get(i).getText());
							}
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null,
									"Please input only integers!",
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
							break;
						}
					}
				
					if (okToAdd)
						try {
							angle = currentAirfield.getDefaultSmallAngledRunway().getAngle();
							char sideChar = currentAirfield.getSmallAngledRunway().getSideLetter();
							Airfield f = new Airfield(angle, sideChar,
									smallInputs, bigInputs);
							if(currentAirfield.getPositionedObstacle() != null){
								PositionedObstacleInterface o = currentAirfield.getPositionedObstacle();
								f.addObstacle(o, o.distanceFromSmallEnd(), o.distanceFromLargeEnd());
							}
							if(topFrame.getAirport().replaceAirfield(f)){
								try {
									topFrame.getTabbePanel().updateTab(topFrame.getAirport().getAirfield(f.getName()));
								} catch (UnrecognisedAirfieldIntifierException e) {
									e.printStackTrace();
								}
								NotificationPanel.notifyIt(f.getName()+" Edited", "Current Values Are: \n\n", Notification.DEFAULT);
							}else{
								JOptionPane.showMessageDialog(topFrame, "OOps! Something went wrong :(", "Runway Edit Failed", JOptionPane.ERROR_MESSAGE);
							}
							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						} catch (VariableDeclarationException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						}
			}
		});
	}
}
