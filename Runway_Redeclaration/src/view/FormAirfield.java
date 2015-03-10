package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Core.Airport;
import Exceptions.VariableDeclarationException;

public class FormAirfield extends FormGeneral {
	TopFrame topFrame;
	ArrayList<JTextField> smallValueTextFields;
	ArrayList<JTextField> bigValueTextFields;
	
	JPanel upperPanel;
	JPanel bottomPanel;
	JPanel smallAndLargeInputsPanel;
	
	JLabel smallValuesLabel;
	JLabel largeValuesLabel;
	
	JLabel smallTORALabel;
	JTextField smallTORATextBox;
	JLabel smallTODALabel;
	JTextField smallTODATextBox;
	JLabel smallASDALabel;
	JTextField smallASDATextBox;
	JLabel smallLDALabel;
	JTextField smallLDATextBox;
	
	JLabel bigTORALabel;
	JTextField bigTORATextBox;
	JLabel bigTODALabel;
	JTextField bigTODATextBox;
	JLabel bigASDALabel;
	JTextField bigASDATextBox;
	JLabel bigLDALabel;
	JTextField bigLDATextBox;
	
	JLabel angleLabel;
	JTextField angleTextBox;
	JLabel sideLabel;
	JComboBox sideComboBox;	

	public FormAirfield(TopFrame topFrame) {
		super(topFrame, "Create Airfield",true);
		this.topFrame = topFrame;
		smallValueTextFields = new ArrayList<JTextField>(10);
		bigValueTextFields = new ArrayList<JTextField>(10);
		
		String[] sides = {" ","L","R","C"};
		
		upperPanel = new JPanel();
		bottomPanel = new JPanel();
		smallAndLargeInputsPanel = new JPanel();
		
		smallValuesLabel = new JLabel("Left Starting Runway values:");
		largeValuesLabel = new JLabel("Right Starting Runway values:");
		
		bigTORALabel = new JLabel("Insert Large Angled TORA:");
		bigTORATextBox = new JTextField();
		bigTODALabel = new JLabel("Insert Large Angled TODA");
		bigTODATextBox = new JTextField();
		bigASDALabel = new JLabel("Insert Large Angled ASDA");
		bigASDATextBox = new JTextField();
		bigLDALabel = new JLabel("Insert Large Angled LDA:");
		bigLDATextBox = new JTextField();
		
		smallTORALabel = new JLabel("Insert Small Angled TORA:");
		smallTORATextBox = new JTextField();
		smallTODALabel = new JLabel("Insert Small Angled TODA:");
		smallTODATextBox = new JTextField();
		smallASDALabel = new JLabel("Insert Small Angled ASDA:");
		smallASDATextBox = new JTextField();
		smallLDALabel = new JLabel("Insert Small Angled LDA:");
		smallLDATextBox = new JTextField();
		
		angleLabel = new JLabel("Insert Airfield Angle to the North:");
		angleTextBox = new JTextField();
		sideLabel = new JLabel("Choose the side of the runway:");
		sideComboBox = new JComboBox<String>(sides);
		setPreferredSize(new Dimension(500,350));
		
		init();
	}
	
	public void init(){
		
		upperPanel.setLayout(new GridLayout(2,2));
		upperPanel.add(angleLabel);
		upperPanel.add(sideLabel);
		upperPanel.add(angleTextBox);
		upperPanel.add(sideComboBox);
		
		bottomPanel.setLayout(new GridLayout(8, 2));
		
		bottomPanel.add(smallTORALabel);
		bottomPanel.add(bigTORALabel);
		bottomPanel.add(smallTORATextBox);
		bottomPanel.add(bigTORATextBox);
		
		bottomPanel.add(smallTODALabel);
		bottomPanel.add(bigTODALabel);
		bottomPanel.add(smallTODATextBox);
		bottomPanel.add(bigTODATextBox);
		
		bottomPanel.add(smallASDALabel);
		bottomPanel.add(bigASDALabel);
		bottomPanel.add(smallASDATextBox);
		bottomPanel.add(bigASDATextBox);
		
		bottomPanel.add(smallLDALabel);
		bottomPanel.add(bigLDALabel);
		bottomPanel.add(smallLDATextBox);
		bottomPanel.add(bigLDATextBox);
		
		textFieldsPanel.setLayout(new BorderLayout());
		textFieldsPanel.add(upperPanel, BorderLayout.NORTH);
		textFieldsPanel.add(bottomPanel, BorderLayout.CENTER);
		
		smallValueTextFields.add(smallTORATextBox);
		bigValueTextFields.add(bigTORATextBox);
		smallValueTextFields.add(smallTODATextBox);
		bigValueTextFields.add(bigTODATextBox);
		smallValueTextFields.add(smallASDATextBox);
		bigValueTextFields.add(bigASDATextBox);
		smallValueTextFields.add(smallLDATextBox);
		bigValueTextFields.add(bigLDATextBox);
		
		setListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setListener(){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean okToAdd = false;
				try{
					if(angleTextBox.getText().equals("") || Integer.parseInt(angleTextBox.getText()) < 0){
						JOptionPane.showMessageDialog(null, "Insert a valid angle value!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Insert a valid angle value!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				
				double[] smallInputs = new double[4];
				double[] bigInputs = new double[4];
				int angle = Integer.parseInt(angleTextBox.getText());
				String sideStrig= (String) sideComboBox.getSelectedItem();
				char sideChar = sideStrig.charAt(0);
				
				if(Integer.parseInt(angleTextBox.getText()) > 0){
					for (int i = 0; i < 4; i++) {
						try {
							if (Integer.parseInt(smallValueTextFields.get(i).getText()) < 0
									|| Integer.parseInt(bigValueTextFields.get(i).getText()) < 0
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
								smallInputs[i] = Integer
										.parseInt(smallValueTextFields.get(i)
												.getText());
								bigInputs[i] = Integer.parseInt(bigValueTextFields
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
							angle = Integer.parseInt(angleTextBox.getText());
							sideStrig = (String) sideComboBox.getSelectedItem();
							sideChar = sideStrig.charAt(0);
							topFrame.loadOrCreateField(angle, sideChar,
									smallInputs, bigInputs);
							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						} catch (VariableDeclarationException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						}
				}
			}
		});
	}
}
