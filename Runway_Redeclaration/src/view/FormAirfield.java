package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	
//	JLabel smallTORALabel;
//	JLabel smallTODALabel;
//	JLabel smallASDALabel;
//	JLabel smallLDALabel;
	
	JTextField smallTORATextBox;
	JTextField smallTODATextBox;
	JTextField smallASDATextBox;
	JTextField smallLDATextBox;
	
	JTextField bigTORATextBox;
	JTextField bigTODATextBox;
	JTextField bigASDATextBox;
	JTextField bigLDATextBox;
	
	JLabel angleLabel;
	JTextField angleTextBox;
	JLabel sideLabel;
	JComboBox<String> sideComboBox;	
	/*
	public static void main(String[] args) {
		TopFrame tf = new TopFrame();
		Airport airp = new Airport("Gatwicked");
		tf.loadOrCreateAirport(airp);
		FormAirfield fa = new FormAirfield(tf);
		//FormObstacle fo = new FormObstacle(tf);
	}*/
	
	// constructor used for editing an airfield
	public FormAirfield(TopFrame topFrame, String title) {
		super(topFrame, title, true);
		this.topFrame = topFrame;
		smallValueTextFields = new ArrayList<JTextField>(10);
		bigValueTextFields = new ArrayList<JTextField>(10);
		
		
		String[] sides = {" ","L","R","C"};
		
		upperPanel = new JPanel();
		bottomPanel = new JPanel();
		smallAndLargeInputsPanel = new JPanel();
		
		smallValuesLabel = new JLabel("Left Starting Runway values:");
		largeValuesLabel = new JLabel("Right Starting Runway values:");
		
		String smallTora = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getTORA());
		String smallToda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getTODA());
		String smallAsda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getASDA());
		String smallLda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getLDA());
		
		String bigTora = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getTORA());
		String bigToda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getTODA());
		String bigAsda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getASDA());
		String bigLda = String.valueOf(topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getLDA());

		bigTORATextBox = new JTextField(bigTora);
		bigTODATextBox = new JTextField(bigToda);
		bigASDATextBox = new JTextField(bigAsda);
		bigLDATextBox = new JTextField(bigLda);
		
		smallTORATextBox = new JTextField(smallTora);
		smallTODATextBox = new JTextField(smallToda);
		smallASDATextBox = new JTextField(smallAsda);
		smallLDATextBox = new JTextField(smallLda);
		
		angleLabel = new JLabel("Insert Airfield Angle to the North:");
		angleTextBox = new JTextField();
		sideLabel = new JLabel("Choose the side of the runway:");
		sideComboBox = new JComboBox<String>(sides);
		setPreferredSize(new Dimension(500,300));

	}

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
		
//		toraLabel = new JLabel("TORA:");
//		todaLabel = new JLabel("TODA");
//		asdaLabel = new JLabel("ASDA");
//		ldaLabel = new JLabel("LDA:");
		
//		bigTORALabel = new JLabel("Insert Large Angled TORA:");
//		bigTODALabel = new JLabel("Insert Large Angled TODA");
//		bigASDALabel = new JLabel("Insert Large Angled ASDA");
//		bigLDALabel = new JLabel("Insert Large Angled LDA:");
		bigTORATextBox = new JTextField("TORA");
		bigTODATextBox = new JTextField("TODA");
		bigASDATextBox = new JTextField("ASDA");
		bigLDATextBox = new JTextField("LDA");
		
//		smallTORALabel = new JLabel("Insert Small Angled TORA:");
//		smallTODALabel = new JLabel("Insert Small Angled TODA:");
//		smallASDALabel = new JLabel("Insert Small Angled ASDA:");
//		smallLDALabel = new JLabel("Insert Small Angled LDA:");
		smallTORATextBox = new JTextField("TORA");
		smallTODATextBox = new JTextField("TODA");
		smallASDATextBox = new JTextField("ASDA");
		smallLDATextBox = new JTextField("LDA");
		
		angleLabel = new JLabel("Insert Airfield Angle to the North:");
		angleTextBox = new JTextField();
		sideLabel = new JLabel("Choose the side of the runway:");
		sideComboBox = new JComboBox<String>(sides);
		setPreferredSize(new Dimension(500,300));
		
		init();

	}
	
	public void init(){
		
		upperPanel.setLayout(new GridLayout(2,2));
		upperPanel.add(angleLabel);
		upperPanel.add(sideLabel);
		upperPanel.add(angleTextBox);
		upperPanel.add(sideComboBox);
		
		bottomPanel.setLayout(new GridLayout(5, 2));
		
		bottomPanel.add(smallValuesLabel);
		bottomPanel.add(largeValuesLabel);
		
//		bottomPanel.add(smallTORALabel);
//		bottomPanel.add(bigTORALabel);
		bottomPanel.add(smallTORATextBox);
		bottomPanel.add(bigTORATextBox);
		
//		bottomPanel.add(smallTODALabel);
//		bottomPanel.add(bigTODALabel);
		bottomPanel.add(smallTODATextBox);
		bottomPanel.add(bigTODATextBox);
		
//		bottomPanel.add(smallASDALabel);
//		bottomPanel.add(bigASDALabel);
		bottomPanel.add(smallASDATextBox);
		bottomPanel.add(bigASDATextBox);
		
//		bottomPanel.add(smallLDALabel);
//		bottomPanel.add(bigLDALabel);
		bottomPanel.add(smallLDATextBox);
		bottomPanel.add(bigLDATextBox);
		
		textFieldsPanel.setLayout(new BorderLayout());
		textFieldsPanel.add(upperPanel, BorderLayout.NORTH);
		textFieldsPanel.add(bottomPanel, BorderLayout.CENTER);
		
		smallValueTextFields.add(smallTORATextBox);
		bigValueTextFields.add(bigTORATextBox);
		smallValueTextFields.add(smallASDATextBox);
		bigValueTextFields.add(bigASDATextBox);
		smallValueTextFields.add(smallTODATextBox);
		bigValueTextFields.add(bigTODATextBox);
		smallValueTextFields.add(smallLDATextBox);
		bigValueTextFields.add(bigLDATextBox);
		
		
//		for(int i=0;i<4;i++){
//			Font newTextFieldFont; 
//			JTextField currentSmall =  smallValueTextFields.get(i);
//			JTextField currentBig =  bigValueTextFields.get(i);
//			newTextFieldFont = new Font(currentSmall.getFont().getName(),Font.ITALIC,currentSmall.getFont().getSize());
//			currentSmall.setFont(newTextFieldFont);
//			currentBig.setFont(newTextFieldFont);
//		}
		
		
		setTextfieldsListener();
		setListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setTextfieldsListener(){
		for(final JTextField jtf : smallValueTextFields){
			jtf.addFocusListener(new FocusListener() {
				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						//jtf.setFont(newTextFieldFont);
						if(smallValueTextFields.indexOf(jtf) == 0){
							jtf.setText("TORA");
						}
						if(smallValueTextFields.indexOf(jtf) == 2){
							jtf.setText("TODA");
						}
						if(smallValueTextFields.indexOf(jtf) == 1){
							jtf.setText("ASDA");
						}
						if(smallValueTextFields.indexOf(jtf) == 3){
							jtf.setText("LDA");
						}
					}
				}
				public void focusGained(FocusEvent e) {
					//jtf.setFont(null);
					jtf.setText("");
				}
			});
		}
		
		for(final JTextField jtf : bigValueTextFields){
			jtf.addFocusListener(new FocusListener() {
				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						//jtf.setFont(newTextFieldFont);
						if(bigValueTextFields.indexOf(jtf) == 0){
							jtf.setText("TORA");
						}
						if(bigValueTextFields.indexOf(jtf) == 2){
							jtf.setText("TODA");
						}
						if(bigValueTextFields.indexOf(jtf) == 1){
							jtf.setText("ASDA");
						}
						if(bigValueTextFields.indexOf(jtf) == 3){
							jtf.setText("LDA");
						}
					}
				}
				public void focusGained(FocusEvent e) {
					//jtf.setFont(null);
					jtf.setText("");
				}
			});
		}
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
					
					okToAdd = true;
				
					if (okToAdd){
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
					}else
						JOptionPane.showMessageDialog(topFrame, "Some inputs were not entered properly", "FAILED TO CREATE AIRFIELD", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
